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
            getManualDataList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addManual()">添加数据</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteManual()">删除</a>
        <input class="easyui-datebox" id="startTime" label="开始日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-datebox" id="endTime" label="结束日前:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="getManualDataList" data-options="region:'center',split:true"></div>
    <div id="dlg" class="easyui-dialog" style="width:520px; height: 260px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" action="/data/data/importManual" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:15px">
                <input id="cbg" name="positionCode"  label="编码:" style="width:400px;">
            </div>
            <div style="margin-bottom:40px">
                <input id="file" name="file" class="easyui-filebox" label="文件:" labelPosition="left"  style="width:400px">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveManual()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
<script type="text/javascript" th:inline="none">
    function getManualDataList() {//展示列表
        var queryBt = $('#queryBt').textbox('getValue');
        var startTime = $('#startTime').datebox('getValue');
        var endTime = $('#endTime').datebox('getValue');
      $('#getManualDataList').datagrid({
            url:'/data/data/getManualDataList',//参数
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
            singleSelect:true,
            remoteFilter: true,
            clientPaging: false,
            nowrap:false,//自动换行
            toolbar:'#toolbar',
            columns:[[
                {field:'id',title:'编号',width:80,align:'center'},
                {field:'positionCode',title:'位置编码',width:80,align:'center'},
                {field:'sxzfqName',title:'上行转发器',width:80,align:'center'},
                {field:'sxplValue',title:'上行频率',width:80,align:'center'},
                {field:'bpqplValue',title:'变频器频率',width:80,align:'center'},
                {field:'zplValue',title:'中频',width:80,align:'center'},
                {field:'xxplValue',title:'下行频率',width:80,align:'center'},
                {field:'systemName',title:'系统',width:80,align:'center'},
                {field:'tzslValue',title:'调制速率',width:80,align:'center'},
                {field:'xxslValue',title:'信息速率',width:80,align:'center'},
                {field:'tzfsName',title:'调制方式',width:80,align:'center'},
                {field:'xdbmCode',title:'信道编码',width:80,align:'center'},
                {field:'xzbValue',title:'信噪比',width:80,align:'center'},
                {field:'cjTime',title:'采集时间',width:150,align:'center'},
                {field:'wzlValue',title:'误帧率',width:80,align:'center'},
                {field:'state',title:'状态',formatter:function(value,row,index)
                    {
                        if(row.state == 0){
                            return "未校对";
                        }else {
                            return "已校对";
                        }
                    },width:50,align:'center'},
                {field:'proCode',title:'批次公文号',width:200,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            getManualDataList();
        }})

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

    //添加文件信息
    function addManual(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','上报人工底数');
        $('#fm').form('clear');
    }

    function saveManual(){
        $('#fm').form('submit',{
            url: '/data/data/importManual',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dlg').dialog('close');        // close the dialog
                        $('#getManualDataList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒",result.msg);
                }
            }
        });
    }

    $('#cbg').combogrid({
        delay: 250,
        mode: 'remote',
        url: '/admin/position/getPositionArr',
        idField: 'positionCode',
        textField: 'positionName',
        striped:true,
        multiple: false,
        fitColumns: true,
        columns: [[
            {field:'positionName',title:'位置名称',width:100,sortable:true},
            {field:'positionCode',title:'位置编码',width:80,sortable:true}
        ]]
    });

    function deleteManual(){
        var row = $('#getManualDataList').datagrid('getSelected');
        if (row){
            $.messager.confirm('Confirm','确定删除该资料?',function(r){
                if (r){
                    $.post('/data/data/deleteManual',{id:row.id},function(result){
                        if (result.success){
                            $('#getManualDataList').datagrid('reload');    // reload the user data
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
</body>
</html>