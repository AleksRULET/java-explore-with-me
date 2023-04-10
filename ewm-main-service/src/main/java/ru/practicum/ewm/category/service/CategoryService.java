package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.category.model.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findCategory(Long id);

    List<CategoryDto> findAllCategories(int from, int size);

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto editCategory(Long id, NewCategoryDto newCategoryDto);

    void removeCategory(Long id);

}