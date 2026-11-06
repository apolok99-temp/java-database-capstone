package com.project.back_end.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;

import jakarta.transaction.Transactional;

@Service 
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final com.project.back_end.services.Service service;
    private final TokenService tokenService;

    public AppointmentService(AppointmentRepository appointmentRepository, 
                              PatientRepository patientRepository, 
                              DoctorRepository doctorRepository,
                              com.project.back_end.services.Service service, 
                              TokenService tokenService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.service = service;
        this.tokenService = tokenService;
    }

    @Transactional 
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1; // Succes
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0; // Error
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        
        Map<String, String> response = new HashMap<>();

        Optional<Appointment> appointmentOpt = appointmentRepository.findById((long)appointment.getId());

        if (appointmentOpt.isEmpty()) {
            response.put("message", "No appointment available with id: " + appointment.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (appointmentOpt.get().getPatient().getId() != appointment.getPatient().getId()) {
            response.put("message", "Patient Id mismatch");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        int isAppoiValidated = service.validateAppointment(appointment);
        if (isAppoiValidated == 1) {
            try {
                appointmentRepository.save(appointment);
                response.put("message", "Appointment Updated Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);

            } catch (Exception e) {
                System.out.println("Error: " + e);
                response.put("message", "Internal Server Error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

        } else if (isAppoiValidated == -1) {
            response.put("message", "Invalid doctor id");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.put("message", "Appointment is already booked for the given time or Doctor not available");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @Transactional 
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        
        Map<String, String> response = new HashMap<>();
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        String extractedToken = tokenService.extractEmail(token);
        Patient patient = patientRepository.findByEmail(extractedToken);

        if (patient.getId() != appointment.get().getPatient().getId()) {
            response.put("message", "Patient Id mismatch");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (appointment.isPresent()) {
            try {
                appointmentRepository.delete(appointment.get());
                response.put("message", "Appointment Deleted Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception e) {
                System.out.println("Error: " + e);
                response.put("message", "Internal Server Error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
        response.put("message", "No appointment for given id: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Transactional
    public Map<String, Object> getAppointment(String pname, LocalDate date, String token) {
        
        Map<String, Object> map = new HashMap<>();
        String extractedEmail = tokenService.extractEmail(token);
        Long doctorId = (long)doctorRepository.findByEmail(extractedEmail).getId();
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Appointment> appointments;

        if (pname.equals("null")) {
            appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);
        } else {
            appointments = appointmentRepository.findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(doctorId, pname, startOfDay, endOfDay);
        }

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(app -> new AppointmentDTO(
                    app.getId(),
                    app.getDoctor().getId(),
                    app.getDoctor().getName(),
                    app.getPatient().getId(),
                    app.getPatient().getName(),
                    app.getPatient().getEmail(),
                    app.getPatient().getPhone(),
                    app.getPatient().getAddress(),
                    app.getAppointmentTime(),
                    app.getStatus())
                )
                .collect(Collectors.toList());

        map.put("appointments", appointmentDTOs);
        return map;
    }

    @Transactional
    public void changeStatus(long appointmentId)
    {
        appointmentRepository.updateStatus(1, appointmentId);
    }
}
