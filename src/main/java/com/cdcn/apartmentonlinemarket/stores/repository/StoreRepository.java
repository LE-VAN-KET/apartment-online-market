package com.cdcn.apartmentonlinemarket.stores.repository;

import com.cdcn.apartmentonlinemarket.common.enums.UserStatus;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends IJpaRepository<Store, UUID> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByResetToken(String resetToken);
}
