var modal = document.getElementById('id01');

window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

async function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    const response = await fetch(`/login?username=${username}&password=${password}`);

    const data = await response.json();

    console.log(data.sessionId);
    sessionStorage.setItem("sessionId", data.sessionId);
    setSessionId();
    document.getElementById('id01').style.display='none'
}

function setSessionId() {
    let token = sessionStorage.getItem("sessionId");

    if (token !== null) {
        document.getElementById("session-data").innerText = "Session token is: "+token;
    }
}
