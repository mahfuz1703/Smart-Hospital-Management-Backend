package com.smarthealth.medicalrecord.domain;

import com.smarthealth.appointment.domain.Appointment;
import com.smarthealth.common.domain.BaseEntity;
import com.smarthealth.doctor.domain.Doctor;
import com.smarthealth.patient.domain.Patient;
import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "medical_records",
        indexes = {
                @Index(name = "idx_medical_records_patient_created", columnList = "patient_id,created_at")
        }
)
public class MedicalRecord extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_medical_records_patient"))
    private Patient patient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_medical_records_doctor"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", foreignKey = @ForeignKey(name = "fk_medical_records_appointment"))
    private Appointment appointment;

    @Column(columnDefinition = "text")
    private String diagnosis;

    @Column(columnDefinition = "text")
    private String prescription;

    @Column(columnDefinition = "text")
    private String notes;

    public UUID getId() { return id; }

    protected MedicalRecord() {}

    public MedicalRecord(Patient patient, Doctor doctor, Appointment appointment) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
    }
}