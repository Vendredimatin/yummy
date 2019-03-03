window.onload = function () {
    init();

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

        for (let i = 0; i < orders.length; i++) {
            let order = orders[i];
            let orderItems = order['orderItems'];
            let name = (orderItems.length > 1)?(orderItems[0].name+"等"):orderItems[0].name;
            let html = '<tr class="timeline">\n' +
                '                    <td class="ordertimeline-time"><p class="ng-binding">'+ order['time'] +'</p>\n' +
                '                        <i class="ordertimeline-time-icon icon-uniE65E finish ng-scope"></i>\n' +
                '                    </td>\n' +
                '\n' +
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
                '                        <a class="ordertimeline-handle-detail">订单详情</a>\n' +
                '                    </td>\n' +
                '                </tr>';

            $(".order-list").append(html);
        }
    }
};