package com.cdcn.apartmentonlinemarket.users.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.UserPriority;
import com.cdcn.apartmentonlinemarket.common.enums.UserStatus;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(unique = true, length = 50)
    private String username;

    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserPriority userPriority = UserPriority.LOWEST;

    private Boolean isDelete = false;

    private String mailNotification;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus status = UserStatus.ACTIVE;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInformation userInformation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Rooms room;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Orders> orders;
}
