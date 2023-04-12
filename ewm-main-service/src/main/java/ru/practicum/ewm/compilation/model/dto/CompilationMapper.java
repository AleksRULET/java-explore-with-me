package ru.practicum.ewm.compilation.model.dto;

import java.util.Set;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.model.Event;

public class CompilationMapper {

    public static CompilationResponseDto toCompilationDto(Compilation compilation) {
        CompilationResponseDto compilationResponseDto = new CompilationResponseDto();
        compilationResponseDto.setId(compilation.getId());
        compilationResponseDto.setEvents(compilation.getEvents());
        compilationResponseDto.setPinned(compilation.isPinned());
        compilationResponseDto.setTitle(compilation.getTitle());
        return compilationResponseDto;
    }

    public static Compilation toCompilation(Long compId,
            UpdateCompilationRequest updateCompilationRequest,
            Set<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setId(compId);
        compilation.setTitle(updateCompilationRequest.getTitle());
        compilation.setPinned(updateCompilationRequest.isPinned());
        compilation.setEvents(events);
        return compilation;
    }

    public static Compilation toCompilation(CompilationRequestDto compilationRequestDto,
            Set<Event> eventSet) {
        Compilation compilation = new Compilation();
        compilation.setEvents(eventSet);
        compilation.setPinned(compilationRequestDto.isPinned());
        compilation.setTitle(compilationRequestDto.getTitle());
        return compilation;
    }
}
