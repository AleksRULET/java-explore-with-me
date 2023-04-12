package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.model.dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.model.dto.UpdateCompilationRequest;

public interface AdminCompilationService {

    CompilationResponseDto addCompilation(CompilationRequestDto compilationRequestDto);

    CompilationResponseDto editCompilation(Long id, UpdateCompilationRequest updateCompilationRequest);

    void removeCompilation(Long id);
}
