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
            getDataList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBatch()">数据校对汇总</a>
        <!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMachine()">删除</a>-->
        <input class="easyui-datebox" id="startTime" label="开始日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:250px;">
        <input class="easyui-datebox" id="endTime" label="结束日前:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:250px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="getDataList" data-options="region:'center',split:true"></div>
    <div id="dlg" class="easyui-dialog" style="width:650px; height: 300px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <h3>数据校对</h3>
            <div style="margin-bottom:15px">
                <input id="cbgManual" name="proCodeManual"  label="人工底数公文号:" style="width:400px;">
            </div>
            <div style="margin-bottom:15px">
                <input id="cbgMachine" name="proCodeMachine"  label="机器底数公文号:" style="width:400px;">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBatch()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
<script type="text/javascript" th:inline="none">
    function getDataList() {//展示列表
        var queryBt = $('#queryBt').textbox('getValue');
        var startTime = $('#startTime').datebox('getValue');
        var endTime = $('#endTime').datebox('getValue');
        $('#getDataList').datagrid({
            url:'/data/data/getDataList',//参数
            method: 'post',
            //携带参数
            queryParams: {
                "queryBt":queryBt,
                "startTime":startTime,
                "endTime":endTime
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
                {field:'cjTime',title:'采集时间',width:80,align:'center'},
                {field:'wzlValue',title:'误帧率',width:80,align:'center'},
                {field:'createTime',title:'上报时间',width:150,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            getDataList();
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

    function addBatch(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增授权');
        $('#fm').form('clear');
    }

    $('#cbgManual').combogrid({
        delay: 250,
        mode: 'remote',
        url: '/data/data/getManualDataList',
        idField: 'proCode',
        textField: 'proCodeManual',
        striped:true,
        multiple: true,
        fitColumns: true,
        nowrap:false,//自动换行
        columns: [[
            {field:'proCode',title:'公文号',width:200,sortable:true},
            {field:'cjTime',title:'上报时间',width:120,sortable:true}
        ]]
    });

    $('#cbgMachine').combogrid({
        delay: 250,
        mode: 'remote',
        url: '/data/data/getMachineDataList',
        idField: 'proCode',
        textField: 'proCodeMachine',
        striped:true,
        fitColumns: true,
        nowrap:false,//自动换行
        columns: [[
            {field:'proCode',title:'公文号',width:200,sortable:true},
            {field:'cjTime',title:'上报时间',width:120,sortable:true}
        ]]
    });

    function saveBatch(){
        $('#fm').form('submit',{
            url: '/data/data/saveBatch',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", "校对成功!", "info",function (){
                        $('#dlg').dialog('close');        // close the dialog
                        $('#roleAuthList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒","校对失败，请重试");
                }
            }
        });
    }
</script>
</div>
</body>
</html>