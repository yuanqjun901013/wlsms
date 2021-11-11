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
            roleList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
    <div id="roleList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
<script type="text/javascript" th:inline="none">
    function roleList() {//展示角色列表
      var dg =  $('#roleList').datagrid({
            url:'/admin/role/queryRole',//角色列表
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
            nowrap:false,//自动换行
            columns:[[
                {field:'id',title:'编号',width:30,align:'center'},
                {field:'roleCode',title:'角色编码',width:80,align:'center'},
                {field:'roleName',title:'角色名称',width:80,align:'center'},
                {field:'menuInfo',title:'菜单权限',width:500,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>