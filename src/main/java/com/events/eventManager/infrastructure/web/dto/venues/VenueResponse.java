package com.events.eventManager.infrastructure.web.dto.venues;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "Response data of a venue")
public record VenueResponse(

        @Schema(description = "Unique venue ID", example = "1")
        @NotNull
        Long id,

        @Schema(description = "Name of the venue", example = "Madison Square Garden")
        @NotBlank
        @Size(max = 255)
        String name,

        @Schema(description = "Physical address of the venue", example = "4 Pennsylvania Plaza, New York, NY 10001")
        @NotBlank
        String address,

        @Schema(description = "Seating capacity of the venue", example = "20000")
        @Min(1)
        Integer capacity
) {}