package com.vsc.vehicleservicecenter.stockmovement.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class StockMovementRequest {
	@NotNull
	private Long partId;
	private Long jobCardId;
	@NotBlank
	private String movementType;
	@NotNull
	private Integer quantity;
	private String remarks;
}
