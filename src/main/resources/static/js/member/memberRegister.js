window.onload = function () {
    let identifyCode;

    $(".send-email").click(function () {
        let d = {};
        d.email = $(".member-email").val();
        $.ajax({
            url: "/member/register/identifyCode",
            type: "post",
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data["message"]);
                if (data['success'])
                    identifyCode = data['identifyCode'];
            },
            fail:function (data) {
                alert("fail");
            }
        });
    });

    $(".member-register-submit").click(function () {
        if (identifyCode != $(".member-identify-code").val()){
            alert("验证码错误！")
            return;
        }

        let d = {};
        d.name = $('.member-name').val();
        d.email = $('.member-email').val();
        d.password = $(".member-password").val();
        d.phone = $(".member-phone").val();

        if (d.name == '' || d.password == '' || d.email == '' || d.phone == ''){
            alert("注册的所有信息不允许为空！");
            return;
        }

        $.ajax({
            url: "/member/register",
            type: "post",
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data["message"]);
                if (data['success'])
                    window.location.href = "login.html";
            },
            fail:function (data) {
                alert("fail");
            }
        });
    });
}