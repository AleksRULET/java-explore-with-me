package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.category.model.dto.CategoryMapper;
import ru.practicum.ewm.category.model.dto.NewCategoryDto;
import ru.practicum.ewm.category.storage.CategoryRepository;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.util.PageRequestWithOffset;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto findCategory(Long id) {
        return CategoryMapper.toCategoryDto(categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found")));
    }

    @Override
    public List<CategoryDto> findAllCategories(int from, int size) {
        return categoryRepository.findAll(PageRequestWithOffset.of(from, size))
                .getContent().stream().map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        if (newCategoryDto != null) {
            Category newCategory = CategoryMapper.toCategory(newCategoryDto);
            Category createdCategory = categoryRepository.save(newCategory);
            return CategoryMapper.toCategoryDto(createdCategory);
        }
        return null;
    }

    @Override
    public CategoryDto editCategory(Long id, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(newCategoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
