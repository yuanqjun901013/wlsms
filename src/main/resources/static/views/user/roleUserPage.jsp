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
            roleAuthList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRoleUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delUserRole()">删除</a>
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="roleAuthList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
    <div id="dlg" class="easyui-dialog" style="width:500px; height: 300px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <h3>授权信息</h3>
            <div style="margin-bottom:15px">
                <input id="cbgUser" name="userNo"  label="姓名:" style="width:320px;">
            </div>
                <div style="margin-bottom:15px">
                <input id="cbgRole" name="roleCode"  label="角色:" style="width:320px;">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUserRole()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
<script type="text/javascript" th:inline="none">
    function roleAuthList() {//展示用户角色列表
        var queryBt = $('#queryBt').textbox('getValue');
       $('#roleAuthList').datagrid({
            url:'/admin/roleUser/queryUserRoleInfo',//用户角色列表
            method: 'post',
            //携带参数
            queryParams: {
                "request":queryBt
            },
            fitColumns:true,
            striped:true,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            checkbox:false,
            remoteFilter: true,
            clientPaging: false,
            toolbar:'#toolbar',
            columns:[[
                {field:'id',title:'编号',width:30,align:'center'},
                {field:'roleCode',title:'角色编码',width:80,align:'center'},
                {field:'roleName',title:'角色名称',width:80,align:'center'},
                {field:'userNo',title:'工号',width:80,align:'center'},
                {field:'userName',title:'姓名',width:80,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            roleAuthList();
        }})

    //新增用户信息
    function newRoleUser(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增授权');
        $('#fm').form('clear');
    }
    $('#cbgUser').combogrid({
        delay: 250,
        mode: 'remote',
        url: '/admin/user/getUserArr',
        idField: 'userNo',
        textField: 'userName',
        striped:true,
        multiple: true,
        fitColumns: true,
        columns: [[
            {field:'userNo',title:'工号',width:100,sortable:true},
            {field:'userName',title:'姓名',width:120,sortable:true}
        ]]
    });

    $('#cbgRole').combogrid({
        delay: 250,
        mode: 'remote',
        url: '/admin/role/queryRoleArr',
        idField: 'roleCode',
        textField: 'roleName',
        striped:true,
        fitColumns: true,
        columns: [[
            {field:'roleCode',title:'角色编码',width:100,sortable:true},
            {field:'roleName',title:'角色名',width:120,sortable:true}
        ]]
    });

    function saveUserRole(){
        $('#fm').form('submit',{
            url: '/admin/roleUser/saveUserRole',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", "授权成功!", "info",function (){
                        $('#dlg').dialog('close');        // close the dialog
                        $('#roleAuthList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒","授权失败，请重试");
                }
            }
        });
    }

    function delUserRole(){
        var row = $('#roleAuthList').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认提醒','确定删除用户权限?',function(r){
                if (r){
                    $.post('/admin/roleUser/delUserRole',{id:row.id},function(result){
                        if (result.success){
                            $('#roleAuthList').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.alert("消息提醒","删除失败，请重试");
                        }
                    },'json');
                }
            });
        }
    }
</script>
</div>
</body>
</html>