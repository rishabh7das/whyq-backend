/*document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    let valid = validateForm();

    if (valid) {
        const role = document.querySelector('input[name="role"]:checked').value; // Get selected role
        if (role === "user") {
            window.location.href = "/login"; // Redirect to fetch salon page
        } else if (role === "salonOwner") {
            window.location.href = "ownerDashboard.html"; // Redirect to salon owner dashboard
        }
    }
});*/

// Real-time validation
document.getElementById("email").addEventListener("blur", function () {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(this.value.trim())) {
        document.getElementById("emailError").classList.remove("d-none");
    } else {
        document.getElementById("emailError").classList.add("d-none");
    }
});

document.getElementById("password").addEventListener("blur", function () {
    if (this.value.trim().length < 6) {
        document.getElementById("passwordError").classList.remove("d-none");
    } else {
        document.getElementById("passwordError").classList.add("d-none");
    }
});

// Validate form fields
function validateForm() {
    let valid = true;

    // Validate Email
    const emailField = document.getElementById("email");
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(emailField.value.trim())) {
        document.getElementById("emailError").classList.remove("d-none");
        valid = false;
    } else {
        document.getElementById("emailError").classList.add("d-none");
    }

    // Validate Password
    const passwordField = document.getElementById("password"); 
    //const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/; 
	const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z]){6,}$/; 
    /*if (!passwordPattern.test(passwordField.value.trim())) 
        { document.getElementById("passwordError").classList.remove("d-none"); valid = false; } 
    else { document.getElementById("passwordError").classList.add("d-none");}
*/
    return valid;
}