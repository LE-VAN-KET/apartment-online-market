package com.cdcn.apartmentonlinemarket.users.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Roles;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends IJpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);

}
