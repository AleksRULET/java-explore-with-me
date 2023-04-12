package ru.practicum.ewm.category.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryResponseDto;
import ru.practicum.ewm.category.model.dto.CategoryMapper;
import ru.practicum.ewm.category.model.dto.CategoryRequestDto;
import ru.practicum.ewm.category.storage.CategoryRepository;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.util.PageRequestWithOffset;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto findCategory(Long id) {
        return CategoryMapper.toCategoryDto(categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found")));
    }

    @Override
    public List<CategoryResponseDto> findAllCategories(int from, int size) {
        return categoryRepository.findAll(PageRequestWithOffset.of(from, size))
                .getContent().stream().map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto != null) {
            Category newCategory = CategoryMapper.toCategory(categoryRequestDto);
            Category createdCategory = categoryRepository.save(newCategory);
            return CategoryMapper.toCategoryDto(createdCategory);
        }
        return null;
    }

    @Override
    public CategoryResponseDto editCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(categoryRequestDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
