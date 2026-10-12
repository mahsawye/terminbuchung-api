package de.terminbuchung.terminbuchung_api.repository;

import de.terminbuchung.terminbuchung_api.model.Appointment;
import de.terminbuchung.terminbuchung_api.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
            select count(a) from Appointment a
            where a.status = :status
              and a.startTime < :end
              and a.endTime > :start
            """)
    long countOverlapping(@Param("status") AppointmentStatus status,
                          @Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);

    @Query("""
        select count(a) from Appointment a
        where a.id <> :id
          and a.status = :status
          and a.startTime < :end
          and a.endTime > :start
        """)
    long countOverlappingExcluding(@Param("id") Long id,
                                   @Param("status") AppointmentStatus status,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);
}