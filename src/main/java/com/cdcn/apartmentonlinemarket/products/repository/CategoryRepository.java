package com.cdcn.apartmentonlinemarket.products.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import org.springframework.stereotype.Repository;

import com.cdcn.apartmentonlinemarket.products.domain.entity.Category;

@Repository
public interface CategoryRepository extends IJpaRepository<Category, Long> {

}