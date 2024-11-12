package com.example.demo.api;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Store;
import com.example.demo.Service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="api")
@RestController
public class CategoryAPI {
    @Autowired
    CategoryService categoryService;
    //GET
    @GetMapping(value = "/api/category")
    public ResponseEntity getAllCategory(){
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }
    //POST
    @PostMapping(value="/api/category")
    public ResponseEntity createNewStore(@RequestBody Category category){
        Category newCategory =categoryService.createNewCategory(category);
        return ResponseEntity.ok(newCategory);

    }

    //PUT
    //DELETE
    @DeleteMapping(value = "/api/category/{id}")
    public ResponseEntity deleteCategory(@PathVariable long id){
        Category category= categoryService.deleteCategory(id);
        return ResponseEntity.ok(category);

    }
}
