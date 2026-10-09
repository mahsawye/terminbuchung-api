package de.terminbuchung.terminbuchung_api.dto;

import de.terminbuchung.terminbuchung_api.model.Treatment;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TreatmentRequest(

        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @Min(value = 5, message = "Duration must be at least 5 minutes")
        int durationMinutes,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price
) {
    public Treatment toEntity() {
        return new Treatment(name, durationMinutes, price);
    }
}