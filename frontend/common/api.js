const BASE_URL = "http://localhost:8080";

async function apiCall(endpoint, method = "GET", body = null) {
  const token = localStorage.getItem("token");

  const response = await fetch(BASE_URL + endpoint, {
    method,
    headers: {
      "Content-Type": "application/json",
      "Authorization": token ? "Bearer " + token : ""
    },
    body: body ? JSON.stringify(body) : null
  });

  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || "API Error");
  }

  return response.json();
}
