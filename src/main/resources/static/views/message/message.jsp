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
            messageList();
        })
        //定时刷新页面
        setInterval(function() {
           // show();//暂不用
            messageList();
        }, 30000);
        function show(){//弹出消息框
            $.messager.show({
                title:'消息提醒',
                msg:'上线',
                timeout:5000,
                showType:'slide'
            });
        }

        function messageList() {//展示最新的动态消息列表
            // $.ajax({
            //     type: 'POST',
            //     async: false,
            //     dataType: "json",
            //     url: '/admin/message/getMessageList',//获取菜单
            //     data:{},
            //     success: function(data) {
            //
            //     }
            //
            // });
            $('#mL').datalist({
                url: '/admin/message/getMessageList',
                lines:true
            });
        }

    </SCRIPT>
</head>
<body>
<ul id="mL"></ul>
</body>
</html>