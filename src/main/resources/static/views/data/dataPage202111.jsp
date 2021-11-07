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
    <script type="text/javascript" src="../../echarts.min.js"></script>
    <SCRIPT th:inline="javascript">
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            getReload();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true" style="width:30%" align="middle">
        <form id="fm" method="post" enctype="multipart/form-data">
        <h3>数据校对</h3>
        <div style="margin-bottom:15px">
            <input id="cbgManual" name="buildDate" label="人工登记日期:" style="width:280px;">
        </div>
        <div style="margin-bottom:15px">
            <input id="cbgMachine" name="buildTime" label="机器数据时间:" style="width:280px;">
        </div>
        </form>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="openAuto()" style="width:90px">开始</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearFrom()" style="width:90px">重置</a>
        </div>
     </div>
    <div data-options="region:'center'">
        <div id="getAutoDataList" data-options="region:'center',split:true"></div>
    </div>
    <script type="text/javascript" th:inline="none">
        function getReload(){
            $('#cbgManual').combogrid({
                delay: 250,
                mode: 'remote',
                url: '/data/macAuto/queryManualByDate',
                idField: 'buildDate',
                textField: 'buildDate',
                striped:true,
                labelPosition:"top",
                fitColumns: true,
                nowrap:false,//自动换行
                columns: [[
                    {field:'buildDate',title:'人工登记日期',width:180,sortable:true},
                    {field:'count',title:'数据量',width:60,sortable:true}

                ]]
            });

            $('#cbgMachine').combogrid({
                delay: 250,
                mode: 'remote',
                url: '/data/macAuto/queryMachineByDate',
                idField: 'buildTime',
                textField: 'buildTime',
                labelPosition:"top",
                striped:true,
                fitColumns: true,
                nowrap:false,//自动换行
                columns: [[
                    {field:'buildTime',title:'机器上报时间',width:180,sortable:true},
                    {field:'count',title:'数据量',width:60,sortable:true}
                ]]
            });
        }

        function clearFrom(){
            $('#fm').form('clear');
        }

        function openAuto(){
            //比对开始
            $('#fm').form('submit',{
                url: '/data/macAuto/openAuto',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            getAutoDataList();    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }

        function getAutoDataList() {//展示列表
            var cbgManual = $('#cbgManual').combogrid('getValue');
            var cbgMachine = $('#cbgMachine').combogrid('getValue');
            $('#getAutoDataList').datagrid({
                url:'/data/macAuto/getAutoDataList',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "buildDate": cbgManual,
                    "buildTime": cbgMachine
                },
                fitColumns:false,
                striped:true,
                pagination:true,
                rownumbers:true,
                singleSelect:true,
                remoteFilter: true,
                clientPaging: false,
                nowrap:true,//自动换行
                columns:[[
                    {field:'id',title:'编号',width:80,align:'center'},
                    {field:'xxplValue',title:'下行频率',width:80,align:'center'},
                    {field:'tkplValue',title:'天空频率',width:80,align:'center'},
                    {field:'xtdValue',title:'差值',width:80,align:'center'},
                    {field:'systemName',title:'系统',width:80,align:'center'},
                    {field:'xhType',title:'信号类型',width:80,align:'center'},
                    {field:'tzslValue',title:'调制速率',width:80,align:'center'},
                    {field:'mslValue',title:'码速率',width:80,align:'center'},
                    {field:'tzdValue',title:'差值',width:80,align:'center'},
                    {field:'tzfsName',title:'调制方式',width:80,align:'center'},
                    {field:'tzysName',title:'调制样式',width:80,align:'center'},
                    {field:'xdbmCode',title:'信道编码',width:80,align:'center'},
                    {field:'bmType',title:'编码类型',width:80,align:'center'},
                    {field:'mlName',title:'码率',width:80,align:'center'},
                    {field:'oneCl',title:'单次策略1',width:80,align:'center'},
                    {field:'twoCl',title:'单次策略2',width:80,align:'center'},
                    {field:'buildDate',title:'人工登记时间',width:150,align:'center'},
                    {field:'buildTime',title:'机器发生时间',width:200,align:'center'}
                ]]
            });
        }
    </script>
</div>
</body>
</html>