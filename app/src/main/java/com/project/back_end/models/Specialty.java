package com.project.back_end.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity 
@Table(name = "specialties")
public class Specialty {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Code is required")
    @Size(max = 50)
    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @NotBlank(message = "Name is required")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 300)
    private String description;

    // Constructors 

    public Specialty() {}

    // Parameterized Constructor

    public Specialty(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
