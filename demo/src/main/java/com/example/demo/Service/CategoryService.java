package com.example.demo.Service;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.ServiceOption;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    public Category createNewCategory(Category category){
        categoryRepository.save(category);
        return category;
    }
    public List<Category> getAllCategory(){
        return categoryRepository.findCategoriesByIsDeletedFalse();
    }
    public Category deleteCategory(long id){
        Category category= categoryRepository.findCategoryById(id);
        category.setDeleted(true);
        return categoryRepository.save(category);
    }
}
