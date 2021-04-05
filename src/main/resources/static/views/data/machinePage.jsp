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
            getMachineDataList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="getMachineDataList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function getMachineDataList() {//展示列表
      $('#getMachineDataList').datagrid({
            url:'/data/data/getMachineDataList',//参数
            method: 'post',
            //携带参数
            queryParams: {
            },
            fitColumns:false,
            striped:true,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            remoteFilter: true,
            clientPaging: false,
            columns:[[
                {field:'id',title:'编号',width:80,align:'center'},
                {field:'positionCode',title:'阵地编码',width:80,align:'center'},
                {field:'wxName',title:'wx名称',width:80,align:'center'},
                {field:'zplValue',title:'中频',width:80,align:'center'},
                {field:'dplValue',title:'电平',width:80,align:'center'},
                {field:'tkplValue',title:'天空频率',width:80,align:'center'},
                {field:'xhType',title:'信号类型',width:80,align:'center'},
                {field:'mslValue',title:'码速率',width:80,align:'center'},
                {field:'zzbValue',title:'载噪比',width:80,align:'center'},
                {field:'tzysName',title:'调制样式',width:80,align:'center'},
                {field:'cjTime',title:'采集时间',width:80,align:'center'},
                {field:'proCode',title:'公文批次号',width:100,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>