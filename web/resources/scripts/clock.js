function setTime() {
    let date = new Date();
    let sec = date.getSeconds();
    let minutes = date.getMinutes();
    let hours = date.getHours();
    document.getElementById("clock").innerHTML = "<label style='color: black;font-size: 25pt;'>Время:</label><br/> " + formatDate(hours) + ":" + formatDate(minutes) + ":" + formatDate(sec);
    let time = setTimeout(setTime, 7000);
}

function formatDate(number) {
    return number < 10 ? "0"+number : number;
}

window.addEventListener("load", setTime);
