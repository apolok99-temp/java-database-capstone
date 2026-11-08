
function renderHeader() {
  const headerDiv = document.getElementById("header");

  if (!headerDiv) {
    console.error("Element with ID 'header' not found.");
    return;
  }

  if (window.location.pathname.endsWith("/")) {
    localStorage.removeItem("userRole");
    localStorage.removeItem("token");
    headerDiv.innerHTML = `
            <header class="header">
              <a href="/" class="logo-link" style="text-decoration: none;>
                <div class="logo-section">
                  <img src="../assets/images/logo/logo.png" alt="Hospital CRM Logo" class="logo-img">
                  <span class="logo-title">Hospital CMS</span>
                </div>
              </a>
            </header>`;
        return;
  }

  const role = localStorage.getItem("userRole");
  const token = localStorage.getItem("token");

  let headerContent = `
          <header class="header">
            <a href="/" class="logo-link" style="text-decoration: none;>
              <div class="logo-section">
                <img src="../assets/images/logo/logo.png" alt="Hospital CRM Logo" class="logo-img">
                <span class="logo-title">Hospital CMS</span>
              </div>
            </a>
      <nav>`;

  if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
    localStorage.removeItem("userRole");
    alert("Session expired or invalid login. Please log in again.");
    window.location.href = "/";
    return;
  }

  if (role === "admin") {
      headerContent += `
        <button id="addDocBtn" class="adminBtn">Add Doctor</button>
        <a href="#" onclick="logout()">Logout</a>`;
  } else if (role === "doctor") {
        headerContent += `
          <button class="adminBtn" onclick="selectRole('doctor')">Home</button>
          <a href="#" onclick="logout()">Logout</a>`;
  } else if (role === "patient") {
        headerContent += `
          <button id="patientLogin" class::adminBtn">Login</button>
          <button id="patientSignup" class="adminBtn">Sign Up</button>`;
  } else if (role === "loggedPatient") {
        headerContent += `
          <button id="home" class="adminBtn" onclick="window.location.href='/pages/loggedPatientDashboard.html'">Home</button>
          <button id="patientAppointments" class="adminBtn" onclick="window.location.href='/pages/patientAppointments.html'">Appointments</button>
          <a href="#" onclick="logoutPatient()">Logout</a>`;
  }

  headerContent += `</nav></header>`;
  headerDiv.innerHTML = headerContent;

  attachHeaderButtonListeners();

}

// --- Helper Functions ---

/**
 * Removes user session data and redirects to the root page.
 */
function logout() {
  localStorage.removeItem("userRole");
  localStorage.removeItem("token");
  window.location.href = "/";
}

/**
 * Removes patient token and redirects to a specific dashboard or home.
 */
function logoutPatient() {
  localStorage.removeItem("token");
  localStorage.setItem("userRole", "patient");
  window.location.href = "/pages/patientDashboard.html";
}

/**
 * Adds event listeners to dynamically created buttons in the header.
 */
function attachHeaderButtonListeners() {
  const doctorBtn = document.getElementById("btn-doctor");
  if (doctorBtn) {
    doctorBtn.addEventListener("click", () => {
      localStorage.removeItem("token");
      openModal("doctorLogin");
    });
  }

  const adminBtn = document.getElementById("btn-admin");
  if (adminBtn) {
    adminBtn.addEventListener("click", () => {
      localStorage.removeItem("token");
      openModal("adminLogin");
    });
  }
}

renderHeader();
