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
            parametersList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateParam()">修改</a>
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="parametersList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">

    function parametersList() {//展示基础参数列表
        var queryBt = $('#queryBt').textbox('getValue');
        $('#parametersList').datagrid({
            url:'/admin/param/getParametersList',//参数
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
            nowrap:false,//自动换行
            toolbar:'#toolbar',
            columns:[[
                {field:'id',title:'参数编号',width:80,align:'center'},
                {field:'paramType',title:'参数类型',width:80,align:'center'},
                {field:'paramName',title:'参数名称',width:80,align:'center'},
                {field:'paramValue',title:'参数值',width:80,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            parametersList();
        }})

    function updateParam(){
        var row = $('#parametersList').datagrid('getSelected');
        if (row){
            $('#dlgUpdate').dialog('open').dialog('center').dialog('setTitle','基础参数修改');
            $('#fmm').form('load',row);
        }
    }

    function paramSubmit(){
        $('#fmm').form('submit',{
            url: '/admin/param/paramSubmit',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dlgUpdate').dialog('close');        // close the dialog
                        $('#parametersList').datagrid('reload');    // reload the user data
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
        <h3>参数信息</h3>
        <div style="margin-bottom:15px">
            <input type="text" style="display: none" name = "id">
            <input class="easyui-textbox" type="text" name="paramType" readonly label="参数类型:" labelPosition="left" style="width:100%;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="paramName" label="参数名称:" labelPosition="left" style="width:100%;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="paramValue" data-options="multiline:true"  label="参数值(浮动范围内):" labelPosition="left" style="width:100%;">&nbsp;&nbsp;
        </div>

    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="paramSubmit()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>