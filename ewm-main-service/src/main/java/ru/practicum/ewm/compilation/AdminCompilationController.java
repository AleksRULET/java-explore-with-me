package ru.practicum.ewm.compilation;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.model.dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.AdminCompilationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/compilations")
public class AdminCompilationController {

    private final AdminCompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto createCompilation(
            @Valid @RequestBody CompilationRequestDto compilationRequestDto) {
        return compilationService.addCompilation(compilationRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long id) {
        compilationService.removeCompilation(id);
    }

    @PatchMapping("/{id}")
    public CompilationResponseDto updateCompilation(@PathVariable Long id,
            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return compilationService.editCompilation(id, updateCompilationRequest);
    }
}
