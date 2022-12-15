package com.cdcn.apartmentonlinemarket.users.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stages")
@Getter
@Setter
@NoArgsConstructor
public class Stages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY)
    private Set<Rooms> roomsSet;

}
