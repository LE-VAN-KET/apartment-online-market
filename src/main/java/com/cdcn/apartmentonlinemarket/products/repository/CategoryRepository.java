package com.cdcn.apartmentonlinemarket.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdcn.apartmentonlinemarket.products.domain.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
