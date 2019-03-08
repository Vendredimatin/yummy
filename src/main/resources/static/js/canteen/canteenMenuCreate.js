function dish(dishName, dishCategory, dishPrice, dishRemnants, dishDescription) {
    this.name = dishName;
    this.dishCategory = dishCategory;
    this.price = dishPrice;
    this.remnants = dishRemnants;
    this.description = dishDescription;
}

function combo(dishNames, dishRemnants, comboName, comboPrice, comboRemnants, comboDescription) {
    this.dishNames = dishNames;
    this.dishRemnants = dishRemnants;
    this.name = comboName;
    this.price = comboPrice;
    this.remnants = comboRemnants;
    this.description = comboDescription;
}

function Preference(targetSums, discountSums) {
    this.targetSums = targetSums;
    this.discountSums = discountSums;
}


window.onload = function () {
    var menu = {};
    var dishes = [];
    var combos = [];
    init();
    //添加新菜
    $("#addblock").click(function () {
        console.log("!!!");
        $(".menudialog").css("display", "block");
        $(".dish-name").val("");
        $(".dish-category").val("");
        $(".dish-price").val("");
        $(".dish-remnants").val("");
        $(".dish-description").val("");
    });

    $(".dish-cancel").click(function () {
        $(".menudialog").css("display", "none");
    });

    //创建新菜
    $(".dish-create").click(function () {
        var dishName = $(".dish-name").val();
        var dishCategory = $(".dish-category").val();
        var dishPrice = $(".dish-price").val();
        var dishRemnants = $(".dish-remnants").val();
        var dishDescription = $(".dish-description").val();

        var newDish = new dish(dishName, dishCategory, dishPrice, dishRemnants, dishDescription);
        dishes.push(newDish);

        addDish(newDish);
        $(".menudialog").css("display", "none");
    });

    //添加新套餐
    $("#addCombo").click(function () {
        $(".combodialog").css("display", "block");
        $(".combo-name").val("");
        $(".combo-price").val("");
        $(".combo-remnants").val("");
        $(".combo-description").val("");
    });

    $(".combo-cancel").click(function () {
        $(".combodialog").css("display", "none");
    });

    //在套餐中添加菜品
    $(".add-combo-dish").click(function () {
        var html = '<div class="add-dish">\n' +
            '                                <div class="menuformfield">\n' +
            '                                    <label>菜名及数量</label>\n' +
            '                                    <input class="add-dish-name" placeholder="请输入菜名">\n' +
            '                                    <input class="add-dish-remnants" placeholder="数量">\n' +
            '                                </div>\n' +
            '                            </div>';
        $(".add-dish-list").append(html);
    });


    //创建新套餐
    $(".combo-create").click(function () {
        var dishNames = [];
        var dishRemnants = [];

        $(".add-dish-name").each(function () {
            dishNames.push($(this).val());
        });

        $(".add-dish-remnants").each(function () {
            dishRemnants.push($(this).val());
        })

        var comboName = $(".combo-name").val();
        var comboPrice = $(".combo-price").val();
        var comboRemnants = $(".combo-remnants").val();
        var comboDescription = $(".combo-description").val();

        var newCombo = new combo(dishNames, dishRemnants, comboName, comboPrice, comboRemnants, comboDescription);

        combos.push(newCombo);

        addCombo(newCombo);
        $(".combodialog").css("display", "none");

        console.log(newCombo);

    });

    //添加新的优惠条件
    $(".add-preference").click(function () {
        var html = '<div class="preference-pair">\n' +
            '                    <span>满<input class="targetSum"></span>\n' +
            '                    <span>减<input class="discountSum"></span>\n' +
            '                </div>';

        $(".preference-list").append(html);
    });

    //提交添加新的菜单
    $(".menu-submit").click(function () {
        var targetSums = [];
        var discountSums = [];

        $(".targetSum").each(function () {
            targetSums.push($(this).val());
        });

        $(".discountSum").each(function () {
            discountSums.push($(this).val());
        })

        var preference = new Preference(targetSums, discountSums);

        menu.dishes = dishes;
        menu.combos = combos;
        menu.preference = preference;
        menu.time = $(".menu-time").val();

        console.log(menu);
        $.ajax({
            url: "/canteen/menu/create",
            type: "post",
            data: JSON.stringify(menu),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data['message']);
                window.location.href="/canteenMenuCreate";
            },
            fail: function (data) {
                alert("fail");
            }

        })
    });
};

function addDish(newDish) {
    var html = '<div class="desktop-menublock">\n' +
        '                    <div class="desktop-menublock-buttons">\n' +
        '                        <button class="desktop-menublock-button delete">删除</button>\n' +
        '                    </div>\n' +
        '                    <div class="desktop-menublock-name">' + newDish.name + '</div>\n' +
        '                    <div class="desktop-menublock-menu description">' + newDish.description + '\n' +
        '                    </div>\n' +
        '                    <div class="desktop-menublock-mobile ng-binding">数量:<span class="dish-remnants-display">' + newDish.remnants + '</span></div>\n' +
        '                    <div class="desktop-menublock-mobile ng-binding">￥<span class="dish-price-display">' + newDish.price + '</span></div>\n' +
        '\n' +
        '                </div>';

    $("#menulist").append(html);

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
}

function init() {
    $.ajax({
        url:"/canteen/info/get",
        type:"post",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            $(".topbar-canteen-name").text(data['canteenName']);
        },
        fail:function (data) {
            alert("fail")
        }
    })
}

function addCombo(newCombo) {
    var html = '<div class="desktop-menublock">\n' +
        '                <div class="desktop-menublock-buttons">\n' +
        '                    <button class="desktop-menublock-button delete">删除</button>\n' +
        '                </div>\n' +
        '                <div class="desktop-menublock-name combo">' + newCombo.name + '</div>\n' +
        '                <div class="desktop-menublock-menu combo description">' + newCombo.description + '\n' +
        '                </div>\n' +
        '                <div class="desktop-menublock-mobile combo">数量:<span class="combo-remnants-display">' + newCombo.remnants + '</span></div>\n' +
        '                <div class="desktop-menublock-mobile combo">￥<span class="combo-price-display">' + newCombo.price + '</span></div>\n' +
        '\n' +
        '            </div>';

    $("#combolist").append(html);
}

