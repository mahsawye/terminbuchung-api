package de.terminbuchung.terminbuchung_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    protected Appointment() {
    }

    public Appointment(User user, Treatment treatment, LocalDateTime startTime) {
        this.user = user;
        this.treatment = treatment;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(treatment.getDurationMinutes());
        this.status = AppointmentStatus.BOOKED;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Treatment getTreatment() { return treatment; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
}