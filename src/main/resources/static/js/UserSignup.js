$(document).ready(function () {
    // Validate on blur
    $('#name, #email, #password, #phone_number').on('blur', function () {
        validateField($(this));
    });

    // Form submission
    /*$('#signUpForm').on('submit', function (e) {
        e.preventDefault();
        let isValid = true;

        // Validate all fields
        $('#name, #email, #password, #phone_number').each(function () {
            isValid = validateField($(this)) && isValid;
        });

        if (isValid) {
            alert('Sign up successful!');
            window.location.href = '/registerUser';
        }
    });*/

    // Validate individual field
    function validateField(field) {
        const value = field.val().trim();
        let valid = true;

        if (field.attr('id') === 'name') {
            if (value === '') {
                showError(field, 'nameError');
                valid = false;
            } else {
                hideError(field, 'nameError');
            }
        } else if (field.attr('id') === 'email') {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(value)) {
                showError(field, 'emailError');
                valid = false;
            } else {
                hideError(field, 'emailError');
            }
        } else if (field.attr('id') === 'password') {
            if (value.length < 6) {
                showError(field, 'passwordError');
                valid = false;
            } else {
                hideError(field, 'passwordError');
            }
        } else if (field.attr('id') === 'phone_number') {
            const phonePattern = /^[6-9][0-9]{9}$/;
            if (!phonePattern.test(value)) {
                showError(field, 'phoneError');
                valid = false;
            } else {
                hideError(field, 'phoneError');
            }
        }

        return valid;
    }

    // Show error message
    function showError(field, errorId) {
        $('#' + errorId).removeClass('d-none');
    }

    // Hide error message
    function hideError(field, errorId) {
        $('#' + errorId).addClass('d-none');
    }
});