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
    <SCRIPT th:inline="javascript">
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            getAlarmInfoList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="getAlarmInfoList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function getAlarmInfoList() {//展示列表
      var dg =  $('#getAlarmInfoList').datagrid({
            url:'/alarm/alarm/getAlarmInfoList',//参数
            method: 'post',
            //携带参数
            queryParams: {
            },
            fitColumns:true,
            striped:true,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            remoteFilter: true,
            clientPaging: false,
            columns:[[
                {field:'id',title:'编号',width:80,align:'center'},
                {field:'alarmTitle',title:'告警标题',width:80,align:'center'},
                {field:'alarmContent',title:'告警内容',width:80,align:'center'},
                {field:'buildTime',title:'告警时间',width:80,align:'center'},
                {field:'todo',title:'操作',width:100,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>