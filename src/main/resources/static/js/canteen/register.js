
window.onload = function () {
    $(".canteen-register-submit").click(function () {
        var d = {};
        d.canteenName = $(".canteen-info-canteenName").val();
        d.password = $(".canteen-info-password").val();
        d.landlordName = $(".canteen-info-password").val();
        d.phone = $(".canteen-info-phone").val();
        d.categories = $(".canteen-info-category").val();
        d.province = $('#cmbProvince option:selected').val();
        d.city = $('#cmbCity option:selected').val();
        d.district = $('#cmbArea option:selected').val();
        d.detail = $(".canteen-info-detail").val();

        $.ajax({
            url:"/canteen/register",
            type:"post",
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert(data["message"]+"登录帐号为："+data["canteenID"]);
                window.location.href = "canteenInfo.html";
            },
            fail:function (data) {
                alert("fail")
            }
        })
    });
}