package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
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
