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
            getTaskList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <div id="toolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="getClear()">清空</a>
            <input id="taskType" name = "taskType" label="任务类型:" style="width:200px;">
            <input class="easyui-datebox" id="startTime" label="开始日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
            <input class="easyui-datebox" id="endTime" label="结束日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
            <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'right',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
        </div>
        <div id="getTaskList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>


    <script type="text/javascript" th:inline="none">
        function getTaskList() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            var startTime = $('#startTime').datebox('getValue');
            var endTime = $('#endTime').datebox('getValue');
            var taskType = $('#taskType').datebox('getValue');
            $('#getTaskList').datagrid({
                url:'/task/getEndTask',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "queryBt":queryBt,
                    "startTime":startTime,
                    "endTime":endTime,
                    "taskType":taskType
                },
                fitColumns:false,
                striped:true,
                pagination:true,
                rownumbers:false,
                remoteFilter: true,
                clientPaging: false,
                nowrap:false,//自动换行
                toolbar:'#toolbar',
                singleSelect:true,
                checkOnSelect:false,
                selectOnCheck:false,
                columns:[[
                    // {field:'ck',checkbox:true,align:'center'},
                    {field:'id',title:'任务编号',width:80,align:'center'},
                    {field:'title',title:'任务标题',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='getDetail("+row.id+")'>"+row.title+"</a>";
                        },
                        width:200,align:'center'},
                    {field:'userName',title:'任务发起人',width:100,align:'center'},
                    {field:'buildTime',title:'下发时间',width:150,align:'center'},
                    {field:'receiverUserName',title:'任务认领人',width:100,align:'center'},
                    {field:'receiverTime',title:'认领时间',width:100,align:'center'},
                    {field:'feedbackUserName',title:'任务处理及反馈人',width:150,align:'center'},
                    {field:'feedbackTime',title:'任务处理及反馈时间',width:150,align:'center'},
                    {field:'positionName',title:'侦测位置',width:100,align:'center'},
                    {field:'taskTypeName',title:'任务类型',width:100,align:'center'},
                    {field:'stateName',title:'任务状态',width:100,align:'center'},
                    {field:'todo',title:'操作',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' style='text-decoration:none;' onclick='getDetail("+row.id+")'>详情</a>&nbsp;&nbsp;&nbsp;";
                        },
                        width:150,align:'center'}
                ]]
            });
        }

        $('#taskType').combobox({
            url:'/task/taskTypeList',
            valueField:'taskType',
            textField:'taskTypeName'
        });

        $('#state').combobox({
            url:'/task/taskStateList',
            valueField:'state',
            textField:'stateName'
        });

        //日前格式化1
        function dateFormatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);

        }
        //日前格式化2
        function dateParser(s){
            var reg=/[\u4e00-\u9fa5]/  //利用正则表达式分隔
            var ss = (s.split("-"));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            } else {
                return new Date();
            }
        }



        //查询按钮
        $("#queryBt").textbox({onClickButton:function(){
                getTaskList();
            }})

        function addTask(){
            $('#taskAdd').dialog('open').dialog('center').dialog('setTitle','下发任务');
            $('#fmAdd').form('clear');
            $('#typeAdd').combobox({
                url:'/task/taskTypeList',
                valueField:'taskType',
                textField:'taskTypeName'
            });
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

        function saveTask(){
            $('#fmAdd').form('submit',{
                url: '/task/saveTask',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#taskAdd').dialog('close');        // close the dialog
                            $('#getTaskList').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }


        //详情
        function getDetail(id){
            $('#taskDetail').dialog('open').dialog('center').dialog('setTitle','任务详情');
            $('#fmDetail').form('clear');
            document.getElementById("id").style.display="none";//隐藏
            $("#title").textbox('readonly',true);
            $("#content").textbox('readonly',true);
            document.getElementById("saveButtons").style.display="none";//隐藏

            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/task/getTaskDetail',//获取任务详情
                data:{
                    "id":id
                },
                success: function(result){
                    if(result.success){
                        //赋值
                        $("#title").textbox('setValue', result.data.title);
                        $("#content").textbox('setValue', result.data.content);
                        $("#stateName").textbox('setValue', result.data.stateName);
                        $("#taskTypeName").textbox('setValue', result.data.taskTypeName)
                        $("#positionName").textbox('setValue', result.data.positionName);
                        $("#id").textbox('setValue', result.data.id);
                        $("#userName").textbox('setValue', result.data.userName);
                        $("#buildTime").textbox('setValue', result.data.buildTime);
                        $("#receiverUserName").textbox('setValue', result.data.receiverUserName);
                        $("#receiverTime").textbox('setValue', result.data.receiverTime);
                        $("#feedbackUserName").textbox('setValue', result.data.feedbackUserName);
                        $("#feedbackTime").textbox('setValue', result.data.feedbackTime);
                        $("#feedbackContent").textbox('setValue', result.data.feedbackContent);
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }


        //任务编辑
        function editTask(id){
            $('#taskDetail').dialog('open').dialog('center').dialog('setTitle','任务编辑');
            $('#fmDetail').form('clear');
            document.getElementById("id").style.display="none";//隐藏
            document.getElementById("saveButtons").style.display="";//展示
            $("#title").textbox('readonly',false);
            $("#content").textbox('readonly',false);
            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/task/getTaskDetail',//获取任务详情
                data:{
                    "id":id
                },
                success: function(result){
                    if(result.success){
                        //赋值
                        $("#title").textbox('setValue', result.data.title);
                        $("#content").textbox('setValue', result.data.content);
                        $("#stateName").textbox('setValue', result.data.stateName);
                        $("#taskTypeName").textbox('setValue', result.data.taskTypeName)
                        $("#positionName").textbox('setValue', result.data.positionName);
                        $("#id").textbox('setValue', result.data.id);
                        $("#userName").textbox('setValue', result.data.userName);
                        $("#buildTime").textbox('setValue', result.data.buildTime);
                        $("#receiverUserName").textbox('setValue', result.data.receiverUserName);
                        $("#receiverTime").textbox('setValue', result.data.receiverTime);
                        $("#feedbackUserName").textbox('setValue', result.data.feedbackUserName);
                        $("#feedbackTime").textbox('setValue', result.data.feedbackTime);
                        $("#feedbackContent").textbox('setValue', result.data.feedbackContent);
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }

        function updateTask(){
            var id = $("#id").textbox('getValue');
            var title = $("#title").textbox('getValue');
            var content = $("#content").textbox('getValue');
            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/task/updateTask',//修改任务详情
                data:{
                    "id":id,
                    "title":title,
                    "content":content
                },
                success: function(result){
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#taskDetail').dialog('close');        // close the dialog
                            $('#getTaskList').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });

        }

        function receiverTask(id){
            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/task/receiverTask',//认领任务
                data:{
                    "id":id,
                },
                success: function(result){
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#getTaskList').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }

        //删除
        function deleteTask(id){
            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/task/deleteTask',//删除任务
                data:{
                    "id":id,
                },
                success: function(result){
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#getTaskList').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }


        //清除
        function getClear(){
            var queryBt = $('#queryBt').textbox('getValue');
            var startTime = $('#startTime').datebox('getValue');
            var endTime = $('#endTime').datebox('getValue');
            $('#getTaskList').datagrid({
                url:'/batch/common/getClear',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "queryBt":queryBt,
                    "startTime":startTime,
                    "endTime":endTime
                },
                fitColumns:false,
                striped:true,
                pagination:true,
                rownumbers:false,
                remoteFilter: true,
                clientPaging: false,
                nowrap:false,//自动换行
                toolbar:'#toolbar',
                singleSelect:true,
                checkOnSelect:false,
                selectOnCheck:false,
                columns:[[
                    // {field:'ck',checkbox:true,align:'center'},
                    {field:'id',title:'任务编号',width:80,align:'center'},
                    {field:'title',title:'任务标题',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' onclick='getDetail("+row.id+")'>"+row.title+"</a>";
                        },
                        width:200,align:'center'},
                    {field:'userName',title:'任务发起人',width:100,align:'center'},
                    {field:'buildTime',title:'下发时间',width:150,align:'center'},
                    {field:'receiverUserName',title:'任务认领人',width:100,align:'center'},
                    {field:'receiverTime',title:'认领时间',width:100,align:'center'},
                    {field:'feedbackUserName',title:'任务处理及反馈人',width:150,align:'center'},
                    {field:'feedbackTime',title:'任务处理及反馈时间',width:150,align:'center'},
                    {field:'positionName',title:'侦测位置',width:100,align:'center'},
                    {field:'taskTypeName',title:'任务类型',width:100,align:'center'},
                    {field:'stateName',title:'任务状态',width:100,align:'center'},
                    {field:'todo',title:'操作',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' onclick='getDetail("+row.id+")'>详情</a>";
                        },
                        width:150,align:'center'}
                ]]
            });
        }

    </script>
</div>
<div id="taskAdd" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#add-buttons'">
    <form id="fmAdd" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>下发任务</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" data-options="required:true" name="title" label="任务标题:" labelPosition="left" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" data-options="required:true" name="content" multiline="true" label="任务内容:" labelPosition="left" style="width:400px;height: 60px">
        </div>
        <div style="margin-bottom:15px">
            <input id="typeAdd" name="taskType" data-options="required:true" label="任务类型:" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input id="cbgAdd" name="positionCode" data-options="required:true" label="地址:" style="width:400px;">
        </div>
    </form>
</div>
<div id="add-buttons">
    <a href="javascript:void(0)" id="addButtons" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveTask()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#taskAdd').dialog('close')" style="width:90px">取消</a>
</div>

<div id="taskDetail" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#detail-buttons'">
    <form id="fmDetail" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>任务详情</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="id" readonly="readonly" label="任务编号:" labelPosition="left">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="title" readonly="readonly" label="任务标题:" labelPosition="left" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="content" multiline="true"  readonly="readonly" label="任务内容:" labelPosition="left" style="width:400px;height: 60px">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="stateName" readonly="readonly" label="任务状态:" labelPosition="left" style="width:200px;">
            <input class="easyui-textbox" id="taskTypeName" readonly="readonly" label="任务类型:" labelPosition="left" style="width:200px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="positionName" readonly="readonly" label="地址:" labelPosition="left" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="userName" readonly="readonly" label="发起人:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" id="buildTime" readonly="readonly" label="下发时间:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="receiverUserName" readonly="readonly" label="认领人:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" id="receiverTime" readonly="readonly" label="认领时间:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="feedbackUserName" readonly="readonly" label="反馈人:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" id="feedbackTime" readonly="readonly" label="反馈时间:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" id="feedbackContent" multiline="true"  readonly="readonly" label="反馈内容:" labelPosition="left" style="width:400px;height: 60px">
        </div>
    </form>
</div>
<div id="detail-buttons">
    <a href="javascript:void(0)" id="saveButtons" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateTask()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#taskDetail').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>