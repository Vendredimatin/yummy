window.onload = function () {
    $(".member-login").click(function () {
        let d = {};
        d.email = $(".member-email").val();
        d.password = $(".member-password").val();
        console.log(d);
        $.ajax({
            url:"/member/login",
            type:"post",
            contentType: "application/json;charset=utf-8",
            data:JSON.stringify(d),
            success: function (data) {
                console.log(data);
                alert(data["message"]);
                if (data['success'])
                    window.location.href = "canteenDisplay.html";
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });


    $(".manager-login").click(function () {
        let d = {};
        d.account = $(".manager-account").val();
        d.password = $(".manager-password").val();
        $.ajax({
            url:"/manager/login",
            type:"post",
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert(data["message"]);
                if (data['success'])
                    window.location.href = "/managerAudit";
            },
            fail:function (data) {
                alert("fail")
            }
        });
    });

    $(".canteen-login").click(function () {
        let d = {};
        d.account = $(".canteen-account").val();
        d.password = $(".canteen-password").val();
        $.ajax({
            url:"/canteen/login",
            type:"post",
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert(data["message"]);
                if (data['success'])
                    window.location.href = "canteenInfo.html";
            },
            fail:function (data) {
                alert("fail")
            }
        });
    });
};

function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}