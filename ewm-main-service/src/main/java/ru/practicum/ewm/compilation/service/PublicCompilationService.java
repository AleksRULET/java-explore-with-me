package ru.practicum.ewm.compilation.service;

import java.util.List;
import ru.practicum.ewm.compilation.model.dto.CompilationResponseDto;

public interface PublicCompilationService {

    List<CompilationResponseDto> findPinned(Boolean pinned, int from, int size);

    CompilationResponseDto findCompilation(Long id);
}