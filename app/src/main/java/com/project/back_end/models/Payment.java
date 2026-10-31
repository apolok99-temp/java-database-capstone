package com.project.back_end.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity 
@Table(name = "payments")
public class Payment {
    
    public enum Method { Card, Cash, Insurance, Other }
    public enum Status { Pending, Completed, Failed, Refunded }

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid = UUID.randomUUID().toString();

    @NotNull(message = "Appointment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @NotBlank(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Payment amount must be greater than zero")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code (e.g., USD)")
    @Column(length = 3, nullable = false)
    private String currency = "USD";

    @NotNull(message = "Method of payment is required")
    @Enumerated(EnumType.STRING)
    private Method method = Method.Card;

    @NotNull(message = "Status of payments is required")
    @Enumerated(EnumType.STRING)
    private Status status = Status.Pending;

    @NotBlank(message = "Transaction reference is required")
    @Size(max = 255, message = "Transaction references must be under 255 characters")
    @Column(name = "transaction_ref", nullable = false, length = 255)
    private String transactionRef;

    @JsonIgnore // Exclude logic field from JSON
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructor

    public Payment() {}

    // Parameterized Constructor

    public Payment(Appointment appointment, BigDecimal amount, String currency, Method method, Status status, String transactionRef) {
        this.appointment = appointment;
        this.amount = amount;
        this.currency = currency;
        this.method = method;
        this.status = status;
        this.transactionRef = transactionRef;
    }

    // Getters and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Method getMethod() { return method; }
    public void setMethod(Method method) { this.method = method; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getTransactionRef() { return transactionRef; }
    public void setTransactionRef(String transactionRef) { this.transactionRef = transactionRef; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}
