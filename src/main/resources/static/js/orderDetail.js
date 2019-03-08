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

      $(".order-canteen-name").text(canteenName);
      $('.order-id').text(orderID);
      $('.order-deliveringTime').text(deliveringTime);
      $('.order-member-name').text(memberName);
      $('.order-member-phone').text(memberPhone);
      $('.order-member-address').text(addressStr);
      $('.order-totalPrice').text(totalPrice);

      for (let i = 0; i < orderItems.length; i++) {
          let orderItem = orderItems[i];
          let html = '<div class="orderprogress-totalrow">\n' +
              '                                <span class="cell name item">'+ orderItem['name'] +'</span>\n' +
              '                                <span class="cell quantity item">'+ orderItem['num'] +'</span>\n' +
              '                                <span class="cell price item">'+ orderItem['subtotal'] + '</span>\n' +
              '                            </div>';

          $('.order-items').append(html);
      }

  }

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
};