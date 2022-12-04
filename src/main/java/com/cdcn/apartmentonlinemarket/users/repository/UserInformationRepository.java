package com.cdcn.apartmentonlinemarket.users.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.users.domain.entity.UserInformation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserInformationRepository extends IJpaRepository<UserInformation, UUID> {
}
