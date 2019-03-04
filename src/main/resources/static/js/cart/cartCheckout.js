
window.onload = function () {
    let metersPerHour = 30000;
    let maxTime = 60;
    let canteenAddress;
    let addresses;
    let deliveringTime;
    init();

    $(document).on('click', '.minus', function () {
        let quantity = $(this).siblings(".quantity").val();
        quantity = (quantity == 0) ? 0 : (quantity - 1);
        $(this).siblings(".quantity").val(quantity);
        let singlePrice = parseFloat($(this).parent().siblings(".cell.itemtotal").children(".subTotal-price").attr('attr-single-price'));
        let subTotal = singlePrice * quantity;

        $(this).parent().siblings(".cell.itemtotal").children(".subTotal-price").text(subTotal);
        caculateTotal();
    });

    $(document).on('click', '.plus', function () {
        let quantity = parseInt($(this).siblings(".quantity").val());
        quantity = quantity + 1;
        $(this).siblings(".quantity").val(quantity);
        let singlePrice = parseFloat($(this).parent().siblings(".cell.itemtotal").children(".subTotal-price").attr('attr-single-price'));
        let subTotal = singlePrice * quantity;

        $(this).parent().siblings(".cell.itemtotal").children(".subTotal-price").text(subTotal);
        caculateTotal();
    });


    //选择地址时判断是否可行
    $(".address-list").change(function () {
        console.log(canteenAddress);
        let addressID = $('.address-list option:selected').attr("attr-address-id");
        let memberAddress;
        for (let i in addresses) {
            let address = addresses[i];
            if (address['id'] == addressID) {
                memberAddress = address;
                break;
            }
        }

        let canAddrStr = canteenAddress['province'] + canteenAddress['city'] + canteenAddress['district'];
        let canAddrCity = canteenAddress['city'];
        console.log('canteen', canAddrStr, canAddrCity);
        let memAddrStr = memberAddress['province'] + memberAddress['city'] + memberAddress['district'];
        let memAddrCity = memberAddress['city'];
        console.log('member', memAddrStr, memAddrCity);
        // 百度地图API功能
        var map = new BMap.Map("allmap");//创建一个map实例，allmap表示地图容器
        var point = new BMap.Point(116.331398, 39.897445);//创建点坐标
        map.centerAndZoom(point, 12);//初始化地图，设置地图的中心点坐标和缩放级别

        var myGeo = new BMap.Geocoder();
        // 将地址解析结果显示在地图上,并调整地图视野

        myGeo.getPoint(canAddrStr, function (point) {
            if (point) {
                map.centerAndZoom(point, 16);
                console.log(point);

                myGeo.getPoint(memAddrStr, function (point2) {
                    if (point2) {
                        let distance = parseInt(map.getDistance(point, point2));
                        let time;
                        if (distance == 0)
                            time = 30;
                        else time = parseInt(distance / metersPerHour * 60);

                        if (time > maxTime)
                            alert("距离太远，无法送到，请更换地址");
                        else{
                            deliveringTime = time;
                            alert("距离为" + distance + "米,需要时间" + time + "分钟");
                        }
                    }
                }, memAddrCity);
            } else {
                alert("您选择地址没有解析到结果!");
            }
        }, canAddrCity);//注：该方法存在3个参数，最后一个参数“城市名”别忘了！
    });

    //下订单
    $(".confirm").click(function () {
        let addressID = $('.address-list option:selected').attr("attr-address-id");
        let totalPrice = $('.num').text();
        let dishIDs = [];
        $(".dish-item-id").each(function () {
            dishIDs.push($(this).text());
        });
        let comboIDs = [];
        $(".combo-item-id").each(function () {
            comboIDs.push($(this).text());
        })
        let dishQuantities = [];
        $(".quantity.dish").each(function () {
            dishQuantities.push($(this).val());
        });
        let comboQuantities = [];
        $(".quantity.combo").each(function () {
            comboQuantities.push($(this).val());
        })
        let dishSubtotals = [];
        $(".subTotal-price.dish").each(function () {
            dishSubtotals.push($(this).text());
        });
        let comboSubtotals = [];
        $(".subTotal-price.combo").each(function () {
            comboSubtotals.push($(this).text());
        });

        let d = {};
        d.addressID = addressID;
        d.totalPrice = totalPrice;
        d.deliveringTime = deliveringTime;
        d.dishIDs = dishIDs;
        d.comboIDs = comboIDs;
        d.dishQuantities = dishQuantities;
        d.comboQuantities = comboQuantities;
        d.dishSubtotals = dishSubtotals;
        d.comboSubtotals = comboSubtotals;


        console.log(d);
        $.ajax({
            url: "/member/order/checkout",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data['message']);
                $(".pay-dialog").css('display', 'block');
            },
            fail: function (data) {
                alert("fail");
            }
        });


    });

    //付款
    $(".pay-btn").click(function () {
        let d = {};
        d.password = $(".pay-password").val();
        d.totalPrice = $(".num").text();

        $.ajax({
            url: "/member/order/pay",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data['message']);
                $(".pay-dialog").css('display', 'none');
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });


    function caculateTotal() {
        let totals = 0.0;
        $(".subTotal-price").each(function () {
            let subTotal = parseFloat($(this).text());
            totals += subTotal;
        });

        $(".num").text(totals);
    }

    function init() {
        $.ajax({
            url: "/member/cart/display",
            type: "post",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                console.log(data['cart']);
                console.log(data['addresses']);

                canteenAddress = data['canteenAddress'];
                addresses = data['addresses'];

                initCart(data['cart']);
                initAddress(data['addresses']);
            },
            fail: function (data) {
                alert("fail");
            }
        })
    }

    function initCart(cart) {
        var comboMap = cart['combos'];
        var dishMap = cart['dishes'];

        for (let i = 0; i < comboMap.length; i++) {
            let combo = comboMap[i];
            console.log(combo);
            var html = '<dd>\n' +
                '                    <div class="checkoutcart-tablerow">\n' +
                '　　　　　　　　　　　　　　<div class="combo-item-id" style="display: none;">' + combo['id'] + '</div>' +
                '                        <div class="cell itemname">' + combo['name'] + '</div>\n' +
                '                        <div class="cell itemquantity">\n' +
                '                            <button class="minus">-</button>\n' +
                '                            <input value="1" class="quantity combo">\n' +
                '                            <button class="plus">+</button>\n' +
                '                        </div>\n' +
                '                        <div class="cell itemtotal">\n' +
                '                            <span>￥</span>\n' +
                '                            <span class="subTotal-price combo" attr-single-price="' + combo['price'] + '">' + combo['price'] + '</span>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '\n' +
                '                </dd>';
            $(".checkoutcart-group").append(html);
        }

        $(".checkoutcart-group").append('<dt class="checkoutcart-grouptitle">单品</dt>');
        for (let i = 0; i < dishMap.length; i++) {
            let dish = dishMap[i];
            console.log(dish);
            var html = '<dd>\n' +
                '                    <div class="checkoutcart-tablerow">\n' +
                '　　　　　　　　　　　　　　<div class="dish-item-id" style="display: none;">' + dish['id'] + '</div>' +
                '                        <div class="cell itemname">' + dish['name'] + '</div>\n' +
                '                        <div class="cell itemquantity">\n' +
                '                            <button class="minus">-</button>\n' +
                '                            <input value="1" class="quantity dish">\n' +
                '                            <button class="plus">+</button>\n' +
                '                        </div>\n' +
                '                        <div class="cell itemtotal">\n' +
                '                            <span>￥</span>\n' +
                '                            <span class="subTotal-price dish" attr-single-price="' + dish['price'] + '">' + dish['price'] + '</span>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '\n' +
                '                </dd>';
            $(".checkoutcart-group").append(html);
        }
    }

    function initAddress(addresses) {
        for (let i = 0; i < addresses.length; i++) {
            let address = addresses[i];
            let html = '<option attr-address-id="' + address['id'] + '" address-province="' + address['province'] + '" address-city="' + address['province'] + '" address-district="' + address['district'] + '">\n' +
                '                    <span>' + address['name'] + '</span>\n' +
                '                    <span>' + address['phone'] + '</span>\n' +
                '                    <p>' + address['province'] + ',' + address['city'] + ',' + address['district'] + '</p>\n' +
                '                </option>';

            $(".address-list").append(html);
        }
    }
}