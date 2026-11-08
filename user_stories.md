# Users Stories
## Admin
### Admin User Story: Logging In
**Title:**
As an Admin, I want to log into the portal with my credentials, so that I can manage the platform securely.

**Acceptance Criteria:**
1. The system provides a login form requesting a username and password.
2. The user is granted access to the admin dashboard only upon entering valid credentials.
3. An error message is displayed if an incorrect username or password is provided.
4. The session remains secure and authenticated throughout the navigation until a log out.

**Priority:** High
**Story Points:** 3

**Notes:**
- Ensure passwords are encrypted with a hash and in transit using HTTPS.
- Consider adding a "Forgot Password" link in future iterations.

### Admin User Story: Logging Out

**Title:**
As an Admin, I want to log out of the portal, so that I can protect system access and prevent unauthorized usage.

**Acceptance Criteria:**
1. A visible "Logout" button is available on the admin dashboard.
2. Clicking the logout button immediately terminates the active session.
3. Upon logging out, the user is redirected to the login page.
4. The user cannot navigate back to protected pages after logging out and without logging in again.

**Priority:** High
**Story Points:** 1

**Notes:**
- Ensure all local session tokens/cookies are cleared upon logout.

### Admin User Story: Add doctors

**Title:**
As an Admin, I want to add new doctors to the portal, so that the user can make an appointment with them.

**Acceptance Criteria:**
1. There is a form available to input doctor details in the dashboard.
2. The system validates that all required fields are filled before saving.
3. Upon successful submission, the new doctor's profile is saved to the database and visible in the portal.
4. A success notification is displayed to the admin after a successful addition.

**Priority:** High
**Story Points:** 5

**Notes:**
- Edge case: Handle duplicate entries (e.g., if a doctor with the same ID/Email/Phone already exists).

### Admin User Story: Delete doctors

**Title:**
As an admin, I want to delete doctor profiles from the portal, so that I can remove inactive or incorrect information.

**Acceptance Criteria:**
1. Each doctor profile has a "Delete" option accessible within the admin dashboard.
2. A confirmation dialog appears asking the admin to confirm the deletion to prevent accidental clicks.
3. Once confirmed, the doctor's profile is considered removed within the database, and they can no longer log in.
4. The UI updates immediately to reflect that the doctor's profile is no longer listed.

**Priority:** Medium
**Story Points:** 3

**Notes:**
- Implement a "Soft Delete" (deactivating the account) instead of a permanent purge if data integrity for historical appointments is required.

### Admin User Story: Get Statistics

**Title:**
_As an Admin, I want to run a stored procedure via MySQL CLI, so that I can track monthly appointment statistics and platform usage._

**Acceptance Criteria:**
1. The administrator can successfully execute the specific stored procedure using the MySQL Command Line Interface (CLI).
2. The procedure returns a clear list showing the number of appointments grouped by month.
3. The output is accurate and reflects the current data present in the database.
4. The command runs without errors or syntax failures within the CLI environment.

**Priority:** Low
**Story Points:** 5

**Notes:**
- This is a technical task. testing should involve verifying the SQL logic against known appointment datasets.
- Ensure the admin has the necessary database permissions to execute procedures.

## Patient
### Patient User Story: View Doctors List Without Logging In
**Title:**
_As a Patient, I want to view a list of doctors without logging in, so that I can explore available specialists before deciding to register._

**Acceptance Criteria:**
1. A public-facing page or section is accessible without authentication.
2. The page displays a list of doctors including their names and specialties.
3. The list updates automatically when new doctors are added to the system by an admin.
4. The page loads quickly without requiring any user session data.

**Priority:** Medium
**Story Points:** 2

**Notes:**
- Avoid displaying sensitive information (like private contact details) on the public view; only show professional profiles.

### Patient User Story: Sign up
**Title:**
_As a patient, I want to sign up using my email and password, so that I can create an account and begin booking appointments._

**Acceptance Criteria:**
1. The registration form includes fields for email address, password and other optional data.
2. The system validates that the email is in a correct format and is not already registered.
3. The system enforces password strength requirements (e.g., minimum length).
4. Upon successful registration, a new patient record is created in the database and the user is redirected to the login page or dashboard.

**Priority:** High
**Story Points:** 5

**Notes:**
- Email verification step in the future to prevent fake accounts.

### Patient User Story: Log In
**Title:**
_As a Patient, I want to log into the portal, so that I can access my dashboard and manage my existing appointments._

**Acceptance Criteria:**
1. The login form accepts email and password.
2. Upon successful authentication, the user is redirected to their personalized patient dashboard.
3. An error message is displayed for incorrect credentials.
4. The user's session is securely maintained while navigating the portal until they log out.

**Priority:** High
**Story Points:** 3

**Notes:**
- Ensure this follows the same security protocols established in the Admin login story.

### Patient User Story: Log Out 
**Title:**
_As a Patient, I want to log out of the portal, so that I can secure my account and protect my personal health information._

**Acceptance Criteria:**
1. A "Logout" button is clearly visible within the logged-in user's dashboard.
2. Clicking logout destroys the current session and clears authentication tokens.
3. The user is redirected to the public home page or login page upon logout.
4. The user cannot access their "My Appointments" page after logging out using browser navigation.

**Priority:** High
**Story Points:** 1

**Notes:**
- Essential data privacy standards.

### Patient User Story: Book an Appointment
**Title:**
_As a Patient, I want to book an hour-long appointment with a doctor, so that I can receive medical consultation._

**Acceptance Criteria:**
1. The user must be logged in to book an appointment.
2. The user can select a specific doctor from the available list.
3. The system provides a way to select a date and time slot.
4. The system prevents "double-booking" (the same slot cannot be taken by two different patients).
5. Upon successful booking, a success message appears and the appointment is saved to the database, linked to the patient's and doctor's account respectively.

**Priority:** High
**Story Points:** 8

**Notes:**
- This is a complex story involving scheduling logic and conflict prevention. 
- Edge case: Handle time-zone differences if the platform expands geographically.

### Patient User Story: View Upcoming Appointments
**Title:**
_As a Patient, I want to view my upcoming appointments, so that I can prepare for my consultations accordingly._

**Acceptance Criteria:**
1. The user must be logged in.
2. A "My Appointments" section is available on the patient dashboard.
3. The list only displays appointments that have not yet passed (future dates).
4. Each entry clearly shows the doctor's name, the date, and the scheduled time.
5. If no upcoming appointments exist, a friendly message is displayed (e.g., "You have no upcoming appointments").

**Priority:** Medium
**Story Points:** 3

**Notes:**
- Ensure that patients can only see their **own** appointments and not those of other users.

## Doctor
### Doctor User Story: Log In
**Title:**
_As a Doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**
1. The system provides a login form for doctors to enter their credentials.
2. Successful authentication directs the doctor to their personalized dashboard.
3. An error message is displayed if the email or password entered is incorrect.
4. The session must be secure to protect sensitive professional information.

**Priority:** High
**Story Points:** 3

**Notes:**
- This follows the same implementation logic as the Admin and Patient login stories.

### Doctor User Story: Log Out
**Title:**
_As a Doctor, I want to log out of the portal, so that I can protect my professional data and account security._

**Acceptance Criteria:**
1. A "Logout" button is clearly accessible from the doctor's dashboard.
2. Clicking logout terminates the current session immediately.
3. The user is redirected to the login screen or public home page.
4. Authentication tokens are cleared so that unauthorized users cannot regain access via the browser history.

**Priority:** High
**Story Points:** 1

**Notes:**
- Crucial for maintaining data privacy compliance in a medical context.

### Doctor User Story: View My Appointments 
**Title:**
_As a Doctor, I want to view my appointment calendar, so that I can stay organized and visualize my upcoming workload._

**Acceptance Criteria:**
1. The system provides a calendar interface (daily, weekly, or monthly view).
2. Each scheduled appointment is clearly visible on the corresponding date and time.
3. Clicking on an appointment entry shows brief summary details (e.g., Patient Name, Time).
4. The calendar distinguishes between available slots and booked appointments.

**Priority:** High
**Story Points:** 5
- Integration with a calendar library is expected.

### Doctor User Story: Mark Unavailability
**Title:**
_As a Doctor, I want to mark my unavailability in the system, so that patients are only able to book available time slots._

**Acceptance Criteria:**
1. The doctor can select specific dates or time ranges and mark them as "Unavailable."
2. Once marked, these slots are automatically removed from the patient's view during the booking process.
3. The doctor can view their blocked/unavailable slots on their own calendar.
4. The doctor has the ability to remove "unavailability" if they become available again.

**Priority:** High
**Story Points:** 5

**Notes:**
- This is a critical feature to prevent scheduling conflicts.

### Doctor User Story: Update Professional Profile
**Title:**
_As a Doctor, I want to update my profile with my specialization and contact information, so that patients have up-to-date and accurate information._

**Acceptance Criteria:**
1. The doctor can access an "Edit Profile" section within the portal.
2. Fields for "Specialization" and "Contact Information" are editable.
3. Changes are saved to the database upon clicking a "Save" or "Update" button.
4. Once updated, the new information is immediately reflected on the public-facing doctor list seen by patients.

**Priority:** Medium
**Story Points:** 3

**Notes:**
- Ensure validation is in place to prevent empty or malformed contact information.

### Doctor User Story: View Patient Details
**Title:**
_As a Doctor, I want to view patient details for my upcoming appointments, so that I can be prepared for each consultation._

**Acceptance Criteria:**
1. The doctor can click on an upcoming appointment in their calendar or list.
2. The system displays the specific patient's information (e.g., Name, Contact Info).
3. The view is limited only to patients who have successfully booked a slot with that specific doctor.
4. The information is presented in a clear, easy-to-read format for quick reference during consultations.

**Priority:** Medium
**Story Points:** 3

**Notes:**
- Ensure doctors can **only** see details for patients they are actually scheduled to meet.

  
