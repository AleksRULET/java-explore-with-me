package ru.practicum.ewm.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.model.dto.CategoryResponseDto;
import ru.practicum.ewm.category.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> getCategories(@RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAllCategories(from, size);
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(@PathVariable Long id) {
        return categoryService.findCategory(id);
    }

}
