
window.onload = function (ev) {
    init();

    $(".modify").click(function () {
        $(".profileinfo-value.name").attr("disabled",false);
        $(".profileinfo-value.email").attr("disabled",false);
        $(".profileinfo-value.phone").attr("disabled",false);
    });

    $(".submit").click(function () {
        var d = {};
        d.name = $(".profileinfo-value.name").val();
        d.email = $(".profileinfo-value.email").val();
        d.phone = $(".profileinfo-value.phone").val();

        $.ajax({
            url: "/member/modifyInfo",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data["message"]);
                $(".profileinfo-value.name").attr("disabled",true);
                $(".profileinfo-value.email").attr("disabled",true);
                $(".profileinfo-value.phone").attr("disabled",true);
            },
            fail:function (data) {
                alert("fail");
            }
        });
    });


    $(".topbar-member-area").mouseover(function () {
        console.log(111);
        $(".user-menu").css("display", "block");
    });
    $(".topbar-member-area").mouseout(function () {
        $(".user-menu").css("display", "none");
    });

    $(".user-menu").mouseover(function () {
        $(".user-menu").css("display", "block");
    })

    $(".user-menu").mouseout(function () {
        $(".user-menu").css("display", "none");
    });
    
    $('.log-off').click(function () {
        let res = confirm("确定注销吗？");
        if (res ==true ){
            $.ajax({
                url: "/member/logoff",
                type: "post",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    alert(data["message"]);
                    window.location.href = "login.html";
                },
                fail:function (data) {
                    alert("fail");
                }
            });
        }
    })
    
};

function init() {
    $.ajax({
        url:"/member/info",
        type:"post",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            console.log(data);
            console.log(data['id']);
            var name = data['name'];
            var email = data['email'];
            var phone = data['phone'];
            var memberLevel = data['memberLevel'];
            $(".profileinfo-value.name").val(name);
            $(".profileinfo-value.email").val(email);
            $(".profileinfo-value.phone").val(phone);
            $(".profileinfo-value.memberLevel").val(memberLevel);
        },
        fail:function (data) {
            alert("fail");
        }
    })
}
