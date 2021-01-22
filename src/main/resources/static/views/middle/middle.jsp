<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>卫星智能处理系统</title>
    <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../demo/demo.css">
    <link rel="stylesheet" type="text/css" href="../../demo/sidemenu/sidemenu_style.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
    <!--/*@thymesVar id="tab" type=""*/-->
    <div th:title="${tab}" data-options="href:'../../demo/accordion/_content.html',iconCls:'icon-no'" style="padding:10px"></div>
</div>
</body>
</html>