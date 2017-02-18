package com.mindshine.clevergrid.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mindshine.clevergrid.domain.Category;
import com.mindshine.clevergrid.service.dto.CategoryDTO;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);

    List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categories);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    List<Category> categoryDTOsToCategories(List<CategoryDTO> categoryDTOs);
}
