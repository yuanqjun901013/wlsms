<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>卫星智能处理系统</title>
    <link rel="stylesheet" type="text/css" href="../../themes/material-teal/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../themes/color.css">
    <link rel="stylesheet" type="text/css" href="../../demo/demo.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
    <SCRIPT th:inline="javascript">
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
        })
    </SCRIPT>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height:1px"></div>
        <div data-options="region:'west',split:true" style="width:20%;padding:10px">
            <div style="width:207px;height: 98%;">
                <ul class="easyui-tree" data-options="url:menu,method:'get',animate:true,onSelect:getSelected"></ul>
                <script th:inline="javascript">
                    var menu = "menu/" + [[${menu}]];
                    function getSelected(obj) {
                        var id = obj.id;
                    }
                </script>
            </div>
        </div>
        <div id="dg" data-options="region:'center',split:true">
            <table class="easyui-datagrid">
              <!--  <thead>
                <tr>
                    <th data-options="field:'id',align:'center'" width="80">菜单ID</th>
                    <th data-options="field:'name',align:'center'" width="80">菜单名称</th>
                    <th data-options="field:'url',align:'center'" width="80">菜单路径</th>
                    <th data-options="field:'parentId',align:'center'" width="80">上级ID</th>
                    <th data-options="field:'isNeedAuth',align:'center'" width="80">权限控制</th>
                    <th data-options="field:'menuCode',align:'center'" width="80">编码</th>
                    <th data-options="field:'level',align:'center'" width="60">菜单级别</th>
                </tr>
                </thead> -->
            </table>
        </div>
        <SCRIPT type="text/javascript" th:inline="none">
            $(function(){
                $('#dg').datagrid({
                    url:'/index/menu/pageQueryMenu',//获取菜单
                    method: 'post',
                    //携带参数
                    queryParams: {
                        "request":0
                    },
                    fitColumns:true,
                    striped:true,
                    pagination:true,
                    rownumbers:true,
                    singleSelect:true,
                    columns:[[
                        {field:'id',title:'菜单ID',width:80,align:'center'},
                        {field:'name',title:'菜单名称',width:80,align:'center'},
                        {field:'url',title:'菜单路径',width:80,align:'center'},
                        {field:'parentId',title:'上级ID',width:80,align:'center'},
                        {field:'isNeedAuth',title:'权限控制',width:80,align:'center'},
                        {field:'menuCode',title:'编码',width:80,align:'center'},
                        {field:'level',title:'菜单级别',width:60,align:'center'}
                    ]]
                });
            })
        </SCRIPT>
    </div>
</body>
</html>