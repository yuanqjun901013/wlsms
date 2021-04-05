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
            getManualDataList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="getManualDataList" data-options="region:'center',split:true"></div>
<script type="text/javascript" th:inline="none">
    function getManualDataList() {//展示列表
      $('#getManualDataList').datagrid({
            url:'/data/data/getManualDataList',//参数
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
                {field:'createTime',title:'上报时间',width:150,align:'center'},
                {field:'proCode',title:'批次公文号',width:100,align:'center'}
            ]]
        });
    }
</script>
</div>
</body>
</html>