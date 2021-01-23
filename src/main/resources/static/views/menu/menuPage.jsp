<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WLSMS</title>
    <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../themes/color.css">
    <link rel="stylesheet" type="text/css" href="../../demo/demo.css">
    <link rel="stylesheet" type="text/css" href="../../demo/sidemenu/sidemenu_style.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
    <script th:inline="javascript">
    var menu = "menu/" + [[${menu}]];
    </script>
    <!--/*@thymesVar id="tab" type=""*/-->
    <div th:title="${tab}"  data-options="iconCls:'icon-tip'" style="padding:10px">
    <!--<div style="margin:20px 0;"></div>-->
    <div class="easyui-panel" style="padding:5px;width:207px;height: 100%;">
    <ul class="easyui-tree" data-options="url:menu,method:'get',animate:true,onSelect:getSelected"></ul>
    <script th:inline="javascript">
    function getSelected(obj) {
    var id = obj.id;

    }
    </script>
    </div>
    </div>
</div>
</body>
</html>