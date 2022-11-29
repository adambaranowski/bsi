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