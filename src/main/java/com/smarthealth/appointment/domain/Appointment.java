package com.smarthealth.appointment.domain;

import com.smarthealth.appointment.domain.enums.AppointmentStatus;
import com.smarthealth.common.domain.BaseEntity;
import com.smarthealth.doctor.domain.Doctor;
import com.smarthealth.doctor.domain.DoctorAvailabilitySlot;
import com.smarthealth.patient.domain.Patient;
import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "appointments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_appointments_slot", columnNames = "availability_slot_id")
        },
        indexes = {
                @Index(name = "idx_appointments_doctor", columnList = "doctor_id"),
                @Index(name = "idx_appointments_patient", columnList = "patient_id")
        }
)
public class Appointment extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_doctor"))
    private Doctor doctor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_patient"))
    private Patient patient;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_slot_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointments_slot"))
    private DoctorAvailabilitySlot availabilitySlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(length = 500)
    private String reason;

    public UUID getId() { return id; }

    protected Appointment() {}

    public Appointment(Doctor doctor, Patient patient, DoctorAvailabilitySlot slot, String reason) {
        this.doctor = doctor;
        this.patient = patient;
        this.availabilitySlot = slot;
        this.reason = reason;
    }
}