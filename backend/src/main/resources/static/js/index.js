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
    checkIfSessionActive();
    document.getElementById('id01').style.display='none'
}

async function logout() {
    let token = sessionStorage.getItem("sessionId");
    const response = await fetch('/logout', {
        method: "GET",
        headers: {
            'sessionId': token,
        }
    });

    if (response.status !== 200) {
        console.log("error logging out, token: ", token);
        return;
    }

    sessionStorage.removeItem("sessionId");
    checkIfSessionActive();
}

async function getHello() {
    let token = sessionStorage.getItem("sessionId");

    const response = await fetch('/resource/hello', {
        method: "GET",
        headers: {
            'sessionId': token,
        }
    });

    document.getElementById("hello-container").innerText = await response.text();
}

function checkIfSessionActive() {
    let token = sessionStorage.getItem("sessionId");

    if (token !== null) {
        document.getElementById("session-data").innerText = "Session token is: "+token;
        document.getElementById("login-btn").style.display='none';
        document.getElementById("logout-btn").style.display='block';
        document.getElementById("tasks-btn").style.display='block';
        document.getElementById("malicious-btn").style.display='block';
    }
    else {
        document.getElementById("session-data").innerText = "Session token is not set.";
        document.getElementById("login-btn").style.display='block';
        document.getElementById("logout-btn").style.display='none';
        document.getElementById("tasks-btn").style.display='none';
        document.getElementById("malicious-btn").style.display='none';
    }
}
