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

        //定时刷新页面
        setInterval(function() {
            // show();//暂不用
            queryAutoBuildList();
        }, 9000);
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="getDelete()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="getClear()">清空</a>
        <input class="easyui-datebox" id="buildDate" label="数据日期:" labelPosition="left" data-options="formatter:dateFormatter,parser:dateParser" style="width:190px;">
        <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="queryAutoBuildList" style="width:100%" data-options="region:'center',split:true"></div>
    </div>
    <script type="text/javascript" th:inline="none">
        var buildId = '';
        function queryAutoBuildList() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            var buildDate = $('#buildDate').datebox('getValue');
            $('#queryAutoBuildList').datagrid({
                url:'/data/buildNew/queryAutoBuildList',//参数
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
                        $.post('/data/buildNew/deleteAutoBuild',{ids:ids},function(result){
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
            buildId = id;
            getAutoDataList(buildId);
        }

        function getExport() {
            $('#getAutoDataList').datagrid('toExcel', '融合数据.xls');
        }

        function getAutoDataList(id) {//展示列表
            var queryBt = $('#queryBtDlg').textbox('getValue');
            var titleOs = $('#titleOsDlg').combogrid('getValue');
            $('#getAutoDataList').datagrid({
                url:'/data/buildNew/getAutoDataListById',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "id": id,
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
                toolbar:'#toolbarDlg',
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

        function editValue(){
            var row = $('#getAutoDataList').datagrid('getSelected');
            if (row){
                $('#dataUpdate').dialog('open').dialog('center').dialog('setTitle','修改');
                $('#fmm').form('load',row);
            }
        }

        function updateDataBuild(){
            $('#fmm').form('submit',{
                url: '/data/buildNew/updateDataBuild',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#dataUpdate').dialog('close');        // close the dialog
                            $('#getAutoDataList').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }
    </script>
<div id="dlg" class="easyui-dialog" style="width:100%; height: 100%"
     data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
    <div id="toolbarDlg">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editValue()">修改</a>
        <input id="titleOsDlg" name="titleOs" label="数据状态:" style="width:280px;">
        <input class="easyui-textbox" id="queryBtDlg" data-options="buttonText:'查询',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <script type="text/javascript" th:inline="none">
    //查询按钮
    $("#queryBtDlg").textbox({onClickButton:function(){
    getAutoDataList(buildId);
    }})

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
    </script>
    <div id="getAutoDataList" data-options="region:'center',split:true"></div>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="getExport()" style="width:90px">导出</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">关闭</a>
</div>
<div id="dataUpdate" class="easyui-dialog" style="width:600px; height: 500px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlgUpdate-buttons'">
    <form id="fmm" method="post" novalidate style="margin:0;padding:20px 50px">
        <h3>编辑底数信息</h3>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="wxName" data-options="required:true" label="卫星:" labelPosition="left" style="width:230px;">&nbsp;&nbsp;
            <input class="easyui-textbox" type="text" name="carPol" data-options="required:true" label="极化:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="tkplValue" data-options="required:true" label="天空频率:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="mslValue" data-options="required:true" label="码速率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="dplValue" data-options="required:true" label="电平:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="zzbValue" data-options="required:true" label="载噪比:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="xhType" data-options="required:true" label="信号类型:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="muladdr" data-options="required:true" label="多址方式:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="others" data-options="required:true" label="其他:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="tzysName" data-options="required:true" label="调制样式:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="bmType" data-options="required:true" label="编码类型:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="mlName" data-options="required:true" label="码率:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="exmlen" data-options="required:true" label="分组长度:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="fcycle" data-options="required:true" label="突发周期:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="flen" data-options="required:true" label="帧长:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="cf" data-options="required:true" label="差分:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="rm" data-options="required:true" label="扰码:" labelPosition="left" style="width:230px;">
            <input class="easyui-textbox" type="text" name="sindex" data-options="required:true" label="索引号:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input class="easyui-textbox" type="text" name="userProperties" data-options="required:true" label="用户属性:" labelPosition="left" style="width:230px;">
        </div>
        <div style="margin-bottom:15px">
            <input type="text" style="display: none" name = "id">
        </div>
    </form>
</div>
<div id="dlgUpdate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateDataBuild()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dataUpdate').dialog('close')" style="width:90px">取消</a>
</div>
</div>
</body>
</html>