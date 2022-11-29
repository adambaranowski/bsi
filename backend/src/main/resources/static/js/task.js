async function loadTaskIdFromPathname() {
    const pageUrl = new URL(document.URL);
    let pathSplit = pageUrl.pathname.split("/");
    const id = pathSplit[pathSplit.length-1];
    console.log(id);
    document.getElementById("task-id").innerHTML = id;
    let task;
    try {
        task = await fetchTaskData(parseInt(id));
    }
    catch (e) {
        console.log(e);
        return;
    }

    document.getElementById("task-id").innerHTML = JSON.stringify(await task);
}

async function fetchTaskData(id) {
    let token = sessionStorage.getItem("sessionId");

    const response = await fetch(`/resource/tasks/${id}`, {
        method: "GET",
        headers: {
            'sessionId': token,
        }
    });

    return await response.json();
}
