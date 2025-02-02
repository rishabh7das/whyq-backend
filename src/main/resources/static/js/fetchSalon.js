document.getElementById("pincodeForm").addEventListener("submit", function(e) {
    e.preventDefault();
    const pincode = document.getElementById("pincode").value;

    // Mock AJAX to fetch salon data
    const salons = [
        { name: "Glamorous Beauty", address: "123, Main Street", contact: "9876543210" },
        { name: "Elegant Spa", address: "456, Central Road", contact: "8765432109" },
        { name: "GoodLuck salon", address: "456, Bokaro Road", contact: "7654325109" },
        { name: "Classic Men's Parlor", address: "19, Harihar Singh Road", contact: "8854325109" }
    ];

    let salonHTML = "<h4 class='mt-4'>Available Salons:</h4><ul class='list-group'>";
    salons.forEach(salon => {
        salonHTML += `
            <li class="list-group-item">
                <strong>${salon.name}</strong><br>
                Address: ${salon.address}<br>
                Contact: ${salon.contact}
                <button class="btn btn-sm btn-dark float-right book-btn">Book</button>
            </li>
        `;
    });

    salonHTML += "</ul>";
    document.getElementById("salonList").innerHTML = salonHTML;

    // Add event listeners to the "Book" buttons after inserting the HTML
    document.querySelectorAll('.book-btn').forEach(button => {
        button.addEventListener('click', () => {
            window.location.href = 'appointmentBooking.html';
        });
    });
});