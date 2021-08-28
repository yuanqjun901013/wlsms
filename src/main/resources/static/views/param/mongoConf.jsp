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
            mongoDbList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateMongoDb()">修改</a>
    </div>
    <div id="mongoDbList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function mongoDbList() {//展示基础参数列表
        $('#mongoDbList').datagrid({
            url:'/mongo/config/getMongoDbList',//参数
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
            toolbar:'#toolbar',
            columns:[[
                {field:'id',title:'编号',width:80,align:'center'},
                {field:'mongodbIp',title:'mongo数据IP地址',width:80,align:'center'},
                {field:'mongodbDatabase',title:'数据库名称',width:80,align:'center'},
                {field:'mongoUser',title:'用户名',width:80,align:'center'},
                {field:'collectionName',title:'集合名',width:80,align:'center'}
            ]]
        });
    }

    function updateMongoDb(){
        var row = $('#mongoDbList').datagrid('getSelected');
        if (row){
            $('#dlgUpdate').dialog('open').dialog('center').dialog('setTitle','mongo配置修改');
            $('#fmm').form('load',row);
        }
    }

    function mongoDbSubmit(){
        $('#fmm').form('submit',{
            url: '/mongo/config/saveMongoConfig',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.msg, "info",function (){
                        $('#dlgUpdate').dialog('close');        // close the dialog
                        $('#mongoDbList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒","保存失败，请重试");
                }
            }
        });
    }
</script>
</div>
<div id="dlgUpdate" class="easyui-dialog" style="width:680px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlgUpdate-buttons'">
    <form id="fmm" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>参数信息</h3>
        <div style="margin-bottom:15px">
            <input type="text" style="display: none" name = "id">
            <input class="easyui-textbox" type="text" name="mongodbIp" label="数据库ip:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="mongodbDatabase" label="数据库名:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="collectionName" data-options="multiline:true"  label="collection:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input id="status" class="easyui-switchbutton" label="是否开启:" checked style="width:100px;height:30px">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="mongoUser" data-options="multiline:true"  label="用户名:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="mongoPwd" data-options="multiline:true"  label="密码:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="wxName" data-options="multiline:true"  label="卫星名称:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="zplValue" data-options="multiline:true"  label="中频:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="dplValue" data-options="multiline:true"  label="电平:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="tkplValue" data-options="multiline:true"  label="天空频率:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="mslValue" data-options="multiline:true"  label="码速率:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="buildTime" data-options="multiline:true"  label="采集时间:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="zzbValue" data-options="multiline:true"  label="载噪比:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="tzysName" data-options="multiline:true"  label="调制样式:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="bmType" data-options="multiline:true"  label="编码类型:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="mlName" data-options="multiline:true"  label="码率:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="mongoDbSubmit()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>