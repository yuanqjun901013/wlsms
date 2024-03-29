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
            queryAutoBuildList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="getDelete()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="getClear()">清空</a>
        <input class="easyui-datebox" id="buildDate" label="数据日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="queryAutoBuildList" data-options="region:'center',split:true"></div>
    <script type="text/javascript" th:inline="none">
        function queryAutoBuildList() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            var buildDate = $('#buildDate').datebox('getValue');
            $('#queryAutoBuildList').datagrid({
                url:'/data/macAuto/queryAutoBuildList',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "queryBt":queryBt,
                    "buildDate":buildDate,
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
                    {field:'详情',title:'操作',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' onclick='getDetail("+row.id+")'>详情</a>";
                        },
                        width:100,align:'center'},
                    {field:'remark',title:'策略比对说明',width:250,align:'center'},
                    {field:'buildTime',title:'机器数据时间',width:200,align:'center'},
                    {field:'buildDate',title:'人工数据日期',width:150,align:'center'},
                    {field:'createTime',title:'首次发生时间',width:200,align:'center'},
                    {field:'editTime',title:'更新时间',width:200,align:'center'}
                ]]
            });
        }

        function getClear() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            var buildDate = $('#buildDate').datebox('getValue');
            $('#queryAutoBuildList').datagrid({
                url:'/batch/common/getClear',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "queryBt":queryBt,
                    "buildDate":buildDate,
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
                    {field:'详情',title:'操作',formatter:function(value,row,index)
                        {
                            return "<a href='javascript:void(0)' onclick='getDetail("+row.id+")'>详情</a>";
                        },
                        width:100,align:'center'},
                    {field:'remark',title:'策略比对说明',width:250,align:'center'},
                    {field:'buildTime',title:'机器数据时间',width:200,align:'center'},
                    {field:'buildDate',title:'人工数据日期',width:150,align:'center'},
                    {field:'createTime',title:'首次发生时间',width:200,align:'center'},
                    {field:'editTime',title:'更新时间',width:200,align:'center'}
                ]]
            });
        }

        function getDelete(){
            var row = $('#queryAutoBuildList').datagrid('getChecked');
            if (row){
                var ids = "";
                for (var i=0;i < row.length;i++)
                {
                    ids += row[i].id + ",";
                }
                $.messager.confirm('确认提醒','确定删除这些数据?',function(r){
                    if (r){
                        $.post('/data/macAuto/deleteAutoBuild',{ids:ids},function(result){
                            if (result.success){
                                $('#queryAutoBuildList').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.alert("消息提醒",result.msg);
                            }
                        },'json');
                    }
                });
            }
        }

        //查询按钮
        $("#queryBt").textbox({onClickButton:function(){
                queryAutoBuildList();
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

        function getDetail(id){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','数据展示');
            getAutoDataList(id);
        }

        function getExport() {
            $('#getAutoDataList').datagrid('toExcel', '融合数据.xls');
        }

        function getAutoDataList(id) {//展示列表
            $('#getAutoDataList').datagrid({
                url:'/data/macAuto/getAutoDataListById',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "queryBt": id,
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
<div id="dlg" class="easyui-dialog" style="width:100%; height: 100%"
     data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
    <div id="getAutoDataList" data-options="region:'center',split:true"></div>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="getExport()" style="width:90px">导出</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">关闭</a>
</div>
</body>
</html>