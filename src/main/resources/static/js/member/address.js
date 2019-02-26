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
        d.sex = $("input[name='sex']:checked").val();
        d.province = $(".add_province").val();
        d.city = $(".add_city").val();
        d.district = $(".add_district").val();
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
                 addAddress(data['addressID'],d.name,d.sex,d.detail,d.phone);
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
        console.log("a");
        var name = $(this).parent().siblings(".desktop-addressblock-name").text().split(" ")[0];
        var sex = $(this).parent().siblings(".desktop-addressblock-name").text().split(" ")[1];
        var detail = $(this).parent().siblings(".desktop-addressblock-address").text();
        var phone = $(this).parent().siblings(".desktop-addressblock-mobile").text();
        var id = $(this).parent().siblings(".desktop-addressblock-id").text();

        console.log(name, sex, detail, phone);
        $(".addressdialog-modify").css("display", "block");

        $(".modify-name").val(name);

        if (sex == "先生")
            $("input[name='modify-sex'][value='先生']").attr("checked", true);
        else
            $("input[name='modify-sex'][value='女士']").attr("checked", true);

        $(".modify-id").val(id);
        $(".modify-province").val("江苏省");
        $(".modify-city").val("南京市");
        $(".modify-district").val("鼓楼区");
        $(".modify-detail").val(detail);
        $(".modify-phone").val(phone);
    });

    //提交修改的地址
    $(".modify_create").click(function () {
        var d = {};
        d.addressID = $(".modify-id").val();
        d.name = $(".modify-name").val();
        d.sex = $("input[name='modify-sex']:checked").val();
        d.province = $(".modify-province").val();
        d.city = $(".modify-city").val();
        d.district = $(".modify-district").val();
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

function addAddress(id, name, sex, detail, phone) {
    var html = '<div class="desktop-addressblock" address-id="\' + id + \'">\n' +
        '                    <div class="desktop-addressblock-buttons">\n' +
        '                        <button class="desktop-addressblock-button modify">修改</button>\n' +
        '                        <button class="desktop-addressblock-button delete">删除</button>\n' +
        '                    </div>\n' +
        '                    <div class="desktop-addressblock-id" style="display: none">' + id + '</div>\n' +
        '                    <div class="desktop-addressblock-name">' + name + ' <span class="ng-binding">' + sex + '</span></div>\n' +
        '                    <div class="desktop-addressblock-address"\n' +
        '                         ng-bind="address.address + \' \' + address.address_detail">' + detail + '</div>\n' +
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
                addAddress(address.id,address.name,address.sex,address.detail,address.phone);
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
