package de.terminbuchung.terminbuchung_api.dto;

import de.terminbuchung.terminbuchung_api.model.Appointment;
import de.terminbuchung.terminbuchung_api.model.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long userId,
        String userName,
        Long treatmentId,
        String treatmentName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status
) {
    public static AppointmentResponse from(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getUser().getId(),
                a.getUser().getName(),
                a.getTreatment().getId(),
                a.getTreatment().getName(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus()
        );
    }
}