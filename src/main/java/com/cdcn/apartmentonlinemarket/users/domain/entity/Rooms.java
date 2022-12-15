package com.cdcn.apartmentonlinemarket.users.domain.entity;

import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
public class Rooms implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private String location;

    private String citizenId;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stages stage;

    @OneToMany(mappedBy="room", fetch = FetchType.LAZY)
    private Set<Users> uses;
}
