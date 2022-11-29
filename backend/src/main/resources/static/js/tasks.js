async function getTasks() {
    let token = sessionStorage.getItem("sessionId");

    const response = await fetch('/resource/tasks', {
        method: "GET",
        headers: {
            'sessionId': token,
        }
    })

    var data = JSON.parse(await response.text());
    var table = '<tbody>'

    for(i = 0;i < data.length; i++) {
        table += '<tr>';
        table += '<td>' + data[i].title + '</td>';
        table += '<td>' + data[i].content + '</td>';
        table += '</tr>';
    }

    table += '</tbody>';
    document.getElementById('tableData').innerHTML = table;
}

async function addTask() {
    let token = sessionStorage.getItem("sessionId");
    let title = document.getElementById("title").value;
    let content = document.getElementById("content").value;

    await fetch(`/resource/addTask?title=${title}&content=${content}`, {
        method: "GET",
        headers: {
            'sessionId': token,
        }
    });

    document.getElementById('id01').style.display='none'
    await getTasks();
}