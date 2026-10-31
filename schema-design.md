## MySQL Database Design
MySQL will store structured and relational entities:
- admins
- appointments
- clinic
- doctors
- doctor_available_times
- patients
- payments
- specialties


### Table: admins
- `id` INT, Primary Key, AUTO_INCREMENT
- `username` VARCHAR(100), UNIQUE, Not Null
- `email` VARCHAR(255), UNIQUE, Not Null
- `password_hash` VARCHAR(255), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP

**Notes**
- Admin can acces with username or email. Validation for duplicates and new admins checks both. 


### Table: appointments
- `id` INT, Primary Key, Auto Increment
- `uuid` CHAR(36), UNIQUE, Not Null
- `doctor_id` INT, Foreign Key → doctors(id)
- `patient_id` INT, Foreign Key → patients(id)
- `clinic_id` INT, Foreign Key → clinics(id)
- `appointment_time` DATETIME, Not Null
- `status` ENUM('Scheduled', 'Completed', 'Cancelled'), DEFAULT 'Scheduled'
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP

**Notes**
- `uuid` is used to prenvet ID Enumeration Attacks (IDOR).


### Table: clinics
- `id` INT, Primary Key, AUTO_INCREMENT
- `name` VARCHAR(150), Not Null
- `address` VARCHAR(300), Not Null
- `timezone` VARCHAR(50), Not Null, DEFAULT 'UTC'
- `phone` VARCHAR(10), Not Null


### Table: doctors
- `id` INT, Primary Key, AUTO_INCREMENT
- `uuid` CHAR(36), UNIQUE, Not Null
- `clinic_id` INT, Foreign Key → clinics(id)
- `specialty_id` INT, Foreign Key → specialties(id)
- `first_name` VARCHAR(50), Not Null
- `last_name` VARCHAR(50), Not Null
- `email` VARCHAR(255), UNIQUE, Null
- `phone` VARCHAR(10), Null
- `is_deleted` TINYINT(1), DEFAULT 0, Not Null
- `password_hash` VARCHAR(255), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP
- `updated_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

**Notes**
- `uuid` is used to prenvet ID Enumeration Attacks (IDOR).
- `is_deleted` is used as a boolean to preserve history of appointments. The appointments related are **not** deleted and they are stored for auditing.


### Table: doctor_available_times
- `doctor_id` INT, Foreign Key → doctors(id)
- `available_times`VARCHAR(20), Not null


### Table: patients
- `id` INT, Primary Key, AUTO_INCREMENT
- `uuid` VARCHAR(36) UNIQUE, Not Null
- `first_name` VARCHAR(50), Not Null
- `last_name` VARCHAR(50), Not Null
- `date_of_birth` DATE, Null 
- `gender` ENUM('Male','Female','Other'), Null
- `email` VARCHAR(255), UNIQUE, Null
- `phone` VARCHAR(30), Null
- `password_hash` VARCHAR(255), Not Null
- `is_deleted` TINYINT(1) DEFAULT 0, Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP
- `updated_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

**Notes**
- `uuid` is used to prenvet ID Enumeration Attacks (IDOR).
- `date_of_birth` may not be known or may not have been provided yet.
- `gender` is optional. The patient may fill it in to specify how they should be addressed or if it is required for the appointment. 
- The database will CHECK (`phone` IS NOT NULL OR `email` IS NOT NULL). Some patients may share `phone`: a family phone, their parents' phone, or a caregiver's phone.
- `is_deleted` is used as a boolean to preserve history of appointments. The appointments related are **not** deleted and they are stored for auditing.


### Table: payments
- `id` INT, Primary Key, AUTO_INCREMENT
- `uuid` CHAR(36), UNIQUE, Not Null
- `appointment_id` INT, Foreign Key → appointments(id)
- `amount` DECIMAL(10,2), Not Null
- `currency` CHAR(3), DEFAULT 'USD'
- `method` ENUM('card','cash','insurance','other'), DEFAULT 'card'
- `status` ENUM('pending','completed','failed','refunded'), DEFAULT 'pending'
- `transaction_ref` VARCHAR(255), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP

**Notes**
- `uuid` is used to prenvet ID Enumeration Attacks (IDOR).


### Table: specialties
- `id` INT, Primary Key, AUTO_INCREMENT
- `code` VARCHAR(50), UNIQUE, NOT NULL
- `name` VARCHAR(150), NOT NULL
- `description` TEXT, NULL


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
