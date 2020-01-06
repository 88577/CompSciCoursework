function pageLoad() {

    let now = new Date();

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

    fetch('/ScheduleController/ListAllBookings', {method:'get'}
    ).then(response => response.json()
    ).then(bookings => {

            for (let i = 1; i < 19; i++) {
                let bookingArray = [];
                for(let j = 1; j < 7; j++){
                    for(let booking of bookings){
                        if((booking.day == j) && (booking.time == i)){
                            bookingArray[j-1] = "Booked";

                        }
                    }
                    if (bookingArray[j-1] == null){
                        bookingArray[j-1] = "Empty";
                    }
                }



                mySchedule += `<tr>` +
                    `<td>${i}:00</td>` +
                    `<td>${bookingArray[0]}</td>` +
                    `<td>${bookingArray[1]}</td>` +
                    `<td>${bookingArray[2]}</td>` +
                    `<td>${bookingArray[3]}</td>` +
                    `<td>${bookingArray[4]}</td>` +
                    `<td>${bookingArray[5]}</td>` +
                    `</tr>`;
            }
            mySchedule += '</table>';

            document.getElementById("testDiv").innerHTML = mySchedule;
    });



}

function checkLogin() {

    let email = Cookies.get("email");

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

        logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";

    }



    document.getElementById("loggedInDetails").innerHTML = logInHTML;

}



