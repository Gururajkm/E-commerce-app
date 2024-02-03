package com.ecommerce.ecom.services.admin.category;

import com.ecommerce.ecom.dto.CategoryDto;
import com.ecommerce.ecom.entity.Category;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
}
