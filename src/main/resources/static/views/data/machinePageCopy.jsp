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
            getMachineDataList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMachine()">导入数据</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMachine()">删除</a>
        <input class="easyui-datebox" id="startTime" label="开始日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-datebox" id="endTime" label="结束日前:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">

    </div>
    <div id="getMachineDataList" data-options="region:'center',split:true"></div>
    <div id="dlg" class="easyui-dialog" style="width:520px; height: 260px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" action="/data/data/importMachine" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:15px">
                <input id="cbg" name="positionCode"  label="编码:" style="width:400px;">
            </div>
            <div style="margin-bottom:40px">
                <input id="file" name="file" class="easyui-filebox" label="文件:" labelPosition="left"  style="width:400px">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveMachine()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    <script type="text/javascript" th:inline="none">
        function getMachineDataList() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            var startTime = $('#startTime').datebox('getValue');
            var endTime = $('#endTime').datebox('getValue');
            $('#getMachineDataList').datagrid({
                url:'/data/data/getMachineDataList',//参数
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
                    {field:'positionCode',title:'位置编码',width:100,align:'center'},
                    {field:'wxName',title:'名称',width:80,align:'center'},
                    {field:'zplValue',title:'中频',width:80,align:'center'},
                    {field:'dplValue',title:'电平',width:80,align:'center'},
                    {field:'tkplValue',title:'天空频率',width:80,align:'center'},
                    {field:'xhType',title:'信号类型',width:80,align:'center'},
                    {field:'mslValue',title:'码速率',width:80,align:'center'},
                    {field:'zzbValue',title:'载噪比',width:80,align:'center'},
                    {field:'tzysName',title:'调制样式',width:80,align:'center'},
                    {field:'state',title:'状态',formatter:function(value,row,index)
                        {
                            if(row.state == 0){
                                return "未校对";
                            }else {
                                return "已校对";
                            }
                        },width:50,align:'center'},
                    {field:'cjTime',title:'采集时间',width:150,align:'center'},
                    {field:'proCode',title:'公文批次号',width:200,align:'center'}
                ]]
            });
        }

        //查询按钮
        $("#queryBt").textbox({onClickButton:function(){
                getMachineDataList();
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
        function addMachine(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','上报机器底数');
            $('#fm').form('clear');
        }

        function saveMachine(){
            $('#fm').form('submit',{
                url: '/data/data/importMachine',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#dlg').dialog('close');        // close the dialog
                            $('#getMachineDataList').datagrid('reload');    // reload the user data
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

        function deleteMachine(){
            var row = $('#getMachineDataList').datagrid('getSelected');
            if (row){
                $.messager.confirm('确认提醒','确定删除该资料?',function(r){
                    if (r){
                        $.post('/data/data/deleteMachine',{id:row.id},function(result){
                            if (result.success){
                                $('#getMachineDataList').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.alert("消息提醒",result.msg);
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