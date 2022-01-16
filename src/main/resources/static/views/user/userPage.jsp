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
            userList();
        })
    </SCRIPT>

</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="userList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
    <div id="dlg" class="easyui-dialog" style="width:600px; height: 400px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <h3>用户信息</h3>
            <div style="margin-bottom:15px">
                <input id="cbgAdd" name="positionCode"  label="地址:" style="width:460px;">
            </div>
            <div style="margin-bottom:15px">
                <input id="userName" class="easyui-textbox" type="text" name="userName" data-options="required:true" label="姓名:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
                <input id="userNo" class="easyui-textbox" type="text" name="userNo" data-options="required:true" label="工号:" labelPosition="left" style="width:230px;">
            </div>
            <div style="margin-bottom:15px">
                <input id="job" class="easyui-textbox" type="text" name="job" label="岗位:" labelPosition="left" style="width:230px;" >&nbsp;&nbsp;
                <input id="tel" class="easyui-textbox" type="text" name="tel" label="固话:" labelPosition="left" style="width:230px;">
            </div>
            <div style="margin-bottom:15px">
                <input id="phone" class="easyui-textbox" type="text" name="phone" label="手机:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
                <input id="email" class="easyui-textbox" type="text" name="email" label="Email:" labelPosition="left"  style="width:230px;">
            </div>
            <div style="margin-bottom:15px">
                <form id="ff">
                    <input id="age" class="easyui-textbox" type="text" name="age" label="年龄:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
                    性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    男<input id="sexM" class="easyui-radiobutton" name="sex" value="1">&nbsp;&nbsp;
                    女<input id="sexW" class="easyui-radiobutton" name="sex" value="2">
                </form>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>

<script type="text/javascript" th:inline="none">
    function userList() {//展示用户列表
        var queryBt = $('#queryBt').textbox('getValue');
       $('#userList').datagrid({
            url:'/admin/user/getUserList',//用户
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
            remoteFilter: true,
            clientPaging: false,
            toolbar:'#toolbar',
            columns:[[
                {field:'id',title:'编号',width:30,align:'center'},
                {field:'userNo',title:'工号',width:80,align:'center'},
                {field:'userName',title:'姓名',width:80,align:'center'},
                {field:'job',title:'岗位',width:80,align:'center'},
                {field:'tel',title:'座机',width:80,align:'center'},
                {field:'phone',title:'移动号',width:120,align:'center'},
                {field:'email',title:'邮箱',width:180,align:'center'},
                // {field:'positionCode',title:'地址编码',width:180,align:'center'},
                {field:'positionName',title:'地址',width:100,align:'center'}
            ]]
        });
    }

    //新增用户信息
    function newUser(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增');
        $('#fm').form('clear');
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
    //修改用户信息
    function editUser(){
        var row = $('#userList').datagrid('getSelected');
        if (row){
            $('#dlgUpdate').dialog('open').dialog('center').dialog('setTitle','修改');
            $('#fmm').form('clear');
            $('#cbgUpdate').combogrid({
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
            $('#fmm').form('load',row);
        }
    }
    function saveUser(){
        $('#fm').form('submit',{
            url: '/admin/user/saveUser',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result == 1){
                    $.messager.alert("消息提醒", "保存成功!", "info",function (){
                        $('#dlg').dialog('close');        // close the dialog
                        $('#userList').datagrid('reload');    // reload the user data
                    });
                }else if(result == 0){
                    $.messager.alert("消息提醒","用户工号已存在");
                }else{
                    $.messager.alert("消息提醒","保存失败，请重试");
                }
            }
        });
    }
    function destroyUser(){
        var row = $('#userList').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认提醒','确定删除该用户?',function(r){
                if (r){
                    $.post('/admin/user/deleteUser',{id:row.id},function(result){
                        if (result == 1){
                            $('#userList').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.alert("消息提醒","删除失败，请重试");
                        }
                    },'json');
                }
            });
        }
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            userList();
        }})

    function updateUser(){
        $('#fmm').form('submit',{
            url: '/admin/user/editUserByUserNo',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result == 1){
                    $.messager.alert("消息提醒", "保存成功!", "info",function (){
                        $('#dlgUpdate').dialog('close');        // close the dialog
                        $('#userList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒","保存失败，请重试");
                }
            }
        });
    }
</script>
</div>
<div id="dlgUpdate" class="easyui-dialog" style="width:600px; height: 400px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlgUpdate-buttons'">
    <form id="fmm" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>用户信息</h3>
        <div style="margin-bottom:15px">
            <input id="cbgUpdate" name="positionCode"  label="地址:" style="width:460px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="userName" data-options="required:true" label="姓名:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="userNo" readonly="readonly" label="工号:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input  class="easyui-textbox" type="text" name="job" label="岗位:" labelPosition="left" style="width:230px;" >&nbsp;&nbsp;
            <input  class="easyui-textbox" type="text" name="tel" label="固话:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input  class="easyui-textbox" type="text" name="phone" label="手机:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
            <input  class="easyui-textbox" type="text" name="email" label="Email:" labelPosition="left"  style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <form id="fff">
                <input class="easyui-textbox" type="text" name="age" label="年龄:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
                性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                男<input  class="easyui-radiobutton" name="sex" value="1">&nbsp;&nbsp;
                女<input  class="easyui-radiobutton" name="sex" value="2">
            </form>
        </div>

    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateUser()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>