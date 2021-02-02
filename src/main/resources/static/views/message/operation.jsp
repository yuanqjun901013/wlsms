<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WLSMS</title>
    <link rel="stylesheet" type="text/css" href="../../themes/material-teal/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../demo/demo.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
    <SCRIPT th:inline="javascript">
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            operationList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="operationList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function operationList() {//展示最新的动态消息列表
      var dg =  $('#operationList').datagrid({
            url:'/admin/message/getOperationList',//消息
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
                {field:'id',title:'ID',width:80,align:'center'},
                {field:'userNo',title:'工号',width:80,align:'center'},
                {field:'buildTime',title:'发生时间',width:80,align:'center'},
                {field:'title',title:'操作类型',width:80,align:'center'},
                {field:'content',title:'业务内容',width:100,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>