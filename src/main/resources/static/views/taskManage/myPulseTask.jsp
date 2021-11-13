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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addTask()">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editTask()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteTask()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="getClear()">清空</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="getExport()">导出</a>
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
            $('#getTaskList').datagrid({
                url:'/taskManage/taskView/getMyPulseTask',//参数
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
                rownumbers:true,
                remoteFilter: true,
                clientPaging: false,
                nowrap:true,//自动换行
                toolbar:'#toolbar',
                singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
                columns:[[
                    {field:'id',title:'编号',width:20,align:'center'},
                    {field:'title',title:'文件名称',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' onclick='getDetail()'>"+row.title+"</a>";
                        },
                        width:100,align:'center'},
                    {field:'userName',title:'任务发起人',width:60,align:'center'},
                    {field:'buildTime',title:'下发时间',width:150,align:'center'},
                    {field:'receiverUserName',title:'任务认领人',width:60,align:'center'},
                    {field:'receiverTime',title:'认领时间',width:150,align:'center'},
                    {field:'feedbackUserName',title:'任务处理及反馈人',width:60,align:'center'},
                    {field:'feedbackTime',title:'任务处理及反馈时间',width:150,align:'center'},
                    {field:'positionName',title:'侦测位置',width:30,align:'center'},
                    {field:'taskType',title:'任务类型',width:30,align:'center'},
                    {field:'state',title:'任务状态',width:30,align:'center'}
                ]]
            });
        }

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
    </script>
</div>
</body>
</html>