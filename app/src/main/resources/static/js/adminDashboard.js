
import { openModal } from './components/modals.js';
import { getDoctors  , filterDoctors , saveDoctor } from './services/doctorServices.js';
import { createDoctorCard } from './components/doctorCard.js';

document.getElementById('addDocBtn').addEventListener('click', () => {
  openModal('addDoctor');
});

document.addEventListener("DOMContentLoaded", () => {
  loadDoctorCards();
});

export function loadDoctorCards() {
  getDoctors()
    .then(doctors => {
      const contentDiv = document.getElementById("content");
      contentDiv.innerHTML = ""; 

      doctors.forEach(doctor => {
        const card = createDoctorCard(doctor);
        contentDiv.appendChild(card);
      });
    })
    .catch(error => {
      console.error(" Failed to load doctors:", error);
    });
}

document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar")?.value.trim() || "";
  const time = document.getElementById("timeFilter")?.value || "";
  const specialty = document.getElementById("specialtyFilter")?.value || "";

  try {
    const result = await filterDoctors(name, time, specialty);

    if (result.length > 0) {
      renderDoctorCards(result);
    } else {
      document.getElementById("content").innerHTML = `<p class="noDoctorMsg">No doctors found.</p>`;
    }
  } catch (err) {
    console.error("Error filtering doctors:", err);
    alert("Failed to filter doctors. Please try again.");
  }
}

function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  doctors.forEach((doctor) => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}

window.adminAddDoctor = async function () {
  const name = document.getElementById("docName")?.value.trim();
  const email = document.getElementById("docEmail")?.value.trim();
  const phone = document.getElementById("docPhone")?.value.trim();
  const password = document.getElementById("docPassword")?.value.trim();
  const specialty = document.getElementById("docSpecialty")?.value.trim();
  const availableTimesInput = document.getElementById("docAvailableTimes")?.value.trim();

  if (!name || !email || !phone || !password || !specialty || !availableTimesInput) {
    alert("Please fill in all fields.");
    return;
  }

  const availableTimes = availableTimesInput.split(",").map(t => t.trim());
  const token = localStorage.getItem("token");

  if (!token) {
    alert("Session expired. Please log in again.");
    window.location.href = "/";
    return;
  }

  const doctor = {
    name,
    email,
    phone,
    password,
    specialty,
    availableTimes
  };

  try {
    const result = await saveDoctor(doctor, token);

    if (result.success) {
      alert("Doctor added successfully!");
      closeModal("addDoctor");
      loadDoctorCards();
    } else {
      alert(`Failed to add doctor: ${result.message}`);
    }
  } catch (err) {
    console.error("Error adding doctor:", err);
    alert("An error occurred. Please try again.");
  }
};
