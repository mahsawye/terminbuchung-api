package de.terminbuchung.terminbuchung_api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Treatment id is required")
        Long treatmentId,

        @NotNull(message = "Start time is required")
        @Future(message = "Start time must be in the future")
        LocalDateTime startTime
) {
}