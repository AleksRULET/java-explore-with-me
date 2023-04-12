package ru.practicum.ewm.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.hit.service.HitService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/hit")
public class HitController {

    private final HitService hitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@RequestBody HitDto hitDto) {
        hitService.addHit(hitDto);
        log.info("Hit created.");
    }
}
