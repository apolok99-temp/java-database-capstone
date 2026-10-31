package com.project.back_end.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private String id;

    @NotNull(message = "Patient name is needed")
    @Size(min = 3, max = 100, message = "Patient name must be between 3 and 100 characters")
    private String patientName;

    @NotNull(message = "Appointment ID is needed")
    private Long appointmentId;

    @NotNull(message = "Medication name is needed")
    @Size(min = 3, max = 100, message = "Medication must be between 3 and 100 characters")
    private String medication;

    @NotNull(message = "Dosage is needed")
    private String dosage;

    @Size(max = 200, message = "Doctor notes cannot exceed 200 characters")
    private String doctorNotes;

    private PrescriptionMetadata metadata;

    public static class PrescriptionMetadata {

        public enum PrescriptionStatus { ACTIVE, CANCELLED, EXPIRED, REFILLED }
        
        @NotNull(message = "Prescription status is required")
        private PrescriptionStatus status = PrescriptionStatus.ACTIVE;

        @NotNull(message = "Date of expirity is required")
        private LocalDate expiryDate;

        @NotNull(message = "Doctor ID is required")
        private String issuedByDoctorId;

        @CreatedDate
        private LocalDateTime createdAt = LocalDateTime.now();

        @LastModifiedDate
        private LocalDateTime updatedAt;
        
        // Constructor for Metadata
        public PrescriptionMetadata(LocalDate expiryDate, String issuedByDoctorId) {
            this.expiryDate = expiryDate;
            this.issuedByDoctorId = issuedByDoctorId;
        }

        // Getters and Setters for Metadata
        public PrescriptionStatus getStatus() { return status; }
        public void setStatus(PrescriptionStatus status) { this.status = status; }

        public LocalDate getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

        public String getIssuedByDoctorId() { return issuedByDoctorId; }
        public void setIssuedByDoctorId(String issuedByDoctorId) { this.issuedByDoctorId = issuedByDoctorId; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
    }

    // Constructors: No-argument constructor (Required for MongoDB/JSON deserialization)
    public Prescription() {}

    // Parameterized Constructor
    public Prescription(String patientName, Long appointmentId, String medication, String dosage, String doctorNotes) {
        this.patientName = patientName;
        this.appointmentId = appointmentId;
        this.medication = medication;
        this.dosage = dosage;
        this.doctorNotes = doctorNotes;
    }

    // Getters and Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }

    public PrescriptionMetadata getMetadata() { return metadata; }
    public void setMetadata(PrescriptionMetadata metadata) { this.metadata = metadata; }

}
