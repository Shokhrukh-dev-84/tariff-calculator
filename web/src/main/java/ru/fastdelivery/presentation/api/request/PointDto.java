package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Коорданиты географической точки")
public record PointDto(

        @Schema(description = "Широта", example = "55.7558")
        @NotNull
        Double latitude,

        @Schema(description = "Долгота", example = "37.6173")
        @NotNull
        Double longitude
) {
}
