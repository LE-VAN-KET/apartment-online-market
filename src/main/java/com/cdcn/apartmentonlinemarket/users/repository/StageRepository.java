package com.cdcn.apartmentonlinemarket.users.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Stages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepository extends IJpaRepository<Stages, Long> {

    @Query("select stage from Stages stage join fetch stage.roomsSet where stage.id = :id")
    Optional<Stages> findOne(@Param("id") Long id);
}
