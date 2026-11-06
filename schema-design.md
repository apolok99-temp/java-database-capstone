## MySQL Database Design
MySQL will store structured and relational entities:
- admins
- appointments
- doctors
- doctor_available_times
- patients

### Table: admins
- `id` BIGINT, Primary Key, AUTO_INCREMENT
- `username` VARCHAR(100), UNIQUE, Not Null
- `email` VARCHAR(255), UNIQUE, Not Null
- `password` VARCHAR(255), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP

**Notes**
- Admin can acces with username or email. Validation for duplicates and new admins checks both. 


### Table: appointments
- `id` BIGINT, Primary Key, Auto Increment
- `doctor_id` BIGINT, Foreign Key → doctors(id)
- `patient_id` BIGINT, Foreign Key → patients(id)
- `appointment_time` DATETIME, Not Null
- `status` TINYINT(1), Not Null, DEFAULT 0 
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP

**Notes**
- `status` can be "Scheduled:0" or "Completed:1".


### Table: doctors
- `id` BIGINT, Primary Key, AUTO_INCREMENT
- `name` VARCHAR(100), Not Null
- `specialty` VARCHAR(50), Not Null
- `email` VARCHAR(255), UNIQUE, Null
- `password` VARCHAR(255), Not Null
- `phone` VARCHAR(30), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP
- `updated_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


### Table: doctor_available_times
- `doctor_id` BIGINT, Foreign Key → doctors(id)
- `available_times` VARCHAR(20), Not null


### Table: patients
- `id` BIGINT, Primary Key, AUTO_INCREMENT
- `name` VARCHAR(100), Not Null
- `email` VARCHAR(255), UNIQUE, Not Null
- `password` VARCHAR(255), Not Null
- `phone` VARCHAR(30), Not Null
- `address` VARCHAR(255), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP
- `updated_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


## MongoDB Collection Design
The following collections are implemented:
- prescriptions

### Collection: prescriptions
```json
{
  "id": "653bc8a2e4b0f1a2c3d4e5f6",
  "patientName": "John Doe",
  "appointmentId": 1025,
  "medication": "Amoxicillin",
  "dosage": "500mg, three times a day",
  "doctorNotes": "Complete the full course of antibiotics.",
  "metadata": {
    "status": "ACTIVE",
    "expiryDate": "2026-12-31",
    "issuedByDoctorId": "DOC-9982",
    "createdAt": "2026-10-27T10:15:30.000+00:00",
    "updatedAt": "2026-10-27T14:20:11.000+00:00"
  }
}
