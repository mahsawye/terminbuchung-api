package de.terminbuchung.terminbuchung_api.service;

import de.terminbuchung.terminbuchung_api.exception.BookingConflictException;
import de.terminbuchung.terminbuchung_api.exception.ResourceNotFoundException;
import de.terminbuchung.terminbuchung_api.model.Appointment;
import de.terminbuchung.terminbuchung_api.model.AppointmentStatus;
import de.terminbuchung.terminbuchung_api.model.Treatment;
import de.terminbuchung.terminbuchung_api.model.User;
import de.terminbuchung.terminbuchung_api.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final TreatmentService treatmentService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserService userService,
                              TreatmentService treatmentService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.treatmentService = treatmentService;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    }

    @Transactional
    public Appointment book(Long userId, Long treatmentId, LocalDateTime startTime) {
        User user = userService.findById(userId);
        Treatment treatment = treatmentService.findById(treatmentId);

        LocalDateTime endTime = startTime.plusMinutes(treatment.getDurationMinutes());

        long overlapping = appointmentRepository
                .countOverlapping(AppointmentStatus.BOOKED, startTime, endTime);
        if (overlapping > 0) {
            throw new BookingConflictException("This time slot is already booked");
        }

        return appointmentRepository.save(new Appointment(user, treatment, startTime));
    }

    @Transactional
    public Appointment cancel(Long id) {
        Appointment appointment = findById(id);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }
}