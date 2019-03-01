window.onload = function () {
    init();

    $(document).on('click','.shop-button',function () {
       var canteenID = $(this).siblings(".rstblock-id").attr("attr-canteen-id");
       console.log(canteenID);

       var d = {};
       d.canteenID = canteenID;
       $.ajax({
           url: "/member/scanCanteen",
           type: "post",
           data:JSON.stringify(d),
           contentType: "application/json;charset=utf-8",
           success: function (data) {
               alert("success");
               window.location.href = "canteenMenuDisplay.html";
           },
           fail:function (data) {
               alert("fail");
           }
       });
    });
};

function init() {
    $.ajax({
        url:"/canteen/display",
        type:"post",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            alert("success");
            console.log(data);
            addCanteen(data);
        },
        fail:function (data) {
            alert("fail");
        }
    })
}

function addCanteen(data) {
    for (var i = 0; i < data.length; i++) {
        var canteen = data[i];
        var canteenCategory = canteen['categories'].toString();
        var address = canteen['address'];
        console.log(address);
        var html = ' <a class="rstblock">\n' +
            '                <div class="rstblock-content">\n' +
            '                    <input type="hidden" class="rstblock-id" attr-canteen-id="'+ canteen['id'] +'">\n' +
            '                    <div class="rstblock-title">'+ canteen['canteenName'] +'</div>\n' +
            '                    <div class="rstblock-phone">'+ canteen['phone'] +'</div>\n' +
            '                    <div class="rstblock-canteenCategory">'+ canteenCategory +'</div>\n' +
            '                    <span class="rstblock-province">'+ address['province'] +'</span>\n' +
            '                    <span class="rstblock-city">'+ address['city'] +'</span>\n' +
            '                    <span class="rstblock-district">' + address['district'] +'</span>\n' +
            '                    <button class="shop-button">进入商家</button>\n' +
            '                </div>\n' +
            '            </a>';

        $(".canteen-list").append(html);
    }
}