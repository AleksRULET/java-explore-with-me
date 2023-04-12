package ru.practicum.ewm.viewstats;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.viewstats.service.ViewStatsServis;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/stats")
public class ViewStatsController {

    private final ViewStatsServis viewStatsServis;

    @GetMapping
    public List<ViewStatsDto> getViewStats(
            @PastOrPresent @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @PastOrPresent @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Getting view statistics.");
        return viewStatsServis.findViewStats(start, end, uris, unique);
    }
}
