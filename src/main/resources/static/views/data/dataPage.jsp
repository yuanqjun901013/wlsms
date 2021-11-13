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

        //定时刷新页面
        setInterval(function() {
            // show();//暂不用
            getReload();
        }, 9000);
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
        <div id="toolbarDlg">
            <input id="titleOsDlg" name="titleOs" label="数据状态:" style="width:280px;">
            <input class="easyui-textbox" id="queryBtDlg" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
        </div>
        <div id="getAutoDataList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
    <script type="text/javascript" th:inline="none">
        function getReload(){
            $('#cbgManual').combogrid({
                delay: 250,
                mode: 'remote',
                url: '/data/buildNew/queryManualByDate',
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
                url: '/data/buildNew/queryMachineByDate',
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


            $('#titleOsDlg').combogrid({
                delay: 250,
                mode: 'remote',
                url: '/data/buildNew/queryTitleOs',
                idField: 'titleOs',
                textField: 'titleOs',
                labelPosition:"left",
                striped:true,
                fitColumns: true,
                nowrap:false,//自动换行
                columns: [[
                    {field:'id',title:'序号',width:90,sortable:true},
                    {field:'titleOs',title:'数据状态',width:120,sortable:true}
                ]]
            });
        }

        function clearFrom(){
            $('#fm').form('clear');
        }

        function openAuto(){
            //比对开始
            $('#fm').form('submit',{
                url: '/data/buildNew/openAuto',
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

        //查询按钮
        $("#queryBtDlg").textbox({onClickButton:function(){
                getAutoDataList();
            }})

        function getAutoDataList() {//展示列表
            var cbgManual = $('#cbgManual').combogrid('getValue');
            var cbgMachine = $('#cbgMachine').combogrid('getValue');
            var queryBt = $('#queryBtDlg').textbox('getValue');
            var titleOs = $('#titleOsDlg').combogrid('getValue');
            $('#getAutoDataList').datagrid({
                url:'/data/buildNew/getAutoDataList',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "buildDate": cbgManual,
                    "buildTime": cbgMachine,
                    "queryBt": queryBt,
                    "titleOs": titleOs
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
                    {field:'titleOs',title:'匹配状态',width:100,align:'center'},
                    {field:'buildType',title:'生成方式',width:100,align:'center'},
                    {field:'positionName',title:'地址',width:100,align:'center'},
                    {field:'wxName',title:'卫星名称',width:80,align:'center'},
                    {field:'carPol',title:'极化',width:80,align:'center'},
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
                    // {field:'appearTime',title:'发现时间',width:150,align:'center'},
                    {field:'buildDate',title:'人工登记时间',width:150,align:'center'},
                    {field:'buildTime',title:'机器发生时间',width:200,align:'center'}
                ]]
            });
        }
    </script>
</div>
</body>
</html>