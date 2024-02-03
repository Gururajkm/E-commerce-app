package com.ecommerce.ecom.services.admin.category;

import com.ecommerce.ecom.dto.CategoryDto;
import com.ecommerce.ecom.entity.Category;
import com.ecommerce.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(category.getName());
        category.setDescription(category.getDescription());
        return categoryRepository.save(category);
    }

}
