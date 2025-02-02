$(document).ready(function () {
	
	
	
	if ($('#successMessage').text().trim() !== '') {
	        $('#successMessage').removeClass('d-none');
	        setTimeout(() => {
	            $('#successMessage').fadeOut(); // Hide message after 3 seconds
	        }, 3000);
	    }
	
	
	
	
	
    //let selectedServices = ["Haircut", "Facial"]; // Replace with backend data

    // Enable editing of fields
    $("#editButton").click(function () {
        $("#profileForm input, #servicesDropdown, #addService").prop("disabled", false);
        $("#editButton").addClass("d-none");
        $("#saveButton").removeClass("d-none");
    });

    // Pre-fill existing services
    renderServices();

    // Handle adding services
    $("#addService").click(function () {
        let service = $("#servicesDropdown").val();
        if (service && !selectedServices.includes(service)) {
            selectedServices.push(service);
            renderServices();
        }
    });

    // Function to render selected services
    function renderServices() {
        $("#servicesList").empty();
        selectedServices.forEach(service => {
            let serviceItem = `
                <button class="btn btn-dark btn-sm m-1 service-btn" data-service="${service}">
                    ${service} <i class="bi bi-x-circle remove-service"></i>
                </button>`;
            $("#servicesList").append(serviceItem);
        });
    }

    // Handle removing services
    $(document).on("click", ".remove-service", function () {
        let service = $(this).closest("button").data("service");
        selectedServices = selectedServices.filter(s => s !== service);
        renderServices();
    });

    // Real-time validation on blur
    $("#name").blur(function () {
        validateField($(this), $("#nameError"), "Name is required.");
    });

    $("#email").blur(function () {
        validateEmail($(this), $("#emailError"));
    });

    $("#phone").blur(function () {
        validatePhone($(this), $("#phoneError"));
    });

    $("#pincode").blur(function () {
        validatePincode($(this), $("#pincodeError"));
    });

    // Ensure at least one service is selected
    function validateServices() {
        if (selectedServices.length === 0) {
            $("#serviceError").removeClass("d-none");
            return false;
        }
        $("#serviceError").addClass("d-none");
        return true;
    }

    // Validate form fields
    function validateField(input, errorElement, errorMessage) {
        if (!input.val().trim()) {
            errorElement.text(errorMessage).removeClass("d-none");
            return false;
        } else {
            errorElement.addClass("d-none");
            return true;
        }
    }

    function validateEmail(input, errorElement) {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(input.val().trim())) {
            errorElement.text("Please enter a valid email.").removeClass("d-none");
            return false;
        } else {
            errorElement.addClass("d-none");
            return true;
        }
    }

    function validatePhone(input, errorElement) {
        const phonePattern = /^[6-9][0-9]{9}$/;
        if (!phonePattern.test(input.val().trim())) {
            errorElement.text("Phone number must start with 6, 7, 8, or 9 and be 10 digits.").removeClass("d-none");
            return false;
        } else {
            errorElement.addClass("d-none");
            return true;
        }
    }

    function validatePincode(input, errorElement) {
        if (input.val().trim().length !== 6) {
            errorElement.text("Pincode must be 6 digits.").removeClass("d-none");
            return false;
        } else {
            errorElement.addClass("d-none");
            return true;
        }
    }

    // Handle form submission
    $("#profileForm").submit(function (e) {
        e.preventDefault();
        let valid = true;

        // Validate all fields
        valid &= validateField($("#name"), $("#nameError"), "Name is required.");
        valid &= validateEmail($("#email"), $("#emailError"));
        valid &= validatePhone($("#phone"), $("#phoneError"));
        valid &= validatePincode($("#pincode"), $("#pincodeError"));
        valid &= validateServices();

        if (!valid) return;
		
        // Disable fields after saving
        $("#profileForm input, #servicesDropdown, #addService").prop("disabled", true);
        $("#editButton").removeClass("d-none");
        $("#saveButton").addClass("d-none");
        $("#successMessage").removeClass("d-none").text("Profile updated successfully!");
    });

    // Handle Delete Account
    $("#deleteAccountButton").click(function () {
        const email = prompt("Please enter your registered email to confirm deletion:");
        if (email === $("#email").val()) {
            alert("Account deleted successfully.");
            window.location.href = "index.html";
        } else {
            alert("Email does not match!");
        }
    });
	
	$('#saveButton').on('click', function () {
	        // Enable all form fields before submission
	        $('#profileForm input, #servicesDropdown').prop('disabled', false);
			$("#successMessage").removeClass("d-none").text("Profile updated successfully!");
	        // Submit the form programmatically
	        document.getElementById('profileForm').submit();
	    });
	
});