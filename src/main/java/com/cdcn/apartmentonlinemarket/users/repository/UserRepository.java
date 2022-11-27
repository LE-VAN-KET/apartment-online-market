package com.cdcn.apartmentonlinemarket.users.repository;

import com.cdcn.apartmentonlinemarket.common.enums.UserStatus;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends IJpaRepository<Users, UUID> {
    @Query("SELECT DISTINCT user FROM Users user JOIN user.roles " +
            "role JOIN role.users u WHERE UPPER(u.username) = upper(:username) AND u.isDelete = :isDelete AND u.status = :status")
    Optional<Users> findJoinRoleByUsernameEqualsIgnoreCaseAndIsDeleteAndStatus(@Param("username") String username,
                                                                               @Param("isDelete") Boolean isDelete,
                                                                               @Param("status") UserStatus status);
}
