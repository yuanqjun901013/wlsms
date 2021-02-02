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
            roleAuthList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="roleAuthList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function roleAuthList() {//展示用户角色列表
      var dg =  $('#roleAuthList').datagrid({
            url:'/admin/roleUser/queryUserRoleInfo',//角色
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
                {field:'id',title:'编号',width:30,align:'center'},
                {field:'roleCode',title:'角色编码',width:80,align:'center'},
                {field:'roleName',title:'角色名称',width:80,align:'center'},
                {field:'userNo',title:'工号',width:80,align:'center'},
                {field:'todo',title:'操作',width:60,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>