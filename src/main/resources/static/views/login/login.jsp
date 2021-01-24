<!DOCTYPE html>
<html>
<HEAD>
	<META content="IE=11.0000" http-equiv="X-UA-Compatible">
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META name="GENERATOR" content="MSHTML 11.00.9600.17496">
	<TITLE>登录页面</TITLE>
	<SCRIPT src="../../jquery-1.9.1.min.js" type="text/javascript"></SCRIPT>
	<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
	<script type="text/javascript" src="../../jquery.easyui.min.js"></script>
	<STYLE>
		body{
			background: #ebebeb;
			font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;
			color: #222;
			font-size: 12px;
		}
		*{padding: 0px;margin: 0px;}
		.top_div{
			background: #008ead;
			width: 100%;
			height: 400px;
		}
		.ipt{
			border: 1px solid #d3d3d3;
			padding: 10px 10px;
			width: 290px;
			border-radius: 4px;
			padding-left: 35px;
			-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
			-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
			transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s
		}
		.ipt:focus{
			border-color: #66afe9;
			outline: 0;
			-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
			box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
		}
		.u_logo{
			background: url("../../themes/images/username.png") no-repeat;
			padding: 10px 10px;
			position: absolute;
			top: 42px;
			left: 40px;

		}
		.p_logo{
			background: url("../../themes/images/password.png") no-repeat;
			padding: 10px 10px;
			position: absolute;
			top: 11px;
			left: 40px;
		}
		a{
			text-decoration: none;
		}
		.tou{
			background: url("../../themes/images/tou.png") no-repeat;
			width: 97px;
			height: 92px;
			position: absolute;
			top: -87px;
			left: 140px;
		}
		.left_hand{
			background: url("../../themes/images/left_hand.png") no-repeat;
			width: 32px;
			height: 37px;
			position: absolute;
			top: -38px;
			left: 150px;
		}
		.right_hand{
			background: url("../../themes/images/right_hand.png") no-repeat;
			width: 32px;
			height: 37px;
			position: absolute;
			top: -38px;
			right: -64px;
		}
		.initial_left_hand{
			background: url("../../themes/images/hand.png") no-repeat;
			width: 30px;
			height: 20px;
			position: absolute;
			top: -12px;
			left: 100px;
		}
		.initial_right_hand{
			background: url("../../themes/images/hand.png") no-repeat;
			width: 30px;
			height: 20px;
			position: absolute;
			top: -12px;
			right: -112px;
		}
		.left_handing{
			background: url("../../themes/images/left-handing.png") no-repeat;
			width: 30px;
			height: 20px;
			position: absolute;
			top: -24px;
			left: 139px;
		}
		.right_handinging{
			background: url("../../themes/images/right_handing.png") no-repeat;
			width: 30px;
			height: 20px;
			position: absolute;
			top: -21px;
			left: 210px;
		}
		.text-center{text-align:center}

		html, body {
			height: 100%;
		}

		@keyframes rotate {
			from {
				transform: rotatez(0deg);
			}

			to {
				transform: rotatez(360deg);
			}
		}


		/*.login-box {*/
		/*	width: 100%;*/
		/*	max-width: 450px;*/
		/*	height: 400px;*/
		/*	position: absolute;*/
		/*	top: 50%;*/

		/*	margin-top: -200px;*/
		/*	z-index: 999;*/
		/*	!*设置负值，为要定位子盒子的一半高度*!*/
		/*}*/


		@media screen and (min-width: 500px) {
			.login-box{
				left: 65%;
				margin: 0;
				top: 20%;
				background: #fff;
				padding: 10px 25px;
				min-height: 300px;
				border-radius: 5px;
			}

		}

		.login-content {
			border-bottom-left-radius: 8px;
			border-bottom-right-radius: 8px;
			height: 310px;
			width: 100%;
			max-width: 450px;
			/*background-color: rgba(255, 250, 2550, .6);*/
			float: left;
		}

		.input-group input{
			width: 100% !important;
			border: none;
			border-bottom: 1px solid #e2e2e2;
			outline: none !important;
			box-shadow: none;
		}

		.login-title {
			border-top-left-radius: 10px;
			border-top-right-radius: 10px;
			margin-bottom: 10px;
			padding: 10px 10px;
			/*background-color: rgba(0, 0, 0, .6);*/
		}

		.login-title h1 {
			/*margin-top: 10px !important;*/
		}

		.login-title small {
			color: #333;
			/*margin-left: -50px;*/
		}

		.link p {
			line-height: 20px;
			margin-top: 30px;
		}

		.tab {
			display: flex;
			margin: 0 auto;
			justify-content: space-around;
			align-items: center;
		}

		.tab-item {
			width: 33.333%;
			height: 30px;
			line-height: 30px;
			text-align: center;
			cursor: pointer;
		}

		.on {
			color: #1E9FFF;
			border-bottom: 2px solid #1E9FFF;
		}

		input:focus,.form-control:focus{
			outline: none !important;
			box-shadow: none;
		}
		button{outline:none !important;}

		@media screen and (max-width: 900px) {

			.login-box{
				left: 20%;
				margin: 0;
				top: 20%;
				background: #fff;
				padding: 10px 20px;
				min-height: 300px;
				border-radius: 5px;
			}
		}
		@media screen and (max-width: 750px) {

			.login-box{
				left: 2%;
				margin: 0;
				top: 5%;
				background: #fff;
				padding: 10px 20px;
				min-height: 300px;
				border-radius: 5px;
				max-width: 340px;
			}
			.login-content{
				max-width: 340px;
			}
		}
	</style>

	<SCRIPT th:inline="javascript">
		$(function(){
			//得到焦点
			$("#pwd").focus(function(){
				$("#left_hand").animate({
					left: "150",
					top: " -38"
				},{step: function(){
						if(parseInt($("#left_hand").css("left"))>140){
							$("#left_hand").attr("class","left_hand");
						}
					}}, 2000);
				$("#right_hand").animate({
					right: "-64",
					top: "-38px"
				},{step: function(){
						if(parseInt($("#right_hand").css("right"))> -70){
							$("#right_hand").attr("class","right_hand");
						}
					}}, 2000);
			});
			//失去焦点
			$("#pwd").blur(function(){
				$("#left_hand").attr("class","initial_left_hand");
				$("#left_hand").attr("style","left:100px;top:-12px;");
				$("#right_hand").attr("class","initial_right_hand");
				$("#right_hand").attr("style","right:-112px;top:-12px");
			});
		});
		//提交登录
		function loginFm()
		{
			document.forms[0].submit();
		}
		window.onload = function ()//用window的onload事件，窗体加载完毕的时候
		{
			if (window.top.location.href != window.location.href) {
				top.location.reload(true);
			}
		}
	</SCRIPT>
</HEAD>
<BODY>
<form action="doLogin" method="post">
	<DIV class="top_div"></DIV>
	<DIV style="margin: -150px auto auto;  border-image: none; width: 400px; height: 300px; text-align: center;">
	<DIV style="width: 165px; height: 96px; position: absolute;">
		<DIV class="tou"></DIV>
		<DIV class="initial_left_hand" id="left_hand"></DIV>
		<DIV class="initial_right_hand" id="right_hand"></DIV>
	</DIV>

	<div class="login-box"  >
		<div class="login-title">
			<h1 class="text-center">
				<small><span>WLSMS测试预览平台</span></small>
			</h1>
		</div>
		<div class="login-content" id="myDiv">
			<div class="tab">
				<div class="tab-item on forget">账号密码登录</div>
			</div>
			<div class="content-box">
				<P style="padding: 30px 0px 10px; position: relative;">
					<SPAN  class="u_logo"></SPAN>
					<!--/*@thymesVar id="userNo" type=""*/-->
					<INPUT class="ipt" type="text" placeholder="请输入账号" id="userNo" name="userNo" th:value="${userNo}" />
				</P>
				<P style="position: relative;">
					<SPAN class="p_logo"></SPAN>
					<INPUT class="ipt" id="pwd" name="pwd" type="password" placeholder="请输入密码"  value=""/>
				</P>
				<DIV style="height: 50px; line-height: 50px; margin-top: 30px; border-top: 1px solid rgb(231, 231, 231);">
				<SPAN style="text-align: center;display:block;">
					<A class="easyui-linkbutton"  style="width: 178px;color: rgba(102,175,233,.6);" onclick="loginFm();">登录</A>
				</SPAN>
				</DIV>
			</div>
		</div>
	</div>
	</DIV>
</form>
</BODY>
</HTML>