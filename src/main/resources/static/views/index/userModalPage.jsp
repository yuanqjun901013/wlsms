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
        var userInfo = [[${userInfo}]];//获取用户信息
        var userNameCode = [[${userNameCode}]];
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            if(userInfo){
                document.getElementById("editDiv").style.display="none";//隐藏
                //赋值
                $("#userName").textbox('setValue', userNameCode);
                $("#userNo").textbox('setValue', userInfo.userNo);
                $("#age").textbox('setValue', userInfo.age);
                $("#job").textbox('setValue', userInfo.job);
                $("#tel").textbox('setValue', userInfo.tel);
                $("#phone").textbox('setValue', userInfo.phone);
                $("#email").textbox('setValue', userInfo.email);
                if(userInfo.sex == "1"){
                    $("#sexM").radiobutton("check");
                }else {
                    $("#sexW").radiobutton("check");
                }
                //默认只读
                $("#userName").textbox('readonly',true);
                $("#userNo").textbox('readonly',true);
                $("#age").textbox('readonly',true);
                $("#job").textbox('readonly',true);
                $("#tel").textbox('readonly',true);
                $("#phone").textbox('readonly',true);
                $("#email").textbox('readonly',true);
                $("#sexM").radiobutton("disable",false);
                $("#sexW").radiobutton("disable",false);
            }else {
                $("#userInfo").window("open");
                document.getElementById("editDiv").style.display="none";//隐藏
                //默认只读
                $("#userName").textbox('readonly',true);
                $("#userNo").textbox('readonly',true);
                $("#age").textbox('readonly',true);
                $("#job").textbox('readonly',true);
                $("#tel").textbox('readonly',true);
                $("#phone").textbox('readonly',true);
                $("#email").textbox('readonly',true);
                $("#sexM").radiobutton("disable",false);
                $("#sexW").radiobutton("disable",false);
            }
        })

        function editUserView(){//编辑个人资料
            document.getElementById("editDiv").style.display="";//展示
            $("#userName").textbox('readonly',true);
            $("#userNo").textbox('readonly',true);
            $("#age").textbox('readonly',false);
            $("#job").textbox('readonly',false);
            $("#tel").textbox('readonly',false);
            $("#phone").textbox('readonly',false);
            $("#email").textbox('readonly',false)
            $("#sexM").radiobutton("disable",false);
            $("#sexW").radiobutton("disable",false);
        }

        function cancelUser(){//取消编辑个人信息
            document.getElementById("editDiv").style.display="none";//隐藏
            //默认只读
            $("#userName").textbox('readonly',true);
            $("#userNo").textbox('readonly',true);
            $("#age").textbox('readonly',true);
            $("#job").textbox('readonly',true);
            $("#tel").textbox('readonly',true);
            $("#phone").textbox('readonly',true);
            $("#email").textbox('readonly',true);
            $("#sexM").radiobutton("disable",false);
            $("#sexW").radiobutton("disable",false);
        }

        function saveUser(){
            var id = userInfo.id;
            var userNo= userInfo.userNo;
            var age = $("#age").textbox('getValue');
            var job = $("#job").textbox('getValue');
            var tel = $("#tel").textbox('getValue');
            var phone = $("#phone").textbox('getValue');
            var email = $("#email").textbox('getValue');

            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/admin/user/editUserByUserNo',//修改个人资料
                data:{
                    "id":id,
                    "userNo":userNo,
                    "age": age,
                    "job":job,
                    "tel":tel,
                    "phone": phone,
                    "email": email
                },
                success: function(data) {
                    $.messager.alert("消息提醒", "保存成功!", "info",function (){
                        document.getElementById("editDiv").style.display="none";//隐藏
                        top.location.href = "/index/main";
                    });
                }
            });
        }
    </SCRIPT>
</head>
<body>
<div>
    <div style="margin-bottom:15px">
        <a class="easyui-linkbutton" onclick="editUserView();" data-options="iconCls:'icon-edit'">编辑</a>
    </div>
    <div style="margin-bottom:15px">
        <input id="userName" class="easyui-textbox" label="姓名:" labelPosition="left" style="width:45%;">&nbsp;&nbsp;
        <input id="userNo" class="easyui-textbox" label="工号:" labelPosition="left" style="width:45%;">
    </div>
    <div style="margin-bottom:15px">
        <form id="ff">
            <input id="age" class="easyui-textbox" label="年龄:" labelPosition="left" style="width:45%;">&nbsp;&nbsp;
            性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            男<input id="sexM" class="easyui-radiobutton" name="sex" value="1">&nbsp;&nbsp;
            女<input id="sexW" class="easyui-radiobutton" name="sex" value="2">
        </form>
    </div>
    <div style="margin-bottom:15px">
        <input id="job" class="easyui-textbox" label="岗位:" labelPosition="left" style="width:45%;" >&nbsp;&nbsp;
        <input id="tel" class="easyui-textbox" label="固定电话:" labelPosition="left" style="width:45%;">
    </div>
    <div style="margin-bottom:15px">
        <input id="phone" class="easyui-textbox" label="手机:" labelPosition="left" style="width:45%;">&nbsp;&nbsp;
        <input id="email" class="easyui-textbox" label="Email:" labelPosition="left"  style="width:45%;">
    </div>
    <div id="editDiv" style="margin-bottom:15px" align="center">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="saveUser();" style="width:80px">保存</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="cancelUser();" style="width:80px">取消</a>
    </div>
</div>
</body>
</html>