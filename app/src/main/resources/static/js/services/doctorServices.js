import { API_BASE_URL } from "../config/config.js";

const DOCTOR_API = API_BASE_URL + '/doctor'

export async function getDoctors() {
  try {
    const response = await fetch(DOCTOR_API);
    const data = await response.json();
    return data.doctors || [];
  } catch (error) {
    console.error("Error fetching doctors:", error);
    return [];
  }
}

export async function deleteDoctor(doctorId, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/delete/${doctorId}/${token}`, {
      method: "DELETE"
    });

    const data = await response.json();
    return {
      success: response.ok,
      message: data.message
    };
  } catch (error) {
    console.error("Error deleting doctor:", error);
    return {
      success: false,
      message: "An unexpected error occurred."
    };
  }
}

export async function saveDoctor(doctor, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/create/${token}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(doctor)
    });

    const data = await response.json();
    return {
      success: response.ok,
      message: data.message
    };
  } catch (error) {
    console.error("Error saving doctor:", error);
    return {
      success: false,
      message: "Unable to save doctor. Please try again."
    };
  }
}

export async function filterDoctors(name = "", time = "", specialty = "") {
  try {
    const response = await fetch(`${DOCTOR_API}/filter/${name}/${time}/${specialty}`);

    if (!response.ok) {
      console.error("Filter request failed");
      return { doctors: [] };
    }

    const data = await response.json();
    return data.doctors || [];
  } catch (error) {
    console.error("Error filtering doctors:", error);
    alert("Something went wrong while filtering doctors.");
    return { doctors: [] };
  }
}
