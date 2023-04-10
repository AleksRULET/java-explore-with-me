package ru.practicum.client.hit;

import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.BaseClient;
import ru.practicum.ewm.dto.HitDto;

public class HitClient extends BaseClient {

    private static final String API_PREFIX = "/hit";

    public HitClient(String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build()
        );
    }

    public void post(HitDto hitCreateDto) {
        post(API_PREFIX, hitCreateDto);
    }
}