window.onload = function () {
    init();

    //搜索
    $(".place-search-btn").click(function () {
        console.log("@@@");
        let d = {};
        d.startTime = $(".start-time").val();
        d.endTime = $('.end-time').val();
        d.maxPrice = $(".max-price").val();
        d.maxPrice = (d.maxPrice == '')?-1:d.maxPrice;
        d.minPrice = $(".min-price").val();
        d.minPrice = (d.minPrice == '')?-1:d.minPrice;
        d.memberName = $('.member-area input').val();
        d.memberNameName = (d.memberName == '')?"null":d.memberName;
        d.orderState =  $('.state-area select option:selected').text();

        $.ajax({
            url:"/canteen/order/search",
            type:'post',
            data:JSON.stringify(d),
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
        d.maxPrice = (d.maxPrice == '')?-1:d.maxPrice;
        d.minPrice = $(".min-price").val();
        d.minPrice = (d.minPrice == '')?-1:d.minPrice;
        d.memberName = $('.member-area input').val();
        d.memberName = (d.memberName == '')?"null":d.memberName;
        d.orderState =  $('.state-area select option:selected').text();
        d.nextPage = currentPage + 1;
        $.ajax({
            url:"/canteen/order/page",
            type:'post',
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (orders) {
                console.log(orders);
                if (orders.length == 0){
                    alert("这是最后一页了");
                }else initHtml(orders);
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });

    $(document).on('click','.ordertimeline-handle-detail',function () {
        let d = {};
        d.orderID = $(this).attr('order-id');
        $.ajax({
            url:"/order/detail/check",
            type:'post',
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                window.location.href = "/canteenOrderDetail.html";
            },
            fail: function (data) {
                alert("fail");
            }
        })
    });

    function init() {
        $.ajax({
            url:"/canteen/order/history",
            type:'post',
            contentType: "application/json;charset=utf-8",
            success: function (orders) {
                console.log(orders);
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
            let name = (orderItems.length > 1)?(orderItems[0].name+"等"):orderItems[0].name;
            let html = '<tr class="timeline">\n' +
                '                    <td class="ordertimeline-time"><p class="ng-binding">'+ order['time'] +'</p>\n' +
                '                        <i class="ordertimeline-time-icon icon-uniE65E finish ng-scope"></i>\n' +
                '                    </td>\n' +
                '\n' +
                '                    <td class="ordertimeline-memberName">\n' +
                '                        <h3 class="ordertimeline-title ordertimeline-member-name ui-arial">'+ order['memberName'] +'</h3>\n' +
                '                    </td>'+
                '                    <td class="ordertimeline-info">\n' +
                '                        <p class="ordertimeline-info-food">\n' +
                '                            <a>\n' +
                '                                <span class="ordertimeline-food">'+ name +'</span>\n' +
                '                                <span class="ordertimeline-info-productnum">'+ orderItems.length +'</span>\n' +
                '                                <span>个菜品</span>\n' +
                '                            </a>\n' +
                '                        </p>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-amount">\n' +
                '                        <h3 class="ordertimeline-title ordertimeline-price ui-arial">'+ order['totalPrice'] +'</h3>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-status">\n' +
                '                        <h3 class="end">'+ order['orderState'] +'</h3>\n' +
                '                    </td>\n' +
                '                    <td class="ordertimeline-handle">\n' +
                '                        <a class="ordertimeline-handle-detail" order-id="'+ order['id'] +'">订单详情</a>\n' +
                '                    </td>\n' +
                '                </tr>';

            $(".order-list").append(html);
        }
    }
};