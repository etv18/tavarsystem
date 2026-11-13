package com.tavarlabs.tavarsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Column(nullable = false, length = 100, unique = true)
    private String code;

    @Column(nullable = false, length = 130)
    private String name;

    @Column(nullable = false, length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /*
     * precision means the total of digits the unitPrice will store in db
     * scale means the total of digits will be set after the point basically
     * is set up this way:
     *
     *      - 12 digits total
     *      - 10 digits before the .
     *      - 2  digits after the .
     * */
    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "tax_type_id")
    private TaxType taxType;

    @Column(name = "unit_of_measure", nullable = false, length = 30)
    private String unitOfMeasure;

    @Column(nullable = false)
    private long quantity = 0;

    @Column(nullable = false)
    private boolean active = true;
}
