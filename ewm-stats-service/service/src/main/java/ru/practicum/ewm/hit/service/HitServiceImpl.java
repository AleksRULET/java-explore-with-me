package ru.practicum.ewm.hit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.hit.model.HitMapper;
import ru.practicum.ewm.hit.storage.HitRepository;

@Service
@AllArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    public void addHit(HitDto hitDto) {
        hitRepository.save(HitMapper.toHit(hitDto));
    }
}
