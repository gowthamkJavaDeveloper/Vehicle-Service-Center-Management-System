
package com.vsc.vehicleservicecenter.stockmovement.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_id", nullable = false)
    private Long partId;

    @Column(name = "job_card_id")
    private Long jobCardId;

    @Column(name = "movement_type", nullable = false)
    private String movementType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "unit_price_at_movement",
            precision = 12, scale = 2)
    private BigDecimal unitPriceAtMovement;
}
