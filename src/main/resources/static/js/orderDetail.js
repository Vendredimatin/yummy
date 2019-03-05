window.onload = function () {
  init();

  function init() {
      $.ajax({
         url:"/order/detail/get",
         type:'post',
          contentType: "application/json;charset=utf-8",
          success: function (data) {
             console.log(data);
             initHtml(data);
          },
          fail: function (data) {
              alert("fail");
          }
      });
  }

  function initHtml(order) {
      let orderID = order['id'];
      let canteenName= order['canteenName'];
      let deliveringTime = order['deliveringTime'];
      let memberName = order['memberName'];
      let memberPhone = order['memberPhone'];
      let memberAddress = order['memberAddress'];
      let addressStr = memberAddress['province']+memberAddress['city']+memberAddress['district']+memberAddress['detail'];
      let totalPrice = order['totalPrice'];

      let orderItems = order['orderItems'];

  }
};