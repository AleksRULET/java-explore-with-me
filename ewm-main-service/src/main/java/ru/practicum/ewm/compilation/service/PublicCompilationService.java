package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.model.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> findPinned(Boolean pinned, int from, int size);
    CompilationDto findCompilation(Long id);
}
