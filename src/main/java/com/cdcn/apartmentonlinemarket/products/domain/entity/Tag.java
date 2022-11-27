package com.cdcn.apartmentonlinemarket.products.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.Color;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer position;

    @Enumerated(EnumType.ORDINAL)
    private Color color;

    @ManyToMany(mappedBy = "tags")
    private Set<Product> products;
}
