window.onload = function () {
    init();
    
    function init() {
        $.ajax({
            url:"/canteen/balance/get",
            type:"post",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                $(".balance-number").text(data['balance']['balance']);
                $(".balance-profit").text(data['totalProfit']);
                $(".orders-number").text(data['totalNums']);
                $(".topbar-canteen-name").text(data['canteenName']);
            },
            fail:function (data) {
                alert("fail");
            }
        })
    }

    $(".topbar-canteen-area").mouseover(function () {
        console.log(111);
        $(".user-menu").css("display", "block");
    });
    $(".topbar-canteen-area").mouseout(function () {
        $(".user-menu").css("display", "none");
    });

    $(".user-menu").mouseover(function () {
        $(".user-menu").css("display", "block");
    })

    $(".user-menu").mouseout(function () {
        $(".user-menu").css("display", "none");
    });

    $(".log-out").click(function () {
        window.location.href = "login.html";
    })
};