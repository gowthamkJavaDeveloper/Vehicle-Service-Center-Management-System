package com.vsc.vehicleservicecenter.sparepart.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter @Setter public class SparePartResponse {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
    private Integer reorderLevel;
}
