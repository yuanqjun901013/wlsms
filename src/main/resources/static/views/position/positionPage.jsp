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
            getPositionList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newPosition()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPosition()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyPosition()">删除</a>
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="getPositionList" data-options="region:'center',split:true"></div>
    <div id="dlg" class="easyui-dialog" style="width:400px; height: 300px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <h3>位置信息</h3>
            <div style="margin-bottom:15px">
                <input id="positionName" class="easyui-textbox" type="text" name="positionName" data-options="required:true" label="位置名称:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
            </div>
            <div style="margin-bottom:15px">
                <input id="positionCode" class="easyui-textbox" type="text" name="positionCode" data-options="required:true" label="位置编码:" labelPosition="left" style="width:230px;">
            </div>

        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savePosition()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
<script type="text/javascript" th:inline="none">
    function getPositionList() {//展示列表
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
                {field:'positionName',title:'位置名称',width:80,align:'center'},
                {field:'positionCode',title:'位置编码',width:80,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            getPositionList();
        }})

    //新增用户信息
    function newPosition(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增');
        $('#fm').form('clear');
    }

    //修改用户信息
    function editPosition(){
        var row = $('#getPositionList').datagrid('getSelected');
        if (row){
            $('#dlgUpdate').dialog('open').dialog('center').dialog('setTitle','修改');
            $('#fmm').form('load',row);
        }
    }

    function savePosition(){
        $('#fm').form('submit',{
            url: '/admin/position/savePosition',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dlg').dialog('close');        // close the dialog
                        $('#getPositionList').datagrid('reload');    // reload the user data
                    });
                }else
                    $.messager.alert("消息提醒",result.data);
                }
        });
    }


    function updatePosition(){
        $('#fmm').form('submit',{
            url: '/admin/position/updatePosition',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dlgUpdate').dialog('close');        // close the dialog
                        $('#getPositionList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒",result.data);
                }
            }
        });
    }

    function destroyPosition(){
        var row = $('#getPositionList').datagrid('getSelected');
        if (row){
            $.messager.confirm('Confirm','确定删除该位置信息?',function(r){
                if (r){
                    $.post('/admin/position/destroyPosition',{id:row.id},function(result){
                        if (result.success){
                            $('#getPositionList').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.alert("消息提醒",result.data);
                        }
                    },'json');
                }
            });
        }
    }

</script>
</div>
<div id="dlgUpdate" class="easyui-dialog" style="width:400px; height: 300px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlgUpdate-buttons'">
    <form id="fmm" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>用户信息</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="positionName" data-options="required:true" label="位置名称:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;

        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="positionCode" readonly="readonly" label="位置编码:" labelPosition="left" style="width:230px;">
        </div>
    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updatePosition()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>