package com.tavarlabs.tavarsystem.entity;

import com.tavarlabs.tavarsystem.utils.UpperCaseConverter;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Convert(converter = UpperCaseConverter.class)
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
}
