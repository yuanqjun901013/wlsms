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
            getLoad();
        })
        //定时刷新页面
        setInterval(function() {
            // show();//暂不用
            getLoad();
        }, 9000);
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
   <!-- <div data-options="title:'统计视图',region:'north',split:false,collapsible:false" style="height:300px">

    </div>-->
    <div data-options="title:'系统总览',region:'west',split:false,collapsible:false" style="width:27%;height:300px;">
        <table data-options="split:false,fit:true" style="width:100%">
            <tr align="center">
                <td data-options="split:false,fit:true" align="center"><div id="container" style="width: 300px;height:250px" data-options="region:'center',split:true,fit:true"></div></td>
            </tr>
        </table>
    </div>
    <div data-options="title:'底数总览',region:'center',split:false" style="width:27%;height:200px;">
        <table data-options="split:false,fit:true" style="width:100%">
            <tr align="center">
                <td data-options="split:false,fit:true" align="center"><div id="data" style="width: 300px;height: 250px" data-options="region:'center',split:true,fit:true"></div></td>
            </tr>
        </table>
    </div>
    <div data-options="title:'近一周底数情况',region:'east',split:false,collapsible:false" style="width:45%;height:200px;">
        <table data-options="split:false,fit:true" style="width:100%">
            <tr align="center">
                <td data-options="split:false,fit:true" align="center"><div id="stackChart" style="width: 520px;height: 250px" data-options="region:'center',split:true,fit:true"></div></td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south',split:false,collapsible:false" style="height:400px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="title:'任务发起',region:'west',split:false,collapsible:false" style="width:33%;"></div>
            <div data-options="title:'任务待认领',region:'center',split:false" style="width:33%"></div>
            <div data-options="title:'任务反馈',region:'east',split:false,collapsible:false" style="width:33%;"></div>
        </div>
    </div>
</div>
<script type="text/javascript" th:inline="none">
      function getLoad() {
          var cotData;
          var vcData;
          var chartData;
          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/data/data/cotData',//获取系统总览
              data: {},
              success: function (data) {
                  cotData = data.data;
              }
          });

          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/data/data/vcData',//获取底数录入情况统计
              data: {},
              success: function (data) {
                  vcData = data.data;
              }
          });


          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/data/data/chartData',//获取底数七天内情况统计
              data: {},
              success: function (data) {
                  chartData = data.data;
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
                      radius: '40%',
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


          //底数总览情况统计
          var dataDom = document.getElementById("data");
          var dataChart = echarts.init(dataDom);
          var dataApp = {};
          var dataOption;
          dataOption = {
              title: {
                  text: '底数总览',
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
                      radius: ['28%', '45%'],
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
                              fontSize: '25',
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

          //一周底数录入情况图
          var stackDom = document.getElementById("stackChart");
          var stackChart = echarts.init(stackDom);
          var stackApp = {};
          var stackOption;

          stackOption = {
              title: {
                  text: '底数一周榜',
                  subtext: '可供统计参考'
              },
              tooltip: {
                  trigger: 'axis'
              },
              legend: {
                  data: ['机器底数', '人工底数', '已融合', '未融合']
              },
              grid: {
                  left: '5%',
                  right: '8%',
                  bottom: '3%',
                  containLabel: true
              },
              xAxis: {
                  type: 'category',
                  boundaryGap: false,
                  data: chartData.dayList
              },
              yAxis: {
                  type: 'value'
              },
              series: chartData.chartValue
          };

          if (stackOption && typeof stackOption === 'object') {
              stackChart.setOption(stackOption);
          }
      }

</script>
</body>
</html>