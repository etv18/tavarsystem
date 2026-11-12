package com.tavarlabs.tavarsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxType extends BaseEntity {

    @Column(nullable = false, length = 100, unique = true)
    private String name;
}
