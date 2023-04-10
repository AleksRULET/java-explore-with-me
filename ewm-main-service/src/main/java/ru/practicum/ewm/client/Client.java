package ru.practicum.ewm.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.ViewClient;
import ru.practicum.client.hit.HitClient;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Client {

    private static final String appName = "ewm-service";
    private static final int DELTA_YEARS_FOR_INTERVAL = 1;

    private final HitClient hitClient;
    private final ViewClient viewClient;

    public Client(@Value("${stat-server.url}") String statServerUrl) {
        hitClient = new HitClient(statServerUrl);
        viewClient = new ViewClient(statServerUrl);
    }

    public void registerHit(String ip, String path) {
        HitDto hit = new HitDto();
        hit.setIp(ip);
        hit.setUri(path);
        hit.setApp(appName);
        hit.setTimestamp(LocalDateTime.now());
        hitClient.post(hit);
    }

    public List<ViewStatsDto> getViews(List<String> uri, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return viewClient.get(rangeStart, rangeEnd, uri);
    }

    public Map<Long, Long> getHits(List<Event> events, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now().minusYears(DELTA_YEARS_FOR_INTERVAL);
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(DELTA_YEARS_FOR_INTERVAL);
        }
        rangeStart = rangeStart.minusYears(DELTA_YEARS_FOR_INTERVAL);
        rangeEnd = rangeEnd.plusYears(DELTA_YEARS_FOR_INTERVAL);
        List<String> eventsUri = events.stream()
                .map(ent -> getEventUri(ent.getId()))
                .collect(Collectors.toList());
        List<ViewStatsDto> views = getViews(eventsUri, rangeStart, rangeEnd);
        return views.stream()
                .collect(Collectors.toMap(view -> getEventId(view.getUri()),
                        ViewStatsDto::getHits,
                        (existing, replacement) -> existing));
    }

    public Long getHit(Long eventId) {
        List<ViewStatsDto> views = getViews(List.of(getEventUri(eventId)),
                LocalDateTime.now().minusYears(DELTA_YEARS_FOR_INTERVAL),
                LocalDateTime.now().plusYears(DELTA_YEARS_FOR_INTERVAL));
        if (views.size() == 0) {
            return 0L;
        } else {
            return views.get(0).getHits();
        }
    }

    private String getEventUri(Long eventId) {
        String uriPart = "/events/";
        return uriPart.concat(eventId.toString());
    }

    private Long getEventId(String uri) {
        String[] splitUri = uri.split("/");
        String eventId = splitUri[splitUri.length - 1];
        return Long.valueOf(eventId);
    }
}
