window.onload = function () {

    init();

    //点击开始添加新地址
    $(document).on('click',"#addblock",function () {
        $(".addressdialog").css("display", "block");
    });

    //取消新添加的地址
    $(".add_cancel").click(function () {
        $(".addressdialog").css("display", "none");
    });

    //创建新地址
    $(".add_create").click(function () {
        var d = {};
        d.name = $(".add_name").val();
        d.province = $('#cmbProvince option:selected').val();
        d.city = $('#cmbCity option:selected').val();
        d.district = $('#cmbArea option:selected').val();
        d.detail = $(".add_detail").val();
        d.phone = $(".add_phone").val();

         $.ajax({
             url:"/member/address/add",
             type:"post",
             data:JSON.stringify(d),
             contentType: "application/json;charset=utf-8",
             success:function (data) {
                 alert(data['message']);
                 $(".addressdialog").css("display","none");
                 addAddress(data['addressID'],d.name,d.province,d.city,d.district,d.detail,d.phone);
             },
             fail:function (data) {
                 alert("fail");
             }
         })
    });


    //取消修改地址
    $(document).on('click', '.desktop-addresslist .desktop-addressblock .desktop-addressblock-buttons .desktop-addressblock-button.modify', function () {
        $(".addressdialog-modify").css("display", "none");
    });

    $(document).on('click', '.desktop-addresslist .desktop-addressblock .desktop-addressblock-buttons .desktop-addressblock-button.delete', function () {
       $(this).parents(".desktop-addressblock").remove();
        var d = {};
        d.id = $(this).parent().siblings(".desktop-addressblock-id").text();


        $.ajax({
            url: "/member/address/delete",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data['message']);
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });


        //点击修改地址
    $(document).on('click', '.desktop-addresslist .desktop-addressblock .desktop-addressblock-buttons .desktop-addressblock-button.modify', function () {
        var name = $(this).parent().siblings(".desktop-addressblock-name").text().split(" ")[0];
        var detail = $(this).parent().siblings(".desktop-addressblock-address").text();
        var phone = $(this).parent().siblings(".desktop-addressblock-mobile").text();
        var id = $(this).parent().siblings(".desktop-addressblock-id").text();
        let province = $(this).parent().siblings(".desktop-addressblock-address").attr("province");
        let city = $(this).parent().siblings(".desktop-addressblock-address").attr("city");
        let district = $(this).parent().siblings(".desktop-addressblock-address").attr("district");
        console.log(province,city,district);

        $(".addressdialog-modify").css("display", "block");

        $(".modify-name").val(name);


        $(".modify-id").val(id);
        $("#cmbProvince-modify option[value='" + province + "']").attr("selected", true);
        $("#cmbCity-modify option[value='" + city + "']").attr("selected", true);
        $("#cmbArea-modify option[value='" + district + "']").attr("selected", true);
        $(".modify-detail").val(detail);
        $(".modify-phone").val(phone);
    });

    $(".modify_cancel").click(function () {
        $(".addressdialog-modify").css("display", "none");
    });

    //提交修改的地址
    $(".modify_create").click(function () {
        var d = {};
        d.addressID = $(".modify-id").val();
        d.name = $(".modify-name").val();
        d.province = $('#cmbProvince-modify option:selected').val();
        d.city = $('#cmbCity-modify option:selected').val();
        d.district = $('#cmbArea-modify option:selected').val();
        d.detail = $(".modify-detail").val();
        d.phone = $(".modify-phone").val();

        $.ajax({
            url: "/member/address/modify",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data['message']);
                $(".addressdialog-modify").css("display", "none");
                init();
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });
}

function addAddress(id, name,province,city,district, detail, phone) {
    var html = '<div class="desktop-addressblock" address-id="\' + id + \'">\n' +
        '                    <div class="desktop-addressblock-buttons">\n' +
        '                        <button class="desktop-addressblock-button modify">修改</button>\n' +
        '                        <button class="desktop-addressblock-button delete">删除</button>\n' +
        '                    </div>\n' +
        '                    <div class="desktop-addressblock-id" style="display: none">' + id + '</div>\n' +
        '                    <div class="desktop-addressblock-name">' + name + '</div>\n' +
        '                    <div class="desktop-addressblock-address"\n' +
        '                         province="'+ province +'" city="'+city+'" district="'+district+'" >' + detail + '</div>\n' +
        '                    <div class="desktop-addressblock-mobile ng-binding">' + phone + '</div>\n' +
        '\n' +
        '                </div>';

    $(".desktop-addresslist").append(html);
}

function init() {
    $(".desktop-addressblock").each(function () {
        $(this).remove();
    });

    $.ajax({
        url: "/member/address/get",
        type: "post",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            console.log(data);
            var addresses = data["addresses"];

            for (var i = 0; i < addresses.length; i++){
                var address = addresses[i];
                addAddress(address.id,address.name,address.province,address.city,address.district,address.detail,address.phone);
            }

            var html = '<button class="desktop-addressblock desktop-addressblock-addblock" id="addblock">\n' +
                '                    <i class="icon-plus"></i>添加新地址\n' +
                '                </button>';

            $("#addresslist").append(html);
        },
        fail: function (data) {
            alert("fail");
        }
    })
}
