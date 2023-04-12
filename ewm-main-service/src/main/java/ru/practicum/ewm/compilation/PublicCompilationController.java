package ru.practicum.ewm.compilation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.model.dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.service.PublicCompilationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationResponseDto> getAll(@RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.findPinned(pinned, from, size);
    }

    @GetMapping("/{id}")
    public CompilationResponseDto get(@PathVariable Long id) {
        return compilationService.findCompilation(id);
    }
}