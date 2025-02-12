document.getElementById("editButton").addEventListener("click", function () {
    document.getElementById("name").disabled = false;
    document.getElementById("email").disabled = false;
    document.getElementById("phone").disabled = false;

    document.getElementById("editButton").classList.add("d-none");
    document.getElementById("saveButton").classList.remove("d-none");
});

// Disable fields and show success message when "Save Changes" is clicked
document.getElementById("profileForm").addEventListener("submit", function (e) {
    e.preventDefault();

    let valid = validateForm();

    if (valid) {
        document.getElementById("name").disabled = true;
        document.getElementById("email").disabled = true;
        document.getElementById("phone").disabled = true;

        document.getElementById("editButton").classList.remove("d-none");
        document.getElementById("saveButton").classList.add("d-none");
        document.getElementById("successMessage").classList.remove("d-none");
    }
});

// Real-time validation
document.getElementById("name").addEventListener("blur", function () {
    if (!this.value.trim()) {
        document.getElementById("nameError").classList.remove("d-none");
    } else {
        document.getElementById("nameError").classList.add("d-none");
    }
});

document.getElementById("email").addEventListener("blur", function () {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(this.value.trim())) {
        document.getElementById("emailError").classList.remove("d-none");
    } else {
        document.getElementById("emailError").classList.add("d-none");
    }
});

document.getElementById("phone").addEventListener("blur", function () {
    const phonePattern = /^[6-9][0-9]{9}$/;
    if (!phonePattern.test(this.value.trim())) {
        document.getElementById("phoneError").classList.remove("d-none");
    } else {
        document.getElementById("phoneError").classList.add("d-none");
    }
});

// Delete Account Button Logic
document.getElementById("deleteAccountButton").addEventListener("click", function () {
    const email = prompt("Please enter your registered email to confirm deletion:");
    if (email === document.getElementById("email").value) {
        alert("Account deleted successfully.");
        // Redirect to a logout or homepage
        window.location.href = "index.html";
    } else {
        alert("Email does not match!");
    }
});

// Validate form fields
function validateForm() {
    let valid = true;

    // Validate Name
    if (!document.getElementById("name").value.trim()) {
        document.getElementById("nameError").classList.remove("d-none");
        valid = false;
    } else {
        document.getElementById("nameError").classList.add("d-none");
    }

    // Validate Email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(document.getElementById("email").value.trim())) {
        document.getElementById("emailError").classList.remove("d-none");
        valid = false;
    } else {
        document.getElementById("emailError").classList.add("d-none");
    }

    // Validate Phone Number
    const phonePattern = /^[6-9][0-9]{9}$/;
    if (!phonePattern.test(document.getElementById("phone").value.trim())) {
        document.getElementById("phoneError").classList.remove("d-none");
        valid = false;
    } else {
        document.getElementById("phoneError").classList.add("d-none");
    }

    return valid;
}