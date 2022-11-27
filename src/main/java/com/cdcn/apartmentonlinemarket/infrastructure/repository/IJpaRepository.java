package com.cdcn.apartmentonlinemarket.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IJpaRepository<T, I> extends JpaRepository<T,I>, JpaSpecificationExecutor<T> {
}
