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
    <div data-options="region:'center',split:true">
    <div id="container" style="height:600px" data-options="split:true,fit:true"></div>
    </div>
</div>
<script th:inline="javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};

    var option;


    var data = [
        [
            [97,8,150,'设备1','设备1']
        ],
        [
            [45,6,400,'设备2','设备2']
        ],
        [
            [79,4,150,'设备3','设备3']
        ]
    ];

    option = {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#ffffff'
        }]),
        title: {
            text: 'xx设备布局' ,
            left: '5%',
            top: '3%'
        },
        legend: {
            right: '10%',
            top: '3%',
            data: ['设备1', '设备2', '设备3']
        },
        grid: {
            left: '8%',
            top: '10%'
        },
        xAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        yAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
        },
        series: [
            {
            name: '设备1',
            data: data[0],
            type: 'scatter',
            symbolSize: function (data) {
                return Math.sqrt(data[2]) / 1;
            },
            emphasis: {
                focus: 'series',
                label: {
                    show: true,
                    formatter: function (param) {
                        return param.data[3];
                    },
                    position: 'top'
                }
            },
            itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(120, 36, 50, 0.5)',
                shadowOffsetY: 5,
                color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: 'rgb(251, 118, 123)'
                }, {
                    offset: 1,
                    color: 'rgb(204, 46, 72)'
                }])
            }
        },
        {
            name: '设备2',
            data: data[1],
            type: 'scatter',
            symbolSize: function (data) {
                return Math.sqrt(data[2]) / 1;
            },
            emphasis: {
                focus: 'series',
                label: {
                    show: true,
                    formatter: function (param) {
                        return param.data[3];
                    },
                    position: 'top'
                }
            },
            itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(25, 100, 150, 0.5)',
                shadowOffsetY: 5,
                color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: 'rgb(129, 227, 238)'
                }, {
                    offset: 1,
                    color: 'rgb(25, 183, 207)'
                }])
            }
        },
            {
                name: '设备3',
                data: data[2],
                type: 'scatter',
                symbolSize: function (data) {
                    return Math.sqrt(data[2]) / 1;
                },
                emphasis: {
                    focus: 'series',
                    label: {
                        show: true,
                        formatter: function (param) {
                            return param.data[3];
                        },
                        position: 'top'
                    }
                },
                itemStyle: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(150,119,25,0.5)',
                    shadowOffsetY: 5,
                    color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                        offset: 0,
                        color: 'rgb(210,205,96)'
                    }, {
                        offset: 1,
                        color: 'rgb(229,177,19)'
                    }])
                }
            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }
</script>
</body>
</html>