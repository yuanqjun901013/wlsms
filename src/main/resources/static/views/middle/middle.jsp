<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../../themes/material-teal/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../demo/demo.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../echarts.min.js"></script>
    <SCRIPT th:inline="javascript">
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true" style="width:50%">
        <div id="container" style="width: 400px;height:400px" data-options="region:'center',split:true,fit:true"></div>
    </div>
    <div data-options="region:'center'">
        <div id="data" style="width: 400px;height: 400px" data-options="region:'center',split:true,fit:true"></div>
    </div>
</div>
<script th:inline="javascript">
        var cotData;
        var vcData;
        $.ajax({
            type: 'POST',
            async: false,
            dataType: "json",
            url: '/data/data/cotData',//获取系统总览
            data:{},
            success: function(data) {
                cotData = data.data;
            }
        });

        $.ajax({
            type: 'POST',
            async: false,
            dataType: "json",
            url: '/data/data/vcData',//获取底数录入情况统计
            data:{},
            success: function(data) {
                vcData = data.data;
            }
        });

    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    var option;
    option = {
        title: {
            text: '系统总览',
            subtext: '可供统计参考',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '50%',
                data: cotData
                    // [
                    // {value: 1048, name: '用户数'},
                    // {value: 735, name: '告警数'},
                    // {value: 580, name: '角色数'},
                    // {value: 484, name: '底数'},
                    // {value: 300, name: '任务数'}
                    // ]
                ,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }


    //底数录入情况统计
    var dataDom = document.getElementById("data");
    var dataChart = echarts.init(dataDom);
    var dataApp = {};
    var dataOption;
    dataOption = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '5%',
            left: 'center'
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: vcData
                    // [
                    // {value: 1048, name: '人工底数'},
                    // {value: 735, name: '机器底数'},
                    // {value: 300, name: '已校对'}
                    // ]
            }
        ]
    };

    if (dataOption && typeof dataOption === 'object') {
        dataChart.setOption(dataOption);
    }
</script>
</body>
</html>