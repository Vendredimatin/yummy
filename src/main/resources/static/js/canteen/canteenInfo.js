window.onload = function () {
    let oldInfo;
    init();

    $(".canteen-info-modify").click(function () {
        $(".profileinfo-item.display").css("display","none");
        $(".profileinfo-item.select").css("display","block");

        $(".profileinfo-value.name").attr("disabled",false);
        $(".profileinfo-value.category").attr("disabled",false);
        $(".profileinfo-value.landlordName").attr("disabled",false);
        $(".profileinfo-value.phone").attr("disabled",false);
    });

    $(".canteen-info-submit").click(function () {
        var d = {};
        d.name = $(".profileinfo-value.name").val();
        d.landlordName = $(".profileinfo-value.landlordName").val();
        d.phone = $(".profileinfo-value.phone").val();
        d.category = $(".profileinfo-value.category").val();
        d.province = $('#cmbProvince option:selected').val();
        d.city = $('#cmbCity option:selected').val();
        d.district = $('#cmbArea option:selected').val();

        $.ajax({
            url: "/canteen/info/modify",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert("已提交，待yummy经理审批");

                $(".profileinfo-item.display").css("display","block");
                $(".profileinfo-item.select").css("display","none");

                $(".profileinfo-value.name").attr("disabled",true);
                $(".profileinfo-value.category").attr("disabled",true);
                $(".profileinfo-value.landlordName").attr("disabled",true);
                $(".profileinfo-value.phone").attr("disabled",true);

                initCanteenInfo(oldInfo);
            },
            fail:function (data) {
                alert("fail");
            }
        });
    });

    function init() {
        $.ajax({
            url:"/canteen/info/get",
            type:"post",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                console.log(data['id']);

                oldInfo = data;
                initCanteenInfo(oldInfo);
            },
            fail:function (data) {
                alert("fail")
            }
        })
    }
};

function initCanteenInfo(data) {
    var name = data['canteenName'];
    var landlordName = data['landlordName'];
    var phone = data['phone'];
    var category = data['categories'];
    var address = data['address'];
    var province = address['province'];
    var city = address['city'];
    var district = address['district'];

    $(".profileinfo-value.name").val(name);
    $(".profileinfo-value.landlordName").val(landlordName);
    $(".profileinfo-value.phone").val(phone);
    $(".profileinfo-value.category").val(category);
    $(".profileinfo-value.province").val(province);
    $(".profileinfo-value.city").val(city);
    $(".profileinfo-value.district").val(district);
}
