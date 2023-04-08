package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private  Long hits;
}
