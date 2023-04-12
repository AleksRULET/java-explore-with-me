package ru.practicum.ewm.event.model.dto;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.state.UpdateStatus;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;
    private UpdateStatus status;
}
