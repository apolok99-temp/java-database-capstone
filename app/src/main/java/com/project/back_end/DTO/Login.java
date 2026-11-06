package com.project.back_end.DTO;

public class Login {
    
    private String identifier; // email or username
    private String password;

    // Constructor 
    public Login() {}

    // Getters and Setters
    public String getidentifier() { return identifier; }
    public void setidentifier(String identifier) { this.identifier = identifier; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
