function pageLoad() {

    document.getElementById("bookingDiv").style.display = 'none';
    //Hides the bookingDiv
    let mySchedule = '<table>'
        + '<tr>'
        + '<th style="width:100px">Timing</th>'
        + '<th style="width:150px">Monday</th>'
        + '<th style="width:150px">Tuesday</th>'
        + '<th style="width:150px">Wednesday</th>'
        + '<th style="width:150px">Thursday</th>'
        + '<th style="width:150px">Friday</th>'
        + '<th style="width:150px">Saturday</th>'
        + '</tr>';
    //Creates the table that all the retrieved information will be put on

    fetch('/ScheduleController/ListAllBookings', {method:'get'}
    ).then(response => response.json()
    ).then(bookings => {
        // Calls the 'ListAllBookings' API call and places the retrieved information, in JSON format, in 'bookings'

            for (let i = 9; i < 19; i++) {
                // Repeats for each hour of the day
                let bookingArray = [];
                // yeee
                let bookingIDArray = [];
                let bookingColourArray = [];
                for(let j = 1; j < 7; j++){
                    // Repeats for each day of the week
                    for(let booking of bookings){
                        // Looks through each booking object inside the bookings array
                        if((booking.day == j) && (booking.time == i)){
                            // If the booking is of this time and day
                            bookingArray[j-1] = "Booked";

                            bookingIDArray[j-1] = booking.bookingID;

                            bookingColourArray[j-1] = "green";

                            // Populates three new arrays for each day of a specific timing
                        }
                    }
                    if (bookingArray[j-1] == null){
                        // If there is no booking at this time
                        bookingArray[j-1] = "Empty";
                        bookingIDArray[j-1] = -1;
                        bookingColourArray[j-1] = "red";
                    }
                }

                mySchedule += `<tr>` +
                    `<td>${i}:00</td>` +
                    `<td style="background-color: ${bookingColourArray[0]}"><button class="scheduleButton" data-id="${bookingIDArray[0]}" time-id="${i}:00 - ${i+1}:00">${bookingArray[0]}</button></td>` +
                    `<td style="background-color: ${bookingColourArray[1]}"><button class="scheduleButton" data-id="${bookingIDArray[1]}" time-id="${i}:00 - ${i+1}:00">${bookingArray[1]}</button></td>` +
                    `<td style="background-color: ${bookingColourArray[2]}"><button class="scheduleButton" data-id="${bookingIDArray[2]}" time-id="${i}:00 - ${i+1}:00">${bookingArray[2]}</button></td>` +
                    `<td style="background-color: ${bookingColourArray[3]}"><button class="scheduleButton" data-id="${bookingIDArray[3]}" time-id="${i}:00 - ${i+1}:00">${bookingArray[3]}</button></td>` +
                    `<td style="background-color: ${bookingColourArray[4]}"><button class="scheduleButton" data-id="${bookingIDArray[4]}" time-id="${i}:00 - ${i+1}:00">${bookingArray[4]}</button></td>` +
                    `<td style="background-color: ${bookingColourArray[5]}"><button class="scheduleButton" data-id="${bookingIDArray[5]}" time-id="${i}:00 - ${i+1}:00">${bookingArray[5]}</button></td>` +
                    `</tr>`;
                // Populates the table for a timing with each booking
            }
            mySchedule += '</table>';
            // Ends the table
            document.getElementById("tableDiv").innerHTML = mySchedule;
            // Makes the tableDiv on index.html the table
            let scheduleButtons = document.getElementsByClassName("scheduleButton");
            for(let button of scheduleButtons){
                button.addEventListener("click", book);
            }
            // Prepares the event handlers for each Book button
    });
    checkLogin();
}

function book(event) {
    const id = event.target.getAttribute("data-id");
    const time = event.target.getAttribute("time-id");
    // Creates a variable for the specific bookingID
    document.getElementById("time").innerHTML = time;
    if (id == -1) {
        // If there is no bookingID
        document.getElementById("description").innerHTML = 'No Current Booking';
        document.getElementById("slots").innerHTML = "";
        document.getElementById("bookingDiv").style.display = 'block';

    } else {
        // If there is a bookingID
        fetch('/BookingsController/ListBookings/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(booking => {
            let count = 0;
            fetch("/PersonalBookingsController/ListAllUsersBookings", {method: 'get'}
            ).then(response => response.json()
            ).then(users => {

                if (booking.hasOwnProperty('error')) {
                    alert(booking.error);
                    // Error checking
                } else {
                    document.getElementById("description").innerHTML = booking.description;
                    document.getElementById("bookingDiv").style.display = 'block';


                for (let user in users) {
                    if (booking.bookingID == user.bookingID) {
                        count = count + 1;
                        console.log(count);
                    }
                }
                if (booking.bookingType === 1) {
                    // If it is an event (unlimited bookings)
                    document.getElementById("description").innerHTML = booking.description;
                    document.getElementById("slots").innerHTML = "No Limit";
                    document.getElementById("bookButtonDiv").style.display = 'block';
                }
                if (booking.bookingType === 2) {
                    // If it is a course (limited bookings)
                    console.log("yee");
                    if (booking.slots - count <= 0) {
                        document.getElementById("description").innerHTML = booking.description;
                        document.getElementById("slots").innerHTML = "Fully Booked";
                        document.getElementById("bookButtonDiv").style.display = 'none';
                    } else {
                        let slotNo = (booking.slots - count);
                        console.log(slotNo);
                        document.getElementById("description").innerHTML = booking.description;
                        document.getElementById("bookButtonDiv").style.display = 'block';
                        document.getElementById("slots").innerHTML = slotNo + " Slots left";

                    }
                }
                if (booking.bookingType === 3) {
                    // If it is freeplay (fully booked)
                    document.getElementById("description").innerHTML = booking.description;
                    document.getElementById("slots").innerHTML = "Fully Booked";
                    document.getElementById("bookButtonDiv").style.display = 'none';
                }
                }
                // Fetches the booking information for that bookingID


            });
            document.getElementById("bookButton").addEventListener("click", bookBooking);
            // Prepares event handler for button
        });
    }
}


function bookBooking() {

}

function checkLogin() {

    let email = Cookies.get("email");
    let name = Cookies.get("firstName");
    let logInHTML = '';

    if (email === undefined) {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "hidden";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "hidden";
        }

        logInHTML = "Not logged in. <a href='/client/login.html'>Log in</a>";
    } else {

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.style.visibility = "visible";
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.style.visibility = "visible";
        }

        logInHTML = "Logged in as " + name + ". <a href='/client/login.html?logout'>Log out</a>";

    }



    document.getElementById("loggedInDetails").innerHTML = logInHTML;

}



