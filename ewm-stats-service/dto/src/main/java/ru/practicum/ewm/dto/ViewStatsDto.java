package ru.practicum.ewm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@ToString
public class ViewStatsDto {

    @NotBlank
    private String app;
    @NotBlank
    @URL
    private String uri;
    @NotNull
    private Long hits;
}
