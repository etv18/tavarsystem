package com.tavarlabs.tavarsystem.entity;

import com.tavarlabs.tavarsystem.utils.UpperCaseConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxType extends BaseEntity {

    @Convert(converter = UpperCaseConverter.class)
    @Column(nullable = false, length = 100, unique = true)
    private String name;
}
