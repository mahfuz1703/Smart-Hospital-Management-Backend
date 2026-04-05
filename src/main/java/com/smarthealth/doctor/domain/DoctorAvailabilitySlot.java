package com.smarthealth.doctor.domain;

import com.smarthealth.common.domain.BaseEntity;
import com.smarthealth.doctor.domain.enums.SlotStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "doctor_availability_slots",
        indexes = {
                @Index(name = "idx_slots_doctor_start", columnList = "doctor_id,start_time")
        }
)
public class DoctorAvailabilitySlot extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_slots_doctor"))
    private Doctor doctor;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SlotStatus status = SlotStatus.OPEN;

    public UUID getId() { return id; }
    public Doctor getDoctor() { return doctor; }
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public SlotStatus getStatus() { return status; }

    protected DoctorAvailabilitySlot() {}

    public DoctorAvailabilitySlot(Doctor doctor, Instant startTime, Instant endTime) {
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}