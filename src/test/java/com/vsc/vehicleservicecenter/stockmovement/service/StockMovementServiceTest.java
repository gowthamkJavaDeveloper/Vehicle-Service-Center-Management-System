
package com.vsc.vehicleservicecenter.stockmovement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vsc.vehicleservicecenter.stockmovement.dto.StockMovementRequest;
import com.vsc.vehicleservicecenter.stockmovement.entity.StockMovement;
import com.vsc.vehicleservicecenter.stockmovement.repository.StockMovementRepository;
import com.vsc.vehicleservicecenter.sparepart.entity.SparePart;
import com.vsc.vehicleservicecenter.sparepart.repository.SparePartRepository;
import com.vsc.vehicleservicecenter.jobcard.repository.JobCardRepository;

class StockMovementServiceTest {

    private StockMovementService service;

    private StockMovementRepository repo;
    private SparePartRepository parts;
    private JobCardRepository jobs;

    @BeforeEach
    void setUp() {
        repo = mock(StockMovementRepository.class);
        parts = mock(SparePartRepository.class);
        jobs = mock(JobCardRepository.class);

        service = new StockMovementService(repo, parts, jobs);
    }

    private StockMovementRequest request(
            String type, int quantity) {

        StockMovementRequest r = new StockMovementRequest();
        r.setPartId(1L);
        r.setMovementType(type);
        r.setQuantity(quantity);
        return r;
    }

    @Test
    void shouldRejectZeroQuantity() {

        StockMovementRequest r = request("IN", 0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(r)
        );

        assertEquals("Quantity must be positive",
                ex.getMessage());
    }

    @Test
    void shouldRejectInvalidMovementType() {

        StockMovementRequest r = request("INVALID", 5);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(r)
        );

        assertEquals("Movement type must be IN or OUT",
                ex.getMessage());
    }

    @Test
    void shouldRejectOutWithoutJobCard() {

        StockMovementRequest r = request("OUT", 2);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(r)
        );

        assertEquals("OUT requires a job card",
                ex.getMessage());
    }

    @Test
    void shouldRejectInsufficientStock() {

        StockMovementRequest r = request("OUT", 10);
        r.setJobCardId(1L);

        var job = mock(
                com.vsc.vehicleservicecenter.jobcard.entity.JobCard.class
        );

        when(job.getStatus()).thenReturn("IN_PROGRESS");
        when(jobs.findById(1L)).thenReturn(Optional.of(job));

        SparePart part = new SparePart();
        part.setStockQuantity(3);

        when(parts.findByIdForUpdate(1L))
                .thenReturn(Optional.of(part));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(r)
        );

        assertEquals("Insufficient stock", ex.getMessage());

        verify(parts, never()).save(any());
        verify(repo, never()).save(any());
    }

    @Test
    void shouldSaveHistoricalPrice() {

        StockMovementRequest r = request("IN", 5);

        SparePart part = new SparePart();
        part.setStockQuantity(10);
        part.setUnitPrice(new BigDecimal("250.00"));

        when(parts.findByIdForUpdate(1L))
                .thenReturn(Optional.of(part));

        when(repo.save(any(StockMovement.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        StockMovement result = service.create(r);

        assertEquals(15, part.getStockQuantity());

        assertEquals(
                0,
                new BigDecimal("250.00")
                    .compareTo(result.getUnitPriceAtMovement())
        );

        verify(parts).save(part);
        verify(repo).save(any(StockMovement.class));
    }
}
