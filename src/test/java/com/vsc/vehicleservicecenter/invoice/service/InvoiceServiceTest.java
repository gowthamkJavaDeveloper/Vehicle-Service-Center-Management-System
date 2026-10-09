
package com.vsc.vehicleservicecenter.invoice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vsc.vehicleservicecenter.common.exception.DuplicateResourceException;
import com.vsc.vehicleservicecenter.invoice.entity.Invoice;
import com.vsc.vehicleservicecenter.invoice.repository.InvoiceRepository;
import com.vsc.vehicleservicecenter.jobcard.entity.JobCard;
import com.vsc.vehicleservicecenter.jobcard.repository.JobCardRepository;
import com.vsc.vehicleservicecenter.booking.entity.Booking;
import com.vsc.vehicleservicecenter.booking.repository.BookingRepository;
import com.vsc.vehicleservicecenter.servicecatalog.entity.ServiceCatalog;
import com.vsc.vehicleservicecenter.servicecatalog.repository.ServiceCatalogRepository;
import com.vsc.vehicleservicecenter.stockmovement.entity.StockMovement;
import com.vsc.vehicleservicecenter.stockmovement.repository.StockMovementRepository;
import com.vsc.vehicleservicecenter.sparepart.repository.SparePartRepository;

class InvoiceServiceTest {

    private InvoiceService service;

    private InvoiceRepository invoices;
    private JobCardRepository jobs;
    private BookingRepository bookings;
    private ServiceCatalogRepository catalogs;
    private StockMovementRepository movements;
    private SparePartRepository parts;

    @BeforeEach
    void setUp() {
        invoices = mock(InvoiceRepository.class);
        jobs = mock(JobCardRepository.class);
        bookings = mock(BookingRepository.class);
        catalogs = mock(ServiceCatalogRepository.class);
        movements = mock(StockMovementRepository.class);
        parts = mock(SparePartRepository.class);

        service = new InvoiceService(
                invoices, jobs, bookings,
                catalogs, movements, parts
        );
    }

    private void setupCompletedJob() {

        JobCard job = mock(JobCard.class);
        when(job.getStatus()).thenReturn("COMPLETED");
        when(job.getBookingId()).thenReturn(20L);
        when(job.getLaborCharge())
                .thenReturn(new BigDecimal("500.00"));

        Booking booking = mock(Booking.class);
        when(booking.getServiceId()).thenReturn(30L);

        ServiceCatalog catalog = mock(ServiceCatalog.class);
        when(catalog.getBasePrice())
                .thenReturn(new BigDecimal("1000.00"));

        when(jobs.findById(10L))
                .thenReturn(Optional.of(job));

        when(bookings.findById(20L))
                .thenReturn(Optional.of(booking));

        when(catalogs.findById(30L))
                .thenReturn(Optional.of(catalog));

        when(movements.findAll()).thenReturn(List.of());

        when(invoices.save(any(Invoice.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));
    }

    @Test
    void shouldCalculateSubtotalAndTax() {

        setupCompletedJob();

        Invoice invoice = service.generate(
                10L, new BigDecimal("18"));

        assertEquals(
                new BigDecimal("1500.00"),
                invoice.getSubtotal());

        assertEquals(
                new BigDecimal("270.00"),
                invoice.getTaxAmount());

        assertEquals(
                new BigDecimal("1770.00"),
                invoice.getTotalAmount());
    }

    @Test
    void shouldUseHistoricalSparePartPrice() {

        setupCompletedJob();

        StockMovement movement = mock(StockMovement.class);

        when(movement.getJobCardId()).thenReturn(10L);
        when(movement.getMovementType()).thenReturn("OUT");
        when(movement.getQuantity()).thenReturn(2);
        when(movement.getUnitPriceAtMovement())
                .thenReturn(new BigDecimal("250.00"));

        when(movements.findAll())
                .thenReturn(List.of(movement));

        Invoice invoice = service.generate(
                10L, new BigDecimal("18"));

        // Base: 1000 + Labor: 500 + Parts: 500
        assertEquals(
                new BigDecimal("2000.00"),
                invoice.getSubtotal());

        assertEquals(
                new BigDecimal("360.00"),
                invoice.getTaxAmount());

        assertEquals(
                new BigDecimal("2360.00"),
                invoice.getTotalAmount());

        // Historical price means no current part lookup.
        verify(parts, never()).findById(anyLong());
    }

    @Test
    void shouldRejectDuplicateInvoice() {

        when(jobs.findById(10L))
                .thenReturn(Optional.of(mock(JobCard.class)));

        when(invoices.existsByJobCardId(10L))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> service.generate(
                        10L, new BigDecimal("18"))
        );

        verify(invoices, never()).save(any());
    }

    @Test
    void shouldRejectIncompleteJobCard() {

        JobCard job = mock(JobCard.class);
        when(job.getStatus()).thenReturn("IN_PROGRESS");

        when(jobs.findById(10L))
                .thenReturn(Optional.of(job));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.generate(
                        10L, new BigDecimal("18"))
        );

        assertEquals(
                "Complete job card before invoicing",
                ex.getMessage());
    }

    @Test
    void shouldRejectNegativeTax() {

        setupCompletedJob();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.generate(
                        10L, new BigDecimal("-5"))
        );

        assertEquals(
                "Invalid tax percent",
                ex.getMessage());

        verify(invoices, never()).save(any());
    }

    @Test
    void shouldRejectTaxAboveHundred() {

        setupCompletedJob();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.generate(
                        10L, new BigDecimal("101"))
        );

        assertEquals(
                "Invalid tax percent",
                ex.getMessage());

        verify(invoices, never()).save(any());
    }
}
