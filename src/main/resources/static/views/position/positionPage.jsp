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
            getPositionList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="getPositionList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function getPositionList() {//展示阵地列表
        var queryBt = $('#queryBt').textbox('getValue');
       $('#getPositionList').datagrid({
            url:'/admin/position/getPositionList',//参数
            method: 'post',
            //携带参数
            queryParams: {
                "request": queryBt
            },
            fitColumns:true,
            striped:true,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            remoteFilter: true,
            clientPaging: false,
           nowrap:false,//自动换行
           toolbar:'#toolbar',
            columns:[[
                {field:'id',title:'编号',width:80,align:'center'},
                {field:'positionName',title:'阵地名称',width:80,align:'center'},
                {field:'positionCode',title:'阵地编码',width:80,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            getPositionList();
        }})


</script>
</div>
</body>
</html>