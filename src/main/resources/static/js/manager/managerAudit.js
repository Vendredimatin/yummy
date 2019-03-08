window.onload = function () {
    let allUnauditedInfo;
    getList();

    $(document).on('click', '.check button', function () {
        $(".unauditedInfo-list").css("display", "none");
        $(".profileinfo").css("display", 'block');
        let canteenID = $(this).parent().siblings(".canteen-id").text();
        console.log(canteenID);
        let unauditedInfo = getUnauditedInfo(canteenID);
        console.log(unauditedInfo);

        $('.profileinfo-value.id').val(canteenID);
        $(".profileinfo-value.name").val(unauditedInfo['canteenName']);
        $(".profileinfo-value.landlordName").val(unauditedInfo['landlordName']);
        $(".profileinfo-value.phone").val(unauditedInfo['phone']);
        $(".profileinfo-value.category").val(unauditedInfo['categories']);
        $(".profileinfo-value.province").val(unauditedInfo['address']['province']);
        $(".profileinfo-value.city").val(unauditedInfo['address']['city']);
        $(".profileinfo-value.district").val(unauditedInfo['address']['district']);
    });

    function getUnauditedInfo(canteenID) {
        for (let i = 0; i < allUnauditedInfo.length; i++) {
            console.log(allUnauditedInfo[i]);
            console.log(allUnauditedInfo[i]['canteenID']);
            if (allUnauditedInfo[i]['canteenID'] == canteenID) {
                return allUnauditedInfo[i];
            }
        }
    }

    $('.canteen-info-pass').click(function () {
        let d = {};
        d.canteenID = $('.profileinfo-value.id').val();
        $.ajax({
            url:'/manager/pass',
            type:'post',
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert(data['message']);
                window.location.href = "/managerAudit";
            },
            fail: function (data) {
                alert("fail")
            }
        });
    });

    $('.canteen-info-reject').click(function () {
        let d = {};
        d.canteenID = $('.profileinfo-value.id').val();
        $.ajax({
            url:'/manager/reject',
            type:'post',
            data:JSON.stringify(d),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert(data['message']);
                window.location.href = "/managerAudit";
            },
            fail: function (data) {
                alert("fail")
            }
        });
    });

    function getList() {
        $.ajax({
            url: '/manager/unauditedInfo/list',
            type: 'post',
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                allUnauditedInfo = data;
                initListHtml(data);
            },
            fail: function (data) {
                alert("fail")
            }
        });
    }

    function initListHtml(data) {
        for (let i = 0; i < data.length; i++) {
            let unauditedInfo = data[i];
            let html = '<tr>\n' +
                '                        <td class="canteen-id">' + unauditedInfo['canteenID'] + '</td>\n' +
                '                        <td class="canteen-name">' + unauditedInfo['canteenName'] + '</td>\n' +
                '                        <td class="canteen-time">' + unauditedInfo['time'] + '</td>\n' +
                '                        <td class="check"><button>审批</button></td>\n' +
                '                    </tr>';

            $(".unauditedInfo-list table").append(html);
        }
    }
};