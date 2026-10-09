package de.terminbuchung.terminbuchung_api.dto;

import de.terminbuchung.terminbuchung_api.model.Treatment;

import java.math.BigDecimal;

public record TreatmentResponse(
        Long id,
        String name,
        int durationMinutes,
        BigDecimal price
) {
    public static TreatmentResponse from(Treatment t) {
        return new TreatmentResponse(t.getId(), t.getName(), t.getDurationMinutes(), t.getPrice());
    }
}