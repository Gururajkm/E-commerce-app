package com.ecommerce.ecom.controller.admin;

import com.ecommerce.ecom.dto.CategoryDto;
import com.ecommerce.ecom.entity.Category;
import com.ecommerce.ecom.services.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;
    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return  ResponseEntity.status(HttpStatus.OK).body(category);
    }

}
