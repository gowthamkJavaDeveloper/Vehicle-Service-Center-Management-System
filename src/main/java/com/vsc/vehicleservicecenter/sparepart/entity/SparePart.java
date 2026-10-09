package com.vsc.vehicleservicecenter.sparepart.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @Entity @Table(name="spare_parts")
public class SparePart {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="sku", nullable=false)
    private String sku;
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="unit_price", nullable=false)
    private BigDecimal unitPrice;
    @Column(name="stock_quantity", nullable=false)
    private Integer stockQuantity;
    @Column(name="reorder_level", nullable=false)
    private Integer reorderLevel;
}
