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
            operationList();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="feedbackOperation()">反馈</a>
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="operationList" data-options="region:'center',split:true"></div>

<script type="text/javascript" th:inline="none">
    function operationList() {//展示最新的动态消息列表
        var queryBt = $('#queryBt').textbox('getValue');
      $('#operationList').datagrid({
            url:'/admin/message/getOperationList',//消息
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
                {field:'id',title:'编号',width:30,align:'center'},
                {field:'userNo',title:'工号',width:60,align:'center'},
                {field:'buildTime',title:'发生时间',width:150,align:'center'},
                {field:'title',title:'任务标题',width:80,align:'center'},
                {field:'content',title:'任务内容',width:150,align:'center'},
                {field:'feedbackContent',title:'反馈内容',width:150,align:'center'},
                {field:'state',title:'状态',formatter:function(value,row,index)
                    {
                        if(row.state == 1){
                            return "未反馈";
                        }else {
                            return "已反馈";
                        }
                    },width:50,align:'center'},
                {field:'feedbackTime',title:'任务反馈时间',width:150,align:'center'},
                {field:'feedbackUser',title:'反馈人工号',width:60,align:'center'},
            ]]
        });
    }
    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            operationList();
        }})
    function feedbackOperation(){

    }
</script>
</div>
</body>
</html>