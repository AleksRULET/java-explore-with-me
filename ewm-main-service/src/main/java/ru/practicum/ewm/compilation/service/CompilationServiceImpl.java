package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.CompilationMapper;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.storage.CompilationRepository;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.compilation.storage.Predicates;
import ru.practicum.ewm.util.JsonPatch;
import ru.practicum.ewm.util.PageRequestWithOffset;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements AdminCompilationService, PublicCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final JsonPatch jsonPatch;

    @Override
    public void removeCompilation(Long id) {
        compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Compilation not found"));
        compilationRepository.deleteById(id);
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        return CompilationMapper.toCompilationDto(compilationRepository.save(CompilationMapper
                .toCompilation(newCompilationDto, eventRepository.findAllByIdIn(newCompilationDto.getEvents()))));
    }

    @Override
    public CompilationDto editCompilation(Long id, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compilation not found"));
        Set<Long> events = updateCompilationRequest.getEvents();
        Compilation newCompilation;
        if (events == null || events.isEmpty()) {
            newCompilation = CompilationMapper.toCompilation(id, updateCompilationRequest, Collections.emptySet());
        } else {
            newCompilation = CompilationMapper.toCompilation(id, updateCompilationRequest, eventRepository
                    .findAllByIdIn(events));
        }
        return CompilationMapper.toCompilationDto(compilationRepository
                .save(jsonPatch.mergePatch(compilation, newCompilation, Compilation.class)));
    }

    @Override
    public List<CompilationDto> findPinned(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        List<Compilation> compilations = compilationRepository.findAll((compilation, query, builder) -> {
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
    public CompilationDto findCompilation(Long id) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compilation not found")));
    }
}
