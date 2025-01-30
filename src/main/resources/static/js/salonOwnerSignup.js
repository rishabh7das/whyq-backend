$(document).ready(function () {
    const selectedServices = [];

    // Add service
    $('#addService').click(function () {
        const service = $('#servicesDropdown').val();
        if (service && !selectedServices.includes(service)) {
            selectedServices.push(service);
            renderServices();
        }
    });

    // Render services
    function renderServices() {
        $('#selectedServices').html('');
        selectedServices.forEach(service => {
            $('#selectedServices').append(
                `<div class="btn btn-dark service-btn">${service} <span class="remove-btn" onclick="removeService('${service}')">&times;</span></div>`
            );
        });
        $('#servicesInput').val(selectedServices.join(','));
    }

    // Remove service
    window.removeService = function (service) {
        const index = selectedServices.indexOf(service);
        if (index > -1) {
            selectedServices.splice(index, 1);
            renderServices();
        }
    };

    // Validation on blur
    $('#salonName, #ownerName, #email, #contactNumber, #address, #pincode').on('blur', function () {
        validateField($(this));
    });

    // Form submission
    $('#salonForm').on('submit', function (e) {
        e.preventDefault();
        let isValid = true;
        $('#salonName, #ownerName, #email, #contactNumber, #address, #pincode').each(function () {
            isValid = validateField($(this)) && isValid;
        });

        if (selectedServices.length === 0) {
            $('#servicesError').removeClass('d-none');
            isValid = false;
        } else {
            $('#servicesError').addClass('d-none');
        }

        if (isValid) {
            alert('Salon registered successfully!');
            window.location.href = 'ownerDashboard';
        }
    });

    // Validate individual field
    function validateField(field) {
        const value = field.val().trim();
        let valid = true;

        if (field.attr('id') === 'email') {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(value)) {
                showError(field, 'emailError');
                valid = false;
            } else {
                hideError(field, 'emailError');
            }
        } else if (field.attr('id') === 'contactNumber') {
            const phonePattern = /^[6-9][0-9]{9}$/;
            if (!phonePattern.test(value)) {
                showError(field, 'contactNumberError');
                valid = false;
            } else {
                hideError(field, 'contactNumberError');
            }
        } else if (field.attr('id') === 'pincode') {
            const pincodePattern = /^[0-9]{6}$/;
            if (!pincodePattern.test(value)) {
                showError(field, 'pincodeError');
                valid = false;
            } else {
                hideError(field, 'pincodeError');
            }
        } else if (!value) {
            showError(field, `${field.attr('id')}Error`);
            valid = false;
        } else {
            hideError(field, `${field.attr('id')}Error`);
        }

        return valid;
    }

    // Show error
    function showError(field, errorId) {
        $('#' + errorId).removeClass('d-none');
    }

    // Hide error
    function hideError(field, errorId) {
        $('#' + errorId).addClass('d-none');
    }
});