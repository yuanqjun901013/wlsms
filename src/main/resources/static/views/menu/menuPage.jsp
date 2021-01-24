<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>卫星智能处理系统</title>
    <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../themes/color.css">
    <link rel="stylesheet" type="text/css" href="../../demo/demo.css">
    <link rel="stylesheet" type="text/css" href="../../demo/sidemenu/sidemenu_style.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
</head>
<body>
<div>
    <script th:inline="javascript">
    var menu = "menu/" + [[${menu}]];
    </script>
    <div style="width:207px;height: 100%;">
    <ul class="easyui-tree" data-options="url:menu,method:'get',animate:true,onSelect:getSelected"></ul>
    <script th:inline="javascript">
    function getSelected(obj) {
    var id = obj.id;

    }
    </script>
    </div>
</div>
</body>
</html>