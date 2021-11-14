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
            getWaitingTaskList();
            getTodoTaskList();
        })
        //定时刷新页面
        setInterval(function() {
            // show();//暂不用
            getLoad();
            getWaitingTaskList();
            getTodoTaskList();
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
            <div data-options="title:'快捷操作区',region:'west',split:false,collapsible:false" style="width:10%;">
                <table data-options="split:false,fit:true" style="width:100%;height: 100%" valign="middle">
                    <tr align="center" valign="middle">
                        <td data-options="split:false,fit:true" valign="middle" align="center">
                            <a href="javascript:void(0)" class="easyui-linkbutton c1" onclick="addTask()" style="width:120px;height: 100px">下发任务</a>
                        </td>
                    </tr>
                    <tr align="center" valign="middle">
                        <td data-options="split:false,fit:true" valign="middle" align="center">
                            <a href="javascript:void(0)" class="easyui-linkbutton c1" onclick="addBatchManual()" style="width:120px;height: 100px">人工底数</a>
                        </td>
                    </tr>
                    <tr align="center" valign="middle">
                        <td data-options="split:false,fit:true" valign="middle" align="center">
                            <a href="javascript:void(0)" class="easyui-linkbutton c1" onclick="addMachine()" style="width:120px;height: 100px">机器底数</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div data-options="title:'任务待认领',region:'center',split:false" style="width:40%">
                <div id="getWaitingTaskList" style="width:100%" data-options="region:'center',split:true"></div>
                <br>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #843534"><h3>详细操作请致:组网协同>任务管理>待认领</h3></span></div>
                <!--
                <div>&nbsp;&nbsp;&nbsp;&nbsp;<a href='forwardToPage?url=views/task/waitingTask&text=待认领&menu=getWaitingTask' style='text-decoration:none;' ><span style="color: #6ab8f7">更多···</span></a></div>
                <div onclick="toWaiting()">更多···</div>
                -->
                <br>
            </div>
            <div data-options="title:'任务反馈',region:'east',split:false,collapsible:false" style="width:50%;">
                <div id="getTodoTaskList" style="width:100%" data-options="region:'center',split:true"></div>
                <br>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #843534"><h3>详细操作请致:组网协同>任务管理>认领待处理</h3></span></div>
                <!--
                <div>&nbsp;&nbsp;&nbsp;&nbsp;<a href='/forwardToPage?url=views/task/todoTask&text=认领待处理&menu=getTodoTask' style='text-decoration:none;' ><span style="color: #6ab8f7">更多···</span></a></div>
                <div onclick="toTodo()">更多···</div>
                -->
                <br>

            </div>
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

      function  getWaitingTaskList(){
          //待认领数据
          $('#getWaitingTaskList').datagrid({
              url:'/task/getWaitingTask',//参数
              method: 'post',
              //携带参数
              queryParams: {},
              fitColumns:true,
              striped:true,
              pagination:false,
              rownumbers:false,
              remoteFilter: true,
              clientPaging: false,
              nowrap:false,//自动换行
              // toolbar:'#toolbar',
              singleSelect:true,
              checkOnSelect:false,
              selectOnCheck:false,
              columns:[[
                  // {field:'ck',checkbox:true,align:'center'},
                  {field:'title',title:'任务标题',formatter:function(value,row,index)
                      {
                          return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='getDetail("+row.id+")'>"+row.title+"</a>";
                      },
                      width:200,align:'center'},
                  {field:'userName',title:'任务发起人',width:100,align:'center'},
                  {field:'buildTime',title:'下发时间',width:150,align:'center'},
                  {field:'todo',title:'操作',formatter:function(value,row,index)
                      {
                          return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='receiverTask("+row.id+")'>认领</a>";
                      },
                      width:150,align:'center'}
              ]]
          });
      }

      function getTodoTaskList(){
          //待反馈数据
          $('#getTodoTaskList').datagrid({
              url:'/task/getTodoTask',//参数
              method: 'post',
              //携带参数
              queryParams: {},
              fitColumns:true,
              striped:true,
              pagination:false,
              rownumbers:false,
              remoteFilter: true,
              clientPaging: false,
              nowrap:false,//自动换行
              // toolbar:'#toolbar',
              singleSelect:true,
              checkOnSelect:false,
              selectOnCheck:false,
              columns:[[
                  // {field:'ck',checkbox:true,align:'center'},
                  {field:'title',title:'任务标题',formatter:function(value,row,index)
                      {
                          return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='getDetail("+row.id+")'>"+row.title+"</a>";
                      },
                      width:200,align:'center'},
                  {field:'userName',title:'任务发起人',width:100,align:'center'},
                  {field:'buildTime',title:'下发时间',width:150,align:'center'},
                  {field:'receiverUserName',title:'认领人',width:150,align:'center'},
                  {field:'receiverTime',title:'认领时间',width:100,align:'center'},
                  {field:'todo',title:'操作',formatter:function(value,row,index)
                      {
                          return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='editTask("+row.id+")'>办理</a>";;
                      },
                      width:150,align:'center'}
              ]]
          });
      }

      function receiverTask(id){
          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/task/receiverTask',//认领任务
              data:{
                  "id":id,
              },
              success: function(result){
                  if(result.success){
                      $.messager.alert("消息提醒", result.data, "info",function (){
                          $('#getWaitingTaskList').datagrid('reload');    // reload the user data
                          $('#getTodoTaskList').datagrid('reload');    // reload the user data
                      });
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }

      //详情
      function getDetail(id){
          $('#taskDetail').dialog('open').dialog('center').dialog('setTitle','任务详情');
          $('#fmDetail').form('clear');
          document.getElementById("id").style.display="none";//隐藏
          $("#feedbackContent").textbox('readonly',true);
          document.getElementById("saveButtons").style.display="none";//隐藏
          document.getElementById("rejectButtons").style.display="none";//隐藏
          document.getElementById("feedbackDiv").style.display="";//显示

          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/task/getTaskDetail',//获取任务详情
              data:{
                  "id":id
              },
              success: function(result){
                  if(result.success){
                      //赋值
                      $("#title").textbox('setValue', result.data.title);
                      $("#content").textbox('setValue', result.data.content);
                      $("#stateName").textbox('setValue', result.data.stateName);
                      $("#taskTypeName").textbox('setValue', result.data.taskTypeName)
                      $("#positionName").textbox('setValue', result.data.positionName);
                      $("#id").textbox('setValue', result.data.id);
                      $("#userName").textbox('setValue', result.data.userName);
                      $("#buildTime").textbox('setValue', result.data.buildTime);
                      $("#receiverUserName").textbox('setValue', result.data.receiverUserName);
                      $("#receiverTime").textbox('setValue', result.data.receiverTime);
                      $("#feedbackUserName").textbox('setValue', result.data.feedbackUserName);
                      $("#feedbackTime").textbox('setValue', result.data.feedbackTime);
                      $("#feedbackContent").textbox('setValue', result.data.feedbackContent);
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }


      //任务反馈
      function editTask(id){
          $('#taskDetail').dialog('open').dialog('center').dialog('setTitle','任务编辑');
          $('#fmDetail').form('clear');
          document.getElementById("id").style.display="none";//隐藏
          document.getElementById("saveButtons").style.display="";//展示
          document.getElementById("rejectButtons").style.display="";//展示
          document.getElementById("feedbackDiv").style.display="none";//隐藏
          $("#feedbackContent").textbox('readonly',false);

          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/task/getTaskDetail',//获取任务详情
              data:{
                  "id":id
              },
              success: function(result){
                  if(result.success){
                      //赋值
                      $("#title").textbox('setValue', result.data.title);
                      $("#content").textbox('setValue', result.data.content);
                      $("#stateName").textbox('setValue', result.data.stateName);
                      $("#taskTypeName").textbox('setValue', result.data.taskTypeName)
                      $("#positionName").textbox('setValue', result.data.positionName);
                      $("#id").textbox('setValue', result.data.id);
                      $("#userName").textbox('setValue', result.data.userName);
                      $("#buildTime").textbox('setValue', result.data.buildTime);
                      $("#receiverUserName").textbox('setValue', result.data.receiverUserName);
                      $("#receiverTime").textbox('setValue', result.data.receiverTime);
                      $("#feedbackUserName").textbox('setValue', result.data.feedbackUserName);
                      $("#feedbackTime").textbox('setValue', result.data.feedbackTime);
                      $("#feedbackContent").textbox('setValue', result.data.feedbackContent);
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }

      //反馈任务
      function feedbackTask(){
          var id = $("#id").textbox('getValue');
          var feedbackContent = $("#feedbackContent").textbox('getValue');
          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/task/feedbackTask',//反馈任务
              data:{
                  "id":id,
                  "feedbackContent":feedbackContent
              },
              success: function(result){
                  if(result.success){
                      $.messager.alert("消息提醒", result.data, "info",function (){
                          $('#taskDetail').dialog('close');        // close the dialog
                          $('#getTodoTaskList').datagrid('reload');    // reload the user data
                      });
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }
      //拒绝任务
      function rejectTask(){
          var id = $("#id").textbox('getValue');
          var feedbackContent = $("#feedbackContent").textbox('getValue');
          $.ajax({
              type: 'POST',
              async: false,
              dataType: "json",
              url: '/task/rejectTask',//拒绝任务
              data:{
                  "id":id,
                  "feedbackContent":feedbackContent
              },
              success: function(result){
                  if(result.success){
                      $.messager.alert("消息提醒", result.data, "info",function (){
                          $('#taskDetail').dialog('close');        // close the dialog
                          $('#getTodoTaskList').datagrid('reload');    // reload the user data
                      });
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }

      // function toWaiting(){
      //     $("#waiting").window("open");
      // }
      // function toTodo(){
      //     $("#todo").window("open");
      // }

      function addTask(){
          $('#taskAdd').dialog('open').dialog('center').dialog('setTitle','下发任务');
          $('#fmAdd').form('clear');
          $('#typeAdd').combobox({
              url:'/task/taskTypeList',
              valueField:'taskType',
              textField:'taskTypeName'
          });
          $('#cbgAdd').combogrid({
              delay: 250,
              mode: 'remote',
              url: '/admin/position/getPositionArr',
              idField: 'positionCode',
              textField: 'positionName',
              striped:true,
              multiple: false,
              fitColumns: true,
              columns: [[
                  {field:'positionName',title:'地址',width:100,sortable:true},
                  {field:'positionCode',title:'标识码',width:80,sortable:true}
              ]]
          });
      }

      function saveTask(){
          $('#fmAdd').form('submit',{
              url: '/task/saveTask',
              onSubmit: function(){
                  return $(this).form('validate');
              },
              success: function(result){
                  var result = eval('('+result+')');
                  if(result.success){
                      $.messager.alert("消息提醒", result.data, "info",function (){
                          $('#getWaitingTaskList').datagrid('reload');// reload the user data
                          $('#taskAdd').dialog('close');        // close the dialog

                      });
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }

      //添加文件信息
      function addBatchManual(){
          $('#dlg').dialog('open').dialog('center').dialog('setTitle','上报人工底数');
          $('#fm').form('clear');
          $('#cbg').combogrid({
              delay: 250,
              mode: 'remote',
              url: '/admin/position/getPositionArr',
              idField: 'positionCode',
              textField: 'positionName',
              striped:true,
              multiple: false,
              fitColumns: true,
              columns: [[
                  {field:'positionName',title:'地址',width:100,sortable:true},
                  {field:'positionCode',title:'标识码',width:80,sortable:true}
              ]]
          });
      }

      function saveBatchManual(){
          $('#fm').form('submit',{
              url: '/data/buildNew/importManual',
              onSubmit: function(){
                  return $(this).form('validate');
              },
              success: function(result){
                  var result = eval('('+result+')');
                  if(result.success){
                      $.messager.alert("消息提醒", result.data, "info",function (){
                          $('#dlg').dialog('close');        // close the dialog
                      });
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }

      //添加文件信息
      function addMachine(){
          $('#dlgMac').dialog('open').dialog('center').dialog('setTitle','上报机器底数');
          $('#fmMac').form('clear');
          $('#cbgMac').combogrid({
              delay: 250,
              mode: 'remote',
              url: '/admin/position/getPositionArr',
              idField: 'positionCode',
              textField: 'positionName',
              striped:true,
              multiple: false,
              fitColumns: true,
              columns: [[
                  {field:'positionName',title:'地址',width:100,sortable:true},
                  {field:'positionCode',title:'标识码',width:80,sortable:true}
              ]]
          });
      }

      function saveMachine(){
          $('#fmMac').form('submit',{
              url: '/data/buildNew/importMachine',
              onSubmit: function(){
                  return $(this).form('validate');
              },
              success: function(result){
                  var result = eval('('+result+')');
                  if(result.success){
                      $.messager.alert("消息提醒", result.data, "info",function (){
                          $('#dlgMac').dialog('close');        // close the dialog
                      });
                  }else {
                      $.messager.alert("消息提醒",result.msg);
                  }
              }
          });
      }

</script>


<div id="taskAdd" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#add-buttons'">
    <form id="fmAdd" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>任务下发</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" data-options="required:true" name="title" label="任务标题:" labelPosition="left" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" data-options="required:true" name="content" multiline="true" label="任务内容:" labelPosition="left" style="width:400px;height: 60px">
        </div>
        <div style="margin-bottom:15px">
            <input id="typeAdd" name="taskType" data-options="required:true" label="任务类型:" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input id="cbgAdd" name="positionCode" data-options="required:true" label="地址:" style="width:400px;">
        </div>
    </form>
</div>
<div id="add-buttons">
    <a href="javascript:void(0)" id="addButtons" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveTask()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#taskAdd').dialog('close')" style="width:90px">取消</a>
</div>

<div id="taskDetail" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#detail-buttons'">
    <form id="fmDetail" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>任务详情</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="id" readonly="readonly" label="任务编号:" labelPosition="left">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="title" readonly="readonly" label="任务标题:" labelPosition="left" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="content" multiline="true"  readonly="readonly" label="任务内容:" labelPosition="left" style="width:400px;height: 60px">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="stateName" readonly="readonly" label="任务状态:" labelPosition="left" style="width:200px;">
            <input class="easyui-textbox" id="taskTypeName" readonly="readonly" label="任务类型:" labelPosition="left" style="width:200px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="positionName" readonly="readonly" label="地址:" labelPosition="left" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="userName" readonly="readonly" label="发起人:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" id="buildTime" readonly="readonly" label="下发时间:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="receiverUserName" readonly="readonly" label="认领人:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" id="receiverTime" readonly="readonly" label="认领时间:" labelPosition="left" style="width:230px;">
        </div>
        <div id = "feedbackDiv" style="margin-bottom:15px">
            <input class="easyui-textbox" id="feedbackUserName" readonly="readonly" label="反馈人:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" id="feedbackTime" readonly="readonly" label="反馈时间:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="feedbackContent" multiline="true"  readonly="readonly" label="反馈内容:" labelPosition="left" style="width:400px;height: 60px">
        </div>
    </form>
</div>
<div id="detail-buttons">
    <a href="javascript:void(0)" id="saveButtons" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="feedbackTask()" style="width:90px">完成反馈</a>
    <a href="javascript:void(0)" id="rejectButtons" class="easyui-linkbutton c6" iconCls="icon-no" onclick="rejectTask()" style="width:90px">拒绝任务</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#taskDetail').dialog('close')" style="width:90px">取消</a>
</div>
<!--
<div id="waiting" class="easyui-window" title="待认领"  style="width:900px;height:600px;padding:10px;"
     data-options="iconCls:'icon-man',modal:true,resizable:false,minimizable:false,maximizable:false">
    <iframe style="vertical-align:bottom;" width="100%" height="100%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="waitingTask"></iframe>
</div>
<div id="todo" class="easyui-window" title="待办理"  style="width:900px;height:600px;padding:10px;"
     data-options="iconCls:'icon-man',modal:true,resizable:false,minimizable:false,maximizable:false">
    <iframe style="vertical-align:bottom;" width="100%" height="100%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="todoTask"></iframe>
</div>
-->
<div id="dlg" class="easyui-dialog" style="width:520px; height: 260px"
     data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
    <form id="fm" method="post" action="/data/buildNew/importManual" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
        <div style="margin-bottom:15px">
            <input id="cbg" name="positionCode"  label="地址:" style="width:400px;">
        </div>
        <div style="margin-bottom:40px">
            <input id="file" name="file" class="easyui-filebox" label="文件:" labelPosition="left"  style="width:400px">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBatchManual()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<div id="dlgMac" class="easyui-dialog" style="width:520px; height: 260px"
     data-options="closed:true,modal:true,border:'thin',buttons:'#dlgMac-buttons'">
    <form id="fmMac" method="post" action="/data/buildNew/importMachine" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
        <div style="margin-bottom:15px">
            <input id="cbgMac" name="positionCode"  label="地址:" style="width:400px;">
        </div>
        <div style="margin-bottom:40px">
            <input id="fileMac" name="file" class="easyui-filebox" label="文件:" labelPosition="left"  style="width:400px">
        </div>
    </form>
</div>
<div id="dlgMac-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveMachine()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgMac').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>