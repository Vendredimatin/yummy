window.onload = function () {
    init();
}

function init() {
    $.ajax({
        url:"/canteen/info/get",
        type:"post",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            console.log(data);
            console.log(data['id']);
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
        },
        fail:function (data) {
            alert("fail")
        }
    })
}