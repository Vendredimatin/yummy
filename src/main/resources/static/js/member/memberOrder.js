window.onload = function () {
    init();

    //退订
    $(document).on("click", ".ordertimeline-unsubscribe button:first-child", function () {
        let orderState = $(this).parent().siblings(".ordertimeline-status").children().text();
        console.log(orderState);
        if (orderState == "完成") {
            alert("已完成的订单无法退订");
        } else if (orderState == "派送中" || orderState == "未支付") {
            //退一定的金额
            let d = {};
            d.orderID = $(this).parent().attr("order-id");
            d.orderState = orderState;
            $.ajax({
                url: "/member/order/unsubscribe",
                type: "post",
                data: JSON.stringify(d),
                contentType: "application/json;charset=utf-8",
                success: function (orders) {
                    alert(orders['message']);
                    console.log(orders);
                    window.location.href = "memberOrder.html";
                },
                fail: function (data) {
                    alert("fail");
                }
            })
        }
    });


    //提前收货
    $(document).on('click', ".order-confirm", function () {
        let d = {};
        d.orderID = $(this).parent().attr("order-id");
        $.ajax({
            url: "/member/order/confirm",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert(data['message']);
                console.log(data);
                init();
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });

    //支付
    $(document).on('click', ".order-pay", function () {
        $(".pay-dialog").css('display', 'block');
    });

    //付款
    $(".pay-btn").click(function () {
        let d = {};
        d.password = $(".pay-password").val();
        d.totalPrice = $(this).attr("total-price");
        console.log(d);

        $.ajax({
            url: "/member/order/pay",
            type: "post",
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert("支付成功！实际支付" + data['totalPrice'] + "元");
                $(".pay-dialog").css('display', 'none');
                window.location.href = "memberOrder.html";
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });

    //搜索
    $(".place-search-btn").click(function () {
        console.log("@@@");
        let d = {};
        d.startTime = $(".start-time").val();
        d.endTime = $('.end-time').val();
        d.maxPrice = $(".max-price").val();
        d.maxPrice = (d.maxPrice == '') ? -1 : d.maxPrice;
        d.minPrice = $(".min-price").val();
        d.minPrice = (d.minPrice == '') ? -1 : d.minPrice;
        d.canteenName = $('.canteen-area input').val();
        d.canteenName = (d.canteenName == '') ? "null" : d.canteenName;
        d.orderState = $('.state-area select option:selected').text();

        $.ajax({
            url: "/member/order/search",
            type: 'post',
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (orders) {
                console.log(orders);
                initHtml(orders);
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });

    //下一页
    $(".pages span").click(function () {
        let currentPage = parseInt($(this).attr("current-page"));

        let d = {};
        d.startTime = $(".start-time").val();
        d.endTime = $('.end-time').val();
        d.maxPrice = $(".max-price").val();
        d.maxPrice = (d.maxPrice == '') ? -1 : d.maxPrice;
        d.minPrice = $(".min-price").val();
        d.minPrice = (d.minPrice == '') ? -1 : d.minPrice;
        d.canteenName = $('.canteen-area input').val();
        d.canteenName = (d.canteenName == '') ? "null" : d.canteenName;
        d.orderState = $('.state-area select option:selected').text();
        d.nextPage = currentPage + 1;
        $.ajax({
            url: "/member/order/page",
            type: 'post',
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (orders) {
                console.log(orders);
                if (orders.length == 0) {
                    alert("这是最后一页了");
                } else initHtml(orders);
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });


    $(document).on('click', '.ordertimeline-handle-detail', function () {
        let d = {};
        d.orderID = $(this).attr('order-id');
        $.ajax({
            url: "/order/detail/check",
            type: 'post',
            data: JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                window.location.href = "/memberOrderDetail.html";
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });

    function init() {
        $.ajax({
            url: "/member/order/history",
            type: 'post',
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                $(".topbar-member-name").text(data['memberName']);
                let orders = data['orders'];
                initHtml(orders);
            },
            fail: function (data) {
                alert("fail");
            }
        })
    }

    function initHtml(orders) {
        $(".timeline").remove();

        for (let i = 0; i < orders.length; i++) {
            let order = orders[i];
            let orderItems = order['orderItems'];
            let name = (orderItems.length > 1) ? (orderItems[0].name + "等") : orderItems[0].name;
            let html = '<tr class="timeline">\n' +
                '                    <td class="ordertimeline-time"><p class="ng-binding">' + order['time'] + '</p>\n' +
                '                        <i class="ordertimeline-time-icon icon-uniE65E finish ng-scope"></i>\n' +
                '                    </td>\n' +
                '\n' +
                '                    <td class="ordertimeline-canteenName">\n' +
                '                        <h3 class="ordertimeline-title ordertimeline-canteen-name ui-arial">' + order['canteenName'] + '</h3>\n' +
                '                    </td>' +
                '                    <td class="ordertimeline-info">\n' +
                '                        <p class="ordertimeline-info-food">\n' +
                '                            <a>\n' +
                '                                <span class="ordertimeline-food">' + name + '</span>\n' +
                '                                <span class="ordertimeline-info-productnum">' + orderItems.length + '</span>\n' +
                '                                <span>个菜品</span>\n' +
                '                            </a>\n' +
                '                        </p>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-amount">\n' +
                '                        <h3 class="ordertimeline-title ordertimeline-price ui-arial">' + order['totalPrice'] + '</h3>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-status">\n' +
                '                        <h3 class="end">' + order['orderState'] + '</h3>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-handle">\n' +
                '                        <a class="ordertimeline-handle-detail" order-id="' + order['id'] + '">订单详情</a>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-unsubscribe" order-id="' + order['id'] + '">\n';
            if (order['orderState'] == "完成")
                html += '                        <button order-state="complete" disabled>无操作</button>\n';
            else if (order['orderState'] == "未支付") {
                html += '                        <button order-state="unpaid">退订</button>\n';
                html += '                        <button class="order-pay" total-price="'+ order['totalPrice'] +'">支付</button>\n';
            } else if (order['orderState'] == "派送中") {
                html += '                        <button order-state="delivering">退订</button>\n';
                html += '                        <button class="order-confirm">确认收货</button>\n';
            } else
                html += '                        <button order-state="complete" disabled>无操作</button>\n';

            html += '                    </td>' +
                '                </tr>';

            $(".order-list").append(html);
        }
    }

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
        if (res == true) {
            $.ajax({
                url: "/member/logoff",
                type: "post",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    alert(data["message"]);
                    window.location.href = "login.html";
                },
                fail: function (data) {
                    alert("fail");
                }
            });
        }
    })

    $(".log-out").click(function () {
        window.location.href = "login.html";
    })

};