# schema-architecture.md

## Application Architecture Summary

The application utilizes a multi-layered architecture combining both MVC and REST approaches and is designed to support diverse user interaction patterns through a Spring Boot backend. The user interface layer consists of a dual system: a User Interface Layer consisting of Thymeleaf-based web dashboards (such as Admin and Doctor Dash/Dashboards) for traditional server-rendered HTML, and a REST API layer (comprising modules like Appointments and PatientRecord) that facilitates scalable, JSON-based interactions for mobile apps and frontend modules. This separation ensures the system can provide both rich interactive browser views and robust API-driven integrations.

At the core of the system, the service layer manages all business logic, validations, and complex workflows, such as verifying doctor availability during appointment scheduling. Data persistence is achieved through a dual-repository approach: MySQL repositories use Spring Data JPA to manage relational entities like patients, doctors, and appointments, while a MongoDB repository handles flexible, document-based records like prescriptions. This data is mapped via model binding into Java classes, specifically as JPA Entities for MySQL and Document objects for MongoDB, ensuring seamless integration throughout the application's response layer.

## Data Flow Process

1. **User Interface Layer**: Users initiate requests through either Thymeleaf-based web dashboards (AdminDashboard, DoctorDashboard) or REST API clients (Appointments, PatientDashboard, PatientRecord) using JSON.
2. **Controller Layer**: Incoming requests are routed to either Thymeleaf Controllers for server-side rendering or REST Controllers for processing JSON-based API calls.
3. **Service Layer**: The controllers call the Service Layer, which acts as the central logic engine to apply business rules and coordinate workflows across different entities.
4. **Repository Layer**: The Service Layer interacts with the Repository Layer, utilizing MySQL Repositories (via Spring Data JPA) for relational data and MongoDB Repository (via Spring Data MongoDB) for document-based data.
5. **Database Access**: The repositories access their respective underlying engines: the MySQL Database manages structured core entities, while the MongoDB Database handles flexible, nested structures like prescriptions.
6. **Model Binding**: Retrieved data is mapped into Java model classes; MySQL data is converted into JPA Entities (e.g., Patient, Doctor, Appointment, Admin), and MongoDB data is loaded into Document objects.
7. **Application Models in Use**: The finalized models are used to generate responses, either being passed to Thymeleaf templates to render dynamic HTML or serialized into JSON for RESTful HTTP responses.
