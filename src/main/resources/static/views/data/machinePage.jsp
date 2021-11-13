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
    <script type="text/javascript" src="../../datagrid.export.js"></script>
    <SCRIPT th:inline="javascript">
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            getMachineList();
        })

        //定时刷新页面
        setInterval(function() {
            // show();//暂不用
            getMachineList();
        }, 9000);
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMachine()">导入</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMachine()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="getClear()">清空</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="getExport()">导出</a>
        <input class="easyui-datebox" id="startTime" label="开始日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-datebox" id="endTime" label="结束日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="getMachineList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
    <div id="dlg" class="easyui-dialog" style="width:520px; height: 260px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" action="/data/buildNew/importMachine" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:15px">
                <input id="cbg" name="positionCode"  label="地址:" style="width:400px;">
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
        function getMachineList() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            var startTime = $('#startTime').datebox('getValue');
            var endTime = $('#endTime').datebox('getValue');
            $('#getMachineList').datagrid({
                url:'/data/buildNew/getMachineList',//参数
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
                    {field:'buildType',title:'生成方式',width:100,align:'center'},
                    {field:'positionName',title:'地址',width:100,align:'center'},
                    {field:'wxName',title:'卫星名称',width:80,align:'center'},
                    {field:'carPol',title:'极化',width:80,align:'center'},
                    // {field:'zplValue',title:'中频',width:80,align:'center'},
                    {field:'tkplValue',title:'天空频率',width:80,align:'center'},
                    {field:'mslValue',title:'码速率',width:80,align:'center'},
                    {field:'dplValue',title:'电平',width:80,align:'center'},
                    {field:'zzbValue',title:'载噪比',width:80,align:'center'},
                    {field:'xhType',title:'信号类型',width:80,align:'center'},
                    {field:'muladdr',title:'多址方式',width:80,align:'center'},
                    {field:'others',title:'其他',width:80,align:'center'},
                    {field:'tzysName',title:'调制样式',width:80,align:'center'},
                    {field:'bmType',title:'编码类型',width:80,align:'center'},
                    {field:'mlName',title:'码率',width:80,align:'center'},
                    {field:'exmlen',title:'分组长度',width:80,align:'center'},
                    {field:'fcycle',title:'突发周期',width:80,align:'center'},
                    {field:'flen',title:'帧长',width:80,align:'center'},
                    {field:'cf',title:'差分',width:80,align:'center'},
                    {field:'rm',title:'扰码',width:80,align:'center'},
                    {field:'sindex',title:'索引号',width:80,align:'center'},
                    {field:'userProperties',title:'用户属性',width:80,align:'center'},
                    {field:'appearTime',title:'发现时间',width:150,align:'center'},
                    {field:'buildTime',title:'登记时间',width:150,align:'center'}
                ]]
            });

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
        function getExport() {
            $('#getMachineList').datagrid('toExcel', '机器数据.xls');
        }

        function getClear(){
            var queryBt = $('#queryBt').textbox('getValue');
            var startTime = $('#startTime').datebox('getValue');
            var endTime = $('#endTime').datebox('getValue');
            $('#getMachineList').datagrid({
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
                    {field:'buildType',title:'生成方式',width:100,align:'center'},
                    {field:'positionName',title:'地址',width:100,align:'center'},
                    {field:'wxName',title:'卫星名称',width:80,align:'center'},
                    {field:'carPol',title:'极化',width:80,align:'center'},
                    // {field:'zplValue',title:'中频',width:80,align:'center'},
                    {field:'tkplValue',title:'天空频率',width:80,align:'center'},
                    {field:'mslValue',title:'码速率',width:80,align:'center'},
                    {field:'dplValue',title:'电平',width:80,align:'center'},
                    {field:'zzbValue',title:'载噪比',width:80,align:'center'},
                    {field:'xhType',title:'信号类型',width:80,align:'center'},
                    {field:'muladdr',title:'多址方式',width:80,align:'center'},
                    {field:'others',title:'其他',width:80,align:'center'},
                    {field:'tzysName',title:'调制样式',width:80,align:'center'},
                    {field:'bmType',title:'编码类型',width:80,align:'center'},
                    {field:'mlName',title:'码率',width:80,align:'center'},
                    {field:'exmlen',title:'分组长度',width:80,align:'center'},
                    {field:'fcycle',title:'突发周期',width:80,align:'center'},
                    {field:'flen',title:'帧长',width:80,align:'center'},
                    {field:'cf',title:'差分',width:80,align:'center'},
                    {field:'rm',title:'扰码',width:80,align:'center'},
                    {field:'sindex',title:'索引号',width:80,align:'center'},
                    {field:'userProperties',title:'用户属性',width:80,align:'center'},
                    {field:'appearTime',title:'发现时间',width:150,align:'center'},
                    {field:'buildTime',title:'登记时间',width:150,align:'center'}
                ]]
            });
        }

        //查询按钮
        $("#queryBt").textbox({onClickButton:function(){
                getMachineList();
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
                url: '/data/buildNew/importMachine',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#dlg').dialog('close');        // close the dialog
                            $('#getMachineList').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }


        function deleteMachine(){
            // var row = $('#getMachineList').datagrid('getSelected');
            var row = $('#getMachineList').datagrid('getChecked');
            if (row){
                var ids = "";
                for (var i=0;i < row.length;i++)
                {
                    ids += row[i].id + ",";
                }
                $.messager.confirm('确认提醒','确定删除这些数据?',function(r){
                    if (r){
                        $.post('/data/buildNew/deleteMachine',{ids:ids},function(result){
                            if (result.success){
                                $('#getMachineList').datagrid('reload');    // reload the user data
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