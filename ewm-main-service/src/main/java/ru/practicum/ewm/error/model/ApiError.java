package ru.practicum.ewm.error.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import ru.practicum.ewm.util.constant.DateFormat;

@Getter
@Setter
@ToString
public class ApiError {

    private HttpStatus status;
    private String reason;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.PATTERN)
    private LocalDateTime timestamp;
    private List<String> errors;
}