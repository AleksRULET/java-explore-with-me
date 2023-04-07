package ru.practicum.ewm.hit.model;

import ru.practicum.ewm.dto.HitDto;

public class HitMapper {
    public static Hit toHit (HitDto hitDto) {
        Hit hit = new Hit();
        hit.setId(hitDto.getId());
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setCreated(hitDto.getCreated());
        return hit;
    }
}
