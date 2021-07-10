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
            queryAutoBuildList();
        })
    </SCRIPT>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
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
                singleSelect:true,
                remoteFilter: true,
                clientPaging: false,
                nowrap:false,//自动换行
                toolbar:'#toolbar',
                columns:[[
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
            alert("hello"+id);
        }
    </script>
</div>
</body>
</html>