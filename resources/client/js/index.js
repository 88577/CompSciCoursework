function pageLoad() {

    let now = new Date();

    let myHTML = '<table>'
        + '<tr>'
        + '<th>Court 1</th>'
        + '<th>Court 2</th>'
        + '<th>Court 3</th>';

    fetch('/ScheduleController/ListAllBookings', {method:'get'}
    ).then(response => response.json()
    ).then(bookings => {
        for(let i = 1; i < 19; i++) {

        }
    })

    document.getElementById("testDiv").innerHTML = myHTML;

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

        checkLogin();

        logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";

    }



    document.getElementById("loggedInDetails").innerHTML = logInHTML;

}



