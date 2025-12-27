const passwordInput = document.getElementById("password");
const passwordStatus = document.getElementById("passwordStatus");

const passwordRegex =
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{5,12}$/;

// Live password validation
passwordInput.addEventListener("input", function () {
  const value = passwordInput.value;

  if (value.length === 0) {
    passwordStatus.innerText = "";
    return;
  }

  if (passwordRegex.test(value)) {
    passwordStatus.innerText = "✔ Strong password";
    passwordStatus.className = "text-success small mt-1";
  } else {
    passwordStatus.innerText =
      "✖ Password must be 5–12 chars with uppercase, lowercase, number & special character";
    passwordStatus.className = "text-danger small mt-1";
  }
});

function togglePassword(id) {
  const input = document.getElementById(id);
  input.type = input.type === "password" ? "text" : "password";
}

document.getElementById("registerForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{5,12}$/;

  if (!passwordRegex.test(password)) {
    showMessage(
      "Password must be 5–12 chars and include uppercase, lowercase, number & special character",
      "danger"
    );
    return;
  }

  if (password !== confirmPassword) {
    showMessage("Passwords do not match", "danger");
    return;
  }

  const data = {
    name: document.getElementById("name").value,
    email: document.getElementById("email").value,
    phone: document.getElementById("phone").value,
    role: document.getElementById("role").value,
    password: password,
    address: {
      street: document.getElementById("street").value,
      city: document.getElementById("city").value,
      state: document.getElementById("state").value,
      zip: document.getElementById("zip").value
    }
  };


  showMessage(
    "Registration successful! Username sent to your email.",
    "success"
  );
});

function showMessage(text, type) {
  const msg = document.getElementById("registerMessage");
  msg.className = `text-${type} text-center mt-3`;
  msg.innerText = text;
}

