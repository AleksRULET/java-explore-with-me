package ru.practicum.ewm.compilation.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.dto.CompilationMapper;
import ru.practicum.ewm.compilation.model.dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.storage.CompilationRepository;
import ru.practicum.ewm.compilation.storage.Predicates;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.util.JsonPatch;
import ru.practicum.ewm.util.PageRequestWithOffset;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements AdminCompilationService, PublicCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final JsonPatch jsonPatch;

    @Override
    public void removeCompilation(Long id) {
        compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compilation not found"));
        compilationRepository.deleteById(id);
    }

    @Override
    public CompilationResponseDto addCompilation(CompilationRequestDto compilationRequestDto) {
        return CompilationMapper.toCompilationDto(compilationRepository.save(CompilationMapper
                .toCompilation(compilationRequestDto,
                        eventRepository.findAllByIdIn(compilationRequestDto.getEvents()))));
    }

    @Override
    public CompilationResponseDto editCompilation(Long id,
            UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compilation not found"));
        Set<Long> events = updateCompilationRequest.getEvents();
        Compilation newCompilation;
        if (events == null || events.isEmpty()) {
            newCompilation = CompilationMapper.toCompilation(id, updateCompilationRequest,
                    Collections.emptySet());
        } else {
            newCompilation = CompilationMapper.toCompilation(id, updateCompilationRequest,
                    eventRepository
                            .findAllByIdIn(events));
        }
        return CompilationMapper.toCompilationDto(compilationRepository
                .save(jsonPatch.mergePatch(compilation, newCompilation, Compilation.class)));
    }

    @Override
    public List<CompilationResponseDto> findPinned(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        List<Compilation> compilations = compilationRepository.findAll(
                (compilation, query, builder) -> {
                    compilation.fetch("events", JoinType.LEFT);
                    List<Predicate> filters = pinned == null ? Collections.emptyList()
                            : List.of(Predicates.hasPinned(compilation, builder, pinned));
                    return builder.and(filters.toArray(new Predicate[]{}));
                }, pageable).getContent();
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationResponseDto findCompilation(Long id) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compilation not found")));
    }
}
