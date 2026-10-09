package com.vsc.vehicleservicecenter.sparepart.dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Getter @Setter public class SparePartRequest {
    @NotBlank @Size(max=50) private String sku;
    @NotBlank @Size(max=120) private String name;
    @NotNull @DecimalMin("0.00") private BigDecimal unitPrice;
    @NotNull @Min(0) private Integer stockQuantity;
    @NotNull @Min(0) private Integer reorderLevel;
}
