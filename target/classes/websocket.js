var ws;

function connect() {
    var username = document.getElementById("username").value;

    var host = document.location.host;
    var pathname = "";//document.location.pathname;

    ws = new WebSocket("ws://" +"localhost:8085/"  + pathname + "chat/" + username);

    ws.onmessage = function(event) {
        var log = document.getElementById("log");
        console.log(event.data);
        var message = event.data;
        // var message = JSON.parse(event.data);
        log.innerHTML += event.data + "\n";//message.from + " : " + message.content + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content":content
    });

    ws.send(json);
}