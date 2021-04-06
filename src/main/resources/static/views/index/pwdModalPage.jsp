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
        var userInfo = [[${userInfo}]];//获取用户信息
        var userNameCode = [[${userNameCode}]];
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            $("#pass").textbox('setValue', '');
            $("#passCfm").textbox('setValue', '');
            $("#oldPass").textbox('setValue', '');
        })

        function confirmPwd(){
            //密码中必须包含大小写 字母、数字、特称字符，至少8个字符，最多30个字符；
            // var pwdRegexA = new RegExp('(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,30}');
            //密码中必须包含字母（不区分大小写）、数字、特称字符，至少8个字符，最多30个字符；
            // var pwdRegexB = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
            //密码中必须包含字母（不区分大小写）、数字，至少8个字符，最多16个字符；
            var userNo= userInfo.userNo;
            var pwd = userInfo.pwd;
            var oldPass = $("#oldPass").textbox('getValue');
            var pass = $("#pass").textbox('getValue');
            var passCfm = $("#passCfm").textbox('getValue');
            var pwdRegexC = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z]).{6,16}');
            if(pwd != oldPass){
                alert("您的旧密码输入错误，请重新输入！");
                return;
            }else if(pass != passCfm){
                alert("两次密码输入不一致，请重新输入！");
                return;
            }
            else if (!pwdRegexC.test(pass)) {
                alert("您的密码复杂度太低（密码中必须包含字母（不区分大小写）、数字，至少6个字符，最多16个字符），请及时修改密码！");
                return;
            }else if(oldPass == pass){
                alert("您的密码与旧密码一致，请重新设置！");
                return;
            }else if(oldPass == '' || pass == '' || passCfm == ''){
                alert("输入框不能为空，请正确输入！");
                return;
            }else{
                savePwd(userNo,pass);
            }
        }
        function savePwd(userNo,pass){
            $.ajax({
                type: 'POST',
                async: false,
                dataType: "json",
                url: '/admin/user/editUserPwd',//修改密码
                data:{
                    "userNo":userNo,
                    "pwd": pass
                },
                success: function(data) {
                    $.messager.alert("消息提醒", "保存成功！即将注销登录", "info",function (){
                        top.location.href = "/user/logout";
                    });
                }
            });
        }



        $.extend($.fn.validatebox.defaults.rules, {
            confirmOldPass: {
                validator: function(value){
                    var pwd = userInfo.pwd;
                    return value == pwd;
                },
                message: '旧密码输入错误!'
            }
            ,confirmPass: {
                validator: function(value, param){
                    var pass = $(param[0]).passwordbox('getValue');
                    return value == pass;
                },
                message: '两次密码输入不一致!'
            },
            confirmPwdLength:{
                validator:function(value){
                    //密码中必须包含字母（不区分大小写）、数字，至少6个字符，最多16个字符；
                    return /(?=.*[0-9])(?=.*[a-zA-Z]).{6,16}$/.test(value);
                    //6-16位只能包含 字母、数字、下划线'
                    //return /^[0-9a-zA-Z_]{6,16}$/.test(value);
                },
                message:'包含字母、数字6~16个字符！'
            }
        });

    </SCRIPT>
</head>
<body>
<div>
    <div style="margin-bottom:20px"></div>
    <div style="margin-bottom:20px">
        <input id="oldPass" class="easyui-passwordbox" prompt="输入旧密码" iconWidth="28" validType="confirmOldPass" style="width:50%;height:34px;padding:10px;">
    </div>
    <div style="margin-bottom:20px">
        <input id="pass" class="easyui-passwordbox" prompt="输入新密码" iconWidth="28" validType="confirmPwdLength" style="width:50%;height:34px;padding:10px">
    </div>
    <div style="margin-bottom:20px">
        <input id="passCfm" class="easyui-passwordbox" prompt="新密码验证" iconWidth="28" validType="confirmPass['#pass']" style="width:50%;height:34px;padding:10px">
    </div>
    <div style="margin-bottom:20px" align="center">
        <a id="pwdBt" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="confirmPwd();" style="width:80px">保存</a>
    </div>
</div>
<div id="viewer"></div>
<script type="text/javascript">
    $('#pass').passwordbox({
        inputEvents: $.extend({}, $.fn.passwordbox.defaults.inputEvents, {
            keypress: function(e){
                var char = String.fromCharCode(e.which);
                $('#viewer').html(char).fadeIn(200, function(){
                    $(this).fadeOut();
                });
            }
        })
    })
</script>
<style>
    #viewer{
        position: relative;
        padding: 0 60px;
        top: -70px;
        font-size: 54px;
        line-height: 60px;
    }
</style>
</body>
</html>