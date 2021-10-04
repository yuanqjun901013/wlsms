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
            getManualList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addManual()">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBatchManual()">批量导入</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editValue()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteManual()">删除</a>
        <input class="easyui-datebox" id="startTime" label="开始日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-datebox" id="endTime" label="结束日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="getManualList" data-options="region:'center',split:true"></div>
    <div id="dlg" class="easyui-dialog" style="width:520px; height: 260px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" action="/data/macAuto/importManual" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:15px">
                <input id="cbg" name="positionCode"  label="地址:" style="width:400px;">
            </div>
            <div style="margin-bottom:40px">
                <input id="file" name="file" class="easyui-filebox" label="文件:" labelPosition="left"  style="width:400px">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBatchManual()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>

<script type="text/javascript" th:inline="none">
    function getManualList() {//展示列表
        var queryBt = $('#queryBt').textbox('getValue');
        var startTime = $('#startTime').datebox('getValue');
        var endTime = $('#endTime').datebox('getValue');
      $('#getManualList').datagrid({
            url:'/data/macAuto/getManualList',//参数
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
            nowrap:false,//自动换行
            toolbar:'#toolbar',
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            columns:[[
                {field:'ck',checkbox:true,align:'center'},
                {field:'id',title:'编号',width:80,align:'center'},
                {field:'positionName',title:'地址',width:80,align:'center'},
                {field:'sxzfqName',title:'卫星',width:80,align:'center'},
                {field:'carPol',title:'极化',width:80,align:'center'},
                // {field:'sxplValue',title:'上行频率',width:80,align:'center'},
                {field:'bpqplValue',title:'变频器频率',width:80,align:'center'},
                {field:'zplValue',title:'中频',width:80,align:'center'},
                {field:'xxplValue',title:'下行频率',width:80,align:'center'},
                {field:'systemName',title:'信号类型',width:80,align:'center'},
                {field:'tzslValue',title:'调制速率',width:80,align:'center'},
                {field:'xxslValue',title:'信息速率',width:80,align:'center'},
                {field:'tzfsName',title:'调制方式',width:80,align:'center'},
                {field:'xdbmCode',title:'信道编码',width:80,align:'center'},
                {field:'mlName',title:'码率',width:80,align:'center'},
                {field:'xzbValue',title:'信噪比',width:80,align:'center'},
                {field:'flen',title:'帧长',width:80,align:'center'},
                {field:'remark',title:'备注',width:80,align:'center'},
                {field:'buildDate',title:'登记日期',width:150,align:'center'}
            ]]
        });
    }

    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
            getManualList();
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

    function addManual(){
        $('#dataAdd').dialog('open').dialog('center').dialog('setTitle','新增单个人工底数');
        $('#fmAdd').form('clear');
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

    function saveManual(){
        $('#fmAdd').form('submit',{
            url: '/data/macAuto/saveManual',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dataAdd').dialog('close');        // close the dialog
                        $('#getManualList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒",result.msg);
                }
            }
        });
    }

    //添加文件信息
    function addBatchManual(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','上报人工底数');
        $('#fm').form('clear');
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
                {field:'positionName',title:'地址',width:100,sortable:true},
                {field:'positionCode',title:'标识码',width:80,sortable:true}
            ]]
        });
    }

    function saveBatchManual(){
        $('#fm').form('submit',{
            url: '/data/macAuto/importManual',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dlg').dialog('close');        // close the dialog
                        $('#getManualList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒",result.msg);
                }
            }
        });
    }

    function editValue(){
        var row = $('#getManualList').datagrid('getSelected');
        if (row){
            $('#dataUpdate').dialog('open').dialog('center').dialog('setTitle','修改');
            $('#fmm').form('load',row);
        }
    }

    function updateManual(){
        $('#fmm').form('submit',{
            url: '/data/macAuto/updateManual',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if(result.success){
                    $.messager.alert("消息提醒", result.data, "info",function (){
                        $('#dataUpdate').dialog('close');        // close the dialog
                        $('#getManualList').datagrid('reload');    // reload the user data
                    });
                }else {
                    $.messager.alert("消息提醒",result.msg);
                }
            }
        });
    }

    function deleteManual(){
        // var row = $('#getManualList').datagrid('getSelected');
        var row = $('#getManualList').datagrid('getChecked');
        if (row){
            var ids = "";
            for (var i=0;i < row.length;i++)
            {
                ids += row[i].id + ",";
            }
            $.messager.confirm('确认提醒','确定删除此条数据?',function(r){
                if (r){
                    $.post('/data/macAuto/deleteManual',{ids:ids},function(result){
                        if (result.success){
                            $('#getManualList').datagrid('reload');    // reload the user data
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
<div id="dataAdd" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlgData-buttons'">
    <form id="fmAdd" method="post" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
        <h3>新增底数信息</h3>
        <div style="margin-bottom:15px">
            <input id="cbgAdd" name="positionCode"  label="地址:" style="width:400px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="sxzfqName" data-options="required:true" label="卫星:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="carPol" data-options="required:true" label="极化:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="bpqplValue" data-options="required:true" label="变频器频率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="zplValue" data-options="required:true" label="中频:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="xxplValue" data-options="required:true" label="下行频率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="systemName" data-options="required:true" label="信号类型:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="tzslValue" data-options="required:true" label="调制速率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="xxslValue" data-options="required:true" label="信息速率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="tzfsName" data-options="required:true" label="调制方式:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="xdbmCode" data-options="required:true" label="信道编码:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="mlName" data-options="required:true" label="码率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="xzbValue" data-options="required:true" label="信噪比:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="flen" data-options="required:true" label="码率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="remark" data-options="required:true" label="帧长:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-datebox" id="buildDate" name ="buildDate" label="登记日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser,required:true" style="width:230px;">
        </div>
    </form>
</div>
<div id="dlgData-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveManual()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dataAdd').dialog('close')" style="width:90px">取消</a>
</div>
<div id="dataUpdate" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlgUpdate-buttons'">
    <form id="fmm" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>编辑底数信息</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="sxzfqName" data-options="required:true" label="卫星:" labelPosition="left" style="width:200px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="buildDate" readonly="readonly" label="登记时间:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="systemName" data-options="required:true" label="信号类型:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="positionName" readonly="readonly" label="地址:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="carPol" data-options="required:true" label="极化:" labelPosition="left" style="width:200px;">
            <input class="easyui-textbox" type="text" name="bpqplValue" data-options="required:true" label="变频器频率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="zplValue" data-options="required:true" label="中频:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="xxplValue" data-options="required:true" label="下行频率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="tzslValue" data-options="required:true" label="调制速率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="xxslValue" data-options="required:true" label="信息速率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="tzfsName" data-options="required:true" label="调制方式:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="xzbValue" data-options="required:true" label="信噪比:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="xdbmCode" data-options="required:true" label="信道编码:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="mlName" data-options="required:true" label="码率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="flen" data-options="required:true" label="帧长:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="remark" data-options="required:true" label="备注:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input type="text" style="display: none" name = "id">
        </div>
    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateManual()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dataUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>