package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "doctor_availability")
public class DoctorAvailability {
    @Id
    @GeneratedValue
    private Long Id;
    @ManyToOne
    private Doctor doctor;
    @NotNull(message = "Wybór dnia tygodnia jest obowiązkowy")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
