window.onload = function () {
    init();
    
    function init() {
        $.ajax({
            url:"/member/balance/get",
            type:"post",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                $(".balance-number").text(data['balance']['balance']);
                $(".balance-cost").text(data['totalCost']);
                $(".orders-number").text(data['totalNums']);
            },
            fail:function (data) {
                alert("fail");
            }
        })
    }
};