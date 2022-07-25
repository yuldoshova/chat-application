var stompClient = null;

var currentUser = {
    'id': 2,
    'name': "Ozoda"
};


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#main-menu").show();
    } else {
        $("#main-menu").hide();
    }
    $("#greetings").html("");
}


async function connect() {
    await getCurrentUser();
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);

        console.log('Connected: ' + frame);  // consolga qavs ichidagini chiqarib qo'yadi.

        getAllUsers();

        stompClient.subscribe('/chat/' + currentUser.id + "/queue/messages"
            , function (funksiyagaKelganXabar) {
                let dataObj = JSON.parse(funksiyagaKelganXabar.body);

                appendNewMessage(dataObj);

            });
    });
}


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);

    console.log("Disconnected");
    $("#receiverId").empty()
    $("#welcome").empty()
}


function getAllUsers() {
    fetch("/api/user").then(function (res) {

        if (res.ok) {
            res.json().then(data => {
                $("#receiverId").append("<option value='" + currentUser.id + "'>Saved Messages")
                data.map(user => {
                    if (user.id !== currentUser.id) {
                        $("#receiverId").append(
                            " <option value='" + user.id + "'>" + user.name + "</option>"
                        )
                    }
                })
                getMessagesByUserId(currentUser.id)
            })
        }
    })
}


function receiverIdChangeHandle(selectObj) {
    $("#greetings").empty();
    let receiverId = selectObj.value;
    console.log(receiverId);
    getMessagesByUserId(receiverId);
}


function getMessagesByUserId(receiverId) {
    fetch("/api/massage/byChatId/" + receiverId+"/"+currentUser.id).then(function (res) {
        if (res.ok) {
            res.json().then(data => {
                data.map(message => {
                    appendNewMessage(message)
                })
            })
        }
    })
}


function appendNewMessage(message) {

    const trAlign = message.senderId === currentUser.id ?
        'right' : 'left'

    // --------------------------------
    console.log(message.senderName);
//     console.log(message.receiverName);
//     console.log(message.text);
//     console.log(message.time)
   // ---------------------------------


    $("#greetings").append("<tr align='"+trAlign+"'><td>" +
        "<h6>" + message.senderName + "</h6>" +
        "<h1>" + message.text + "</h1>" +
        // "<h6>" + message.time + "</h6>" +
        "</td></tr>");
}


function sendMessage() {

    const receiverName = $("#receiverId").find(":selected").text();

    const bodyStr = JSON.stringify({
        'text': $("#name").val(),
        'receiverId': $("#receiverId").val(),
        'senderId': currentUser.id,
        'receiverName': receiverName,
        'senderName': currentUser.name
    });

    stompClient.send("/app/send-message",
        {},
        bodyStr)




    // stringify bu- json formatdagi ma'lumotlarni "string-json" formatiga o'tkazadi!
}


function getCurrentUser() {
    fetch("/api/user/currentUser").then(function (response) {
        response.json()
            .then(
                data => {
                    currentUser = data;
                    $("#welcome").append("Welcome " + currentUser.name);
                }
            )
    })

}


function getAllMessages() {
    fetch("/api/massage").then(function (res) {
        if (res.ok) {
            res.json().then(data => {
                data.map(message => {
                    appendNewMessage(message)
                })
            })
        }
    })
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
});