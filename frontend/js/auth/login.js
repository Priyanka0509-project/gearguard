function togglePassword(id) {
  const input = document.getElementById(id);
  input.type = input.type === "password" ? "text" : "password";
}

document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  // Backend will handle real auth
  if (username === "user" && password === "User@123") {
    alert("Logged in as User");
  } else {
    document.getElementById("loginError").innerText =
      "Invalid username or password";
  }
});
