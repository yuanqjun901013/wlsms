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
            parametersList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="parametersList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function parametersList() {//展示基础参数列表
      var dg =  $('#parametersList').datagrid({
            url:'/admin/param/getParametersList',//参数
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
                {field:'id',title:'参数编号',width:80,align:'center'},
                {field:'paramType',title:'参数类型',width:80,align:'center'},
                {field:'paramName',title:'参数名称',width:80,align:'center'},
                {field:'paramValue',title:'参数值',width:80,align:'center'},
                {field:'todo',title:'操作',width:100,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>