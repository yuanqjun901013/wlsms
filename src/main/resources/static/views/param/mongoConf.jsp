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
    <div data-options="region:'center'">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateMongoDb()">修改</a>
    </div>
    <div id="mongoDbList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
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
                // {field:'mongodbIp',title:'mongo数据IP地址',width:80,align:'center'},
                // {field:'mongodbDatabase',title:'数据库名称',width:80,align:'center'},
                // {field:'mongoUser',title:'用户名',width:80,align:'center'},
                {field:'collectionName',title:'集合名',width:80,align:'center'},
                {field:'status',title:'状态',
                    formatter:function(value,row,index)
                    {
                        if(value == 'on'){
                            return "<label>开</label>";
                        }else{
                            return "<label>关</label>";
                        }
                    },
                    width:80,align:'center'}
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
                    $.messager.alert("消息提醒",result.msg);
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
           <!--
            <input class="easyui-textbox" type="text" name="mongodbIp" label="数据库ip:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="mongodbDatabase" label="数据库名:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            -->
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="collectionName" data-options="multiline:true"  label="collection:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input id="status" name = "status" class="easyui-switchbutton" label="状态:" checked style="width:100px;height:30px">
        </div>
        <!--
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="mongoUser" data-options="multiline:true"  label="用户名:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="mongoPwd" data-options="multiline:true"  label="密码:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        -->
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="wxName" data-options="multiline:true"  label="卫星名称:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="xhType" data-options="multiline:true"  label="信号类型:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="dplValue" data-options="multiline:true"  label="电平:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="tkplValue" data-options="multiline:true"  label="天空频率:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="mslValue" data-options="multiline:true"  label="码速率:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="appearTime" data-options="multiline:true"  label="发现时间:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="zzbValue" data-options="multiline:true"  label="载噪比:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="tzysName" data-options="multiline:true"  label="调制样式:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="bmType" data-options="multiline:true"  label="编码类型:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="mlName" data-options="multiline:true"  label="码率:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="carPol" data-options="multiline:true"  label="极化方式:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="muladdr" data-options="multiline:true"  label="多址方式:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="others" data-options="multiline:true"  label="其他:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="exmlen" data-options="multiline:true"  label="分组长度:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="fcycle" data-options="multiline:true"  label="突发周期:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="flen" data-options="multiline:true"  label="帧长:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="cf" data-options="multiline:true"  label="差分:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="rm" data-options="multiline:true"  label="扰码:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="sindex" data-options="multiline:true"  label="索引号:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="userProperties" data-options="multiline:true"  label="用户属性:" labelPosition="left" style="width:250px;">&nbsp;&nbsp;
        </div>
    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="mongoDbSubmit()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>