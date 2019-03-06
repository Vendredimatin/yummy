window.onload = function () {
    init();

    function init() {
        $.ajax({
           url:'/manager/statistics',
           type:'post',
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                console.log(data);
                alert(data['message']);
                initChart(data);
            },
            fail: function (data) {
                alert("fail")
            }
        });
    }


    
    function initChart(data) {
        $(".balance-number").text(data['yummyProfit']);
        initBarChart(data['memberNums'],data['canteenNums']);
        initMemCostChart(data['memCostMap']);
        initCanSellChart(data['canSellMap']);
    }
    
    function initMemCostChart(memCostMap) {
        let labels = ["100以下","100~500","500~1000","1000~5000","5000以上"];
        let percentages = [];
        let backColor1 = [];
        backColor1.push('#FF6384','#36A2EB','#FFCE96','#FFCE00','#36E2EB');//,'#838B2E','#B2E6C3','#DCB4B8','#9FC3F7');
        for(let i in memCostMap){
            let percentage = memCostMap[i];
            percentages.push(percentage);
        }

        var myChart1 = new Chart($("#chart2"), {
            type: 'pie',
            data: {
                labels: labels, //矩形标题
                datasets: [{
                    data: percentages, //所占整圆的比例
                    backgroundColor: backColor1, //背景色
                    hoverBackgroundColor: backColor1
                }]
            }
        });
    }
    
    function initCanSellChart(canSellMap) {
        let labels = ["50以下","50~100","100~500","500以上"];
        let percentages = [];
        let backColor1 = [];
        backColor1.push('#838B2E','#B2E6C3','#DCB4B8','#9FC3F7');//,'#838B2E','#B2E6C3','#DCB4B8','#9FC3F7');
        for(let i in canSellMap){
            let percentage = canSellMap[i];
            percentages.push(percentage);
        }

        var myChart1 = new Chart($("#chart3"), {
            type: 'pie',
            data: {
                labels: labels, //矩形标题
                datasets: [{
                    data: percentages, //所占整圆的比例
                    backgroundColor: backColor1, //背景色
                    hoverBackgroundColor: backColor1
                }]
            }
        });
    }

    function initBarChart(memberNums,canteenNums) {
        var barChart = new Chart($("#chart1"), {
            type: 'bar',
            data: {
                labels: ["会员数", "餐厅数"],
                datasets: [{
                    label: '数量',
                    data: [memberNums, canteenNums],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(54, 162, 235, 0.6)'/*,
                        'rgba(255, 206, 86, 0.6)',
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(153, 102, 255, 0.6)',
                        'rgba(255, 159, 64, 0.6)',
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(54, 162, 235, 0.6)',
                        'rgba(255, 206, 86, 0.6)',
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(153, 102, 255, 0.6)'*/
                    ]
                }]
            }
        });

    }
};