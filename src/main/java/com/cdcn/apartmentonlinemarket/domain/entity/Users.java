package com.cdcn.apartmentonlinemarket.domain.entity;

import com.cdcn.apartmentonlinemarket.common.enums.UserPrioty;
import com.cdcn.apartmentonlinemarket.common.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
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
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(unique = true, length = 50)
    private String username;

    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserPrioty userPrioty;

    private Boolean isDelete;

    private String mailNotification;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserInformation userInformation;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Rooms room;

    @OneToMany(mappedBy = "")
    private Set<Orders> orders;
}
