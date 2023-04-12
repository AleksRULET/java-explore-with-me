package ru.practicum.ewm.category.service;

import java.util.List;
import ru.practicum.ewm.category.model.dto.CategoryResponseDto;
import ru.practicum.ewm.category.model.dto.CategoryRequestDto;

public interface CategoryService {

    CategoryResponseDto findCategory(Long id);

    List<CategoryResponseDto> findAllCategories(int from, int size);

    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto editCategory(Long id, CategoryRequestDto categoryRequestDto);

    void removeCategory(Long id);

}