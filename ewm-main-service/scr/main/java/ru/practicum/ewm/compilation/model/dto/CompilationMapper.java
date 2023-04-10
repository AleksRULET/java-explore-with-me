package ru.practicum.ewm.compilation.model.dto;

import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.model.Event;

import java.util.Set;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setEvents(compilation.getEvents());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());
        return compilationDto;
    }

    public static Compilation toCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest,
                                            Set<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setId(compId);
        compilation.setTitle(updateCompilationRequest.getTitle());
        compilation.setPinned(updateCompilationRequest.isPinned());
        compilation.setEvents(events);
        return compilation;
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, Set<Event> eventSet) {
        Compilation compilation = new Compilation();
        compilation.setEvents(eventSet);
        compilation.setPinned(newCompilationDto.isPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }
}
