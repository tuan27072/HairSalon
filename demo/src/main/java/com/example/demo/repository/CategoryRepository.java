package com.example.demo.repository;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(long id);
    List<Category> findCategoriesByIsDeletedFalse();
}
