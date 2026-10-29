## MySQL Database Design
MySQL will store structured and relational entities:
- admins
- appointments
- clinic_locations
- doctors
- doctors_availability_exceptions
- doctors_schedule
- patients
- payments
- specialties


### Table: admins 
(System users responsible for organizing, managing, supervising, and directing the operations of the app)
- `id` INT, Primary Key, AUTO_INCREMENT
- `username` VARCHAR(100), UNIQUE, Not Null
- `email` VARCHAR(255), UNIQUE, Not Null
- `password_hash` VARCHAR(255), Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP


### Table: appointments
- `id` INT, Primary Key, Auto Increment
- `uuid` CHAR(36), UNIQUE, Not Null
- `doctor_id` INT, Foreign Key → doctors(id)
- `patient_id` INT, Foreign Key → patients(id)
- `location_id` INT, Foreign Key → clinic_locations(id)
- `appointment_start` DATETIME, Not Null
- `appointment_end` DATETIME, Not Null
- `status` ENUM('Scheduled', 'Completed', 'Cancelled'), DEFAULT 'Scheduled'
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP

**Notes**
- `uuid` is used to prenvet ID Enumeration Attacks (IDOR).
- `appointment_end` is calculated at the Service Layer when a new `appointment` is created. `appointment_end` = `appointment_start` + `doctor_id`.`doctors_schedule`.`appointment_duration`
- Validation logic (in the Service Layer) will be used to ensure that there are no overlaps between `start_time` and `end_time` for the same doctor_id. In other words, that there are no two appointments for the same doctor at the same time.


### Table: clinic_locations
- `id` INT, Primary Key, AUTO_INCREMENT
- `name` VARCHAR(150), Not Null
- `address` VARCHAR(300), Null
- `timezone` VARCHAR(50), Not Null, DEFAULT 'UTC'
- `phone` VARCHAR(30), Null


### Table: doctors
- `id` INT, Primary Key, AUTO_INCREMENT
- `uuid` CHAR(36), UNIQUE, Not Null
- `clinic_id` INT, Foreign Key → clinic_locations.id
- `first_name` VARCHAR(50), Not Null
- `last_name` VARCHAR(50), Not Null
- `email` VARCHAR(255), UNIQUE, Null
- `phone` VARCHAR(30), Null
- `specialty_id` INT, Foreign Key → specialties(id)
- `is_deleted` TINYINT(1), DEFAULT 0, Not Null
- `created_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP
- `updated_at` TIMESTAMP, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

**Notes**
- `uuid` is used to prenvet ID Enumeration Attacks (IDOR).
- `is_deleted` is used as a boolean to preserve history of appointments. The appointments related are **not** deleted and they are stored for auditing.


### Table: doctor_availability_exceptions
- `id` INT, Primary Key, AUTO_INCREMENT
- `doctor_id` INT, Foreign Key → doctors(id)
- `exception_date` DATE, Not Null
- `type` ENUM('holiday', 'vacation', 'personal_day', 'temporary_change'), Not Null
- `reason` TEXT, Null


### Table: doctors_schedule
- `id` INT, Primary Key, AUTO_INCREMENT
- `doctor_id` INT, Foreign Key → doctors(id)
- `day_of_week` ENUM('Mon', 'Tue', 'Wed', 'Thu', 'Fri'), Not Null
- `start_time` TIME, Not Null
- `end_time` TIME, Not Null
- `appointment_duration` INT, Not Null


### Table: patients
- `id` INT, Primary Key, AUTO_INCREMENT
- `uuid` VARCHAR(36) UNIQUE, Not Null
- `first_name` VARCHAR(50), Not Null
- `last_name` VARCHAR(50), Not Null
- `date_of_birth` DATE, Null 
- `gender` ENUM('Male','Female','Other'), Null
- `email` VARCHAR(255), UNIQUE, Null
- `phone` VARCHAR(30), Null
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
- `patient_id` INT, Foreign Key → patients(id)
- `amount` DECIMAL(10,2), NOT NULL
- `currency` CHAR(3), DEFAULT 'USD'
- `method` ENUM('card','cash','insurance','other'), DEFAULT 'card'
- `status` ENUM('pending','completed','failed','refunded'), DEFAULT 'pending'
- `transaction_ref` VARCHAR(255), NULL
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
  "_id": { "$oid": "65a1f2b3e4b0a1a2b3c4d5e6" },
  "prescription_uuid": "pres-9876-abcd-5432",
  "schema_version": 2,
  "metadata": {
    "created_at": { "$date": "2023-10-27T10:30:00Z" },
    "source_device": "tablet_clinic_01",
    "tags": ["urgent", "post-surgery", "follow-up-required"]
  },
  "clinical_context": {
    "patient_id": { "$oid": "507f1f77bcf86cd799439011" }, 
    "doctor_id": { "$oid": "507f1f77bcf86cd799439022" },
    "diagnosis": {
      "description": "Infección respiratoria aguda",
      "icd_10_code": "J06.9"
    },
    "snapshot_patient_info": {
      "full_name": "Juan Pérez",
      "allergies_at_time_of_prescription": ["Penicillin"]
    }
  },
  "medications": [
    {
      "drug_name": "Amoxicilina",
      "dosage": "500mg",
      "frequency": {
        "interval": "8h",
        "instructions": "Tomar cada 8 horas después de las comidas"
      },
      "duration": {
        "value": 7,
        "unit": "days"
      },
      "notes": "No interrumpir el tratamiento"
    },
    {
      "drug_name": "Paracetamol",
      "dosage": "1g",
      "frequency": {
        "interval": "cada 6h",
        "instructions": "Solo en caso de fiebre o dolor"
      },
      "duration": {
        "value": 3,
        "unit": "days"
      }
    }
  ]
}
