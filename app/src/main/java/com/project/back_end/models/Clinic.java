package com.project.back_end.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clinics")
public class Clinic {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "A name is required")
    @Size(min = 2, max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "An address is required")
    @Size(max = 300)
    @Column(nullable = false, length = 300)
    private String address;

    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String timezone = "UTC";

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, length = 10)
    private String phone;

    // Constructor

    public Clinic(){}

    // Parameterized Constructor
    
    public Clinic(String name, String address, String timezone, String phone) {
        this.name = name;
        this.address = address;
        this.timezone = timezone;
        this.phone = phone;
    }

    // Getters and Setters
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

}
