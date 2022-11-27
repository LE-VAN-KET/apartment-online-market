package com.cdcn.apartmentonlinemarket.products.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.CategoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(unique = true)
    private String slug;

    @Enumerated(EnumType.ORDINAL)
    private CategoryStatus status;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Category parent;
}
