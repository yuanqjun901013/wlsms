<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport">
	<!-- 避免IE使用兼容模式 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content='spring cloud,快速开发平台'/>
	<meta name="description" content='WLSMS 卫星智能处理系统'/>
	<!-- 浏览器标签图片 -->
	<!--href="/topjui/images/favicon.ico"-->
	<link rel="shortcut icon" href="../../favicon.ico"/>
	<link rel="stylesheet" href="../../bootstrap.min.css">
	<link rel="stylesheet" href="../../font-awesome.min.css">

	<style type="text/css">
		html, body {
			height: 100%;
		}
		.con{
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			z-index: 99999999999;
			display: none;
		}
		.loading{
			cursor: pointer;
			pointer-events: none;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%,-50%);
			z-index: 999999999999;
			width: 300px;
			height: 200px;
			/*background: #fff;*/
			text-align: center;
			line-height: 200px;
			border-radius: 8px;
			vertical-align: center;
			color: #fff;
		}
		.loading:before {
			content: '';
			display: inline-block;
			width: 50px;
			height: 50px;
			margin-right: 0.5em;
			color: red;
			border: 5px solid #fff;
			border-radius: 50%;
			vertical-align: -10%;
			clip-path: polygon(0% 0%, 100% 0%, 100% 80%, 0% 80%);
			animation: rotate 1s linear infinite;
		}

		@keyframes rotate {
			from {
				transform: rotatez(0deg);
			}

			to {
				transform: rotatez(360deg);
			}
		}
		.box {
			background: url("../../login_bg.png");
			background-size: 100% 100%;
			margin: 0 auto;
			position: relative;
			width: 100%;
			height: 100%;
		}

		.login-box {
			width: 100%;
			max-width: 450px;
			height: 400px;
			position: absolute;
			top: 50%;

			margin-top: -200px;
			z-index: 999;
			/*设置负值，为要定位子盒子的一半高度*/
		}

		.QR-code-box {
			width: 100%;
			max-width: 150px;
			height: 185px;
			background: url("");
			background-size: cover;
			position: absolute;
			top: 50%;
			margin-top: 170px;;
		}

		@media screen and (min-width: 500px) {
			.login-box{
				left: 65%;
				margin: 0;
				top: 20%;
				background: #fff;
				padding: 10px 25px;
				min-height: 500px;
				border-radius: 5px;
			}

			.QR-code-box {
				left: 50%;
				/*设置负值，为要定位子盒子的一半宽度*/
				margin-left: -75px;
			}
		}

		.form {
			width: 100%;
			max-width: 500px;
			height: 275px;
			margin: 2px auto 0px auto;
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

		.input-group {
			margin: 15px 0px 0px 0px !important;
		}
		.input-group input{
			width: 100% !important;
			border: none;
			border-bottom: 1px solid #e2e2e2;
			outline: none !important;
			box-shadow: none;
		}
		.input-group-addon{
			border: none;
			background: transparent;
			color: #999;
		}
		.form-control,
		.input-group {
			height: 40px;
		}

		.form-actions {
			margin-top: 0px;
		}

		.form-group {
			margin-bottom: 0px !important;
		}

		.login-title {
			border-top-left-radius: 8px;
			border-top-right-radius: 8px;
			margin-bottom: 20px;
			/*padding: 20px 10px;*/
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

		.btn-sm {
			padding: 8px 24px !important;
			font-size: 16px !important;
		}

		.flag {
			position: absolute;
			top: 10px;
			right: 10px;
			color: #fff;
			font-weight: bold;
			font: 14px/normal "microsoft yahei", "Times New Roman", "宋体", Times, serif;
		}

		.code_box {
			display: flex;
			justify-content: space-between;
		}

		#canvas {
			vertical-align: middle;
		}

		.tab {
			display: flex;
			margin: 0 auto;
			justify-content: space-around;
			align-items: center;
		}

		.tab-item {
			width: 33.333%;
			height: 50px;
			line-height: 50px;
			text-align: center;
			cursor: pointer;
		}

		.on {
			color: #1E9FFF;
			border-bottom: 2px solid #1E9FFF;
		}

		.form {
			display: none;
		}

		.active {
			display: block;
		}

		.login_qrcode_content {
			margin-top: 20px !important;
		}

		.bg {
			position: absolute;
			left: 0;
			top: 0;
			width: 100%;
			height: 100%;
			background: rgba(0, 0, 0, 0.4);
			z-index: 9;
		}
		.btn-blue{
			background: #1E9FFF;
			color: #fff;
		}
		.btn-blue:hover,.btn-blue:active,.btn-blue:focus{
			background: #1E9FFF;
			color: #fff;
		}
		.btn:hover{
			opacity: 0.8;
		}
		input:focus,.form-control:focus{
			outline: none !important;
			box-shadow: none;
		}
		button{outline:none !important;}
		.manage{
			position: absolute;
			top: 20px;
			left: 20px;
			color: #fff;
			font-weight: 500;
		}
		#canvas{
			border: 1px solid #e2e2e2;
			border-radius: 5px;
		}
		@media screen and (max-width: 900px) {
			.box {
				background: url("../../login_bg_m.png");
				background-size: 100% 100%;
			}
			.login-box{
				left: 20%;
				margin: 0;
				top: 20%;
				background: #fff;
				padding: 10px 20px;
				min-height: 500px;
				border-radius: 5px;
			}
		}
		@media screen and (max-width: 750px) {
			.box {
				background: url("../../login_bg_m.png");
				background-size: 100% 100%;
			}
			.login-box{
				left: 2%;
				margin: 0;
				top: 5%;
				background: #fff;
				padding: 10px 20px;
				min-height: 500px;
				border-radius: 5px;
				max-width: 340px;
			}
			.login-content{
				max-width: 340px;
			}
		}
	</style>
	<title>WLSMS 卫星智能处理系统 - 基于Spring Cloud微服务架构</title>
</head>
<body>
<div class="box">
	<div class="bg"></div>
	<div class="con" id="load"><div class="loading">加载中，请稍后...</div></div>
	<div class="login-box"  >
		<div class="login-title">
			<!--            <div style="float: left;margin-left:10px;">-->
			<!--                &lt;!&ndash;src="/public/images/logo_100.png"&ndash;&gt;-->
			<!--                <img th:src="${sysConfig.companyLoginIcon}" alt="" style="width: 60px;height: 60px;">-->
			<!--            </div>-->
			<h1 class="text-center">
				<small><span>MISBoot快速开发平台</span></small>
			</h1>
		</div>
		<div class="login-content" id="myDiv">
			<div class="tab">
				<div class="tab-item on forget">账号密码登录</div>
				<div class="tab-item dingding-tab">钉钉扫码登录</div>
				<div class="tab-item">微信扫码登录</div>
			</div>
			<div class="content-box">
				<div class="form active ">
					<form id="modifyPassword" class="form-horizontal" action="#" method="post">
						<input type="hidden" id="referer" name="referer" value="${param.referer}">
						<div class="form-group">
							<div class="col-xs-12">
								<div class="input-group">
                                    <span class="input-group-addon"><span
											class="glyphicon glyphicon-user"></span></span>
									<input type="text" id="userNameId" name="userNameId" class="form-control"
										   placeholder="用户名"
										   value="ewsd0001">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<div class="input-group">
                                    <span class="input-group-addon"><span
											class="glyphicon glyphicon-lock"></span></span>
									<input type="password" id="password" name="password" class="form-control"
										   placeholder="密码" value="ewsd0001">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<div class="input-group code_box">
									<div style="width: 76%;display: flex;justify-content: space-between;align-items: center;">
                                    <span class="input-group-addon" style="line-height: 1.9;width: 12.5%"><span
											class="glyphicon glyphicon-question-sign"></span></span>
										<input type="text" id="code" name="code" class="form-control pwd"
											   placeholder="验证码" style="width: 87.5%"></div>
									<div style="width: 20%;height: 40px">
										<canvas id="canvas" width="80%" height="38px" ></canvas>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group" style="margin-top: 15px">
							<div class="col-xs-12" style="text-align:right;">
								<a href="#" id="forgetThePassword">忘记密码</a>
							</div>
						</div>
						<div class="form-group form-actions" >
							<div class="col-xs-12 text-center">
								<button type="button" id="login" class="btn btn-sm btn-blue" style="width: 100%;border-radius:25px;margin-top: 10px">
									登录
								</button>
								<button type="button" id="reset" class="btn btn-sm" style="width: 100%;border-radius:25px;margin-top: 10px">
									重置
								</button>
							</div>
						</div>

					</form>
					<form id="retrievePassword" class="form-horizontal" action="#" method="post">
						<div class="form-group">
							<div class="col-xs-12">
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
									<input type="text" id="lookForUserNameId" name="lookForUserNameId" class="form-control"
										   placeholder="请输入账号"
										   value="">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<div class="input-group code_box">
									<div style="width: 100%;display: flex;justify-content: space-between;align-items: center;">
                                    <span class="input-group-addon" style="line-height: 1.9;width: 40px"><span
											class="glyphicon glyphicon-envelope"></span></span>
										<input type="text" id="email" name="email" class="form-control pwd"
											   placeholder="请输绑定的邮箱" >
									</div>
								</div>
							</div>
						</div>

						<div class="form-group form-actions" style="margin-top: 25px">
							<div class="col-xs-12 text-center">
								<button type="button" id="modifies" class="btn btn-sm btn-blue" style="width: 100%;border-radius:25px;margin-top: 10px">
									确认找回
								</button>
								<button type="button" id="goBack" class="btn btn-sm" style="width: 100%;border-radius:25px;margin-top: 10px">
									返回登录
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="form dingding">
					<div id="login_container" style="text-align: center;margin-top: 10px;"></div>
				</div>
				<div class="form weixin">

				</div>
			</div>
		</div>

	</div>
	<!--     注释掉二维码-->
	<!--    <div class="QR-code-box" style="">-->
	<!--    </div>-->
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-body">
				<span class="text-danger"><i class="fa fa-warning"></i> <span id="alertMsg">用户名或密码错误，请重试！</span></span>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="myModalSuccess" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-body">
                <span class="text-danger"><i class="fa fa-check"></i> <span
						id="alertMsgSuccess">用户名或密码错误，请重试！</span></span>
			</div>
		</div>
	</div>
</div>
<!-- 引入jQuery -->
<script src="/plugins/jquery/jquery.min.js"></script>
<script src="/plugins/jquery/jquery.cookie.js"></script>
<script src="/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="/plugins/bootstrap/plugins/html5shiv.min.js"></script>
<script src="/plugins/bootstrap/plugins/respond.min.js"></script>

<![endif]-->
<script type="text/javascript">
	if (navigator.appName == "Microsoft Internet Explorer" &&
			(navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE6.0" ||
					navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE7.0" ||
					navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE8.0")
	) {
		alert("您的浏览器版本过低，请使用360安全浏览器的极速模式或IE9.0以上版本的浏览器");
	}
</script>
<script>
	var _hmt = _hmt || [];
	(function () {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?8bb8dc6fb1864361ecbca12c39cd1c68";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>
<script type="text/javascript">
	$(function () {
		//删除cookie
		$.cookie('token', null, {'path': '/'});

		$('#password').keyup(function (event) {
			if (event.keyCode == "13") {
				$("#login").trigger("click");
				return false;
			}
		});

		$('#code').keyup(function (event) {
			if (event.keyCode == "13") {
				$("#login").trigger("click");
				return false;
			}
		});

		$("#login").on("click", function () {
			submitForm();
		});

		function submitForm() {
			if (navigator.appName == "Microsoft Internet Explorer" &&
					(navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE6.0" ||
							navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE7.0" ||
							navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE8.0")
			) {
				alert("您的浏览器版本过低，请使用360安全浏览器的极速模式或IE9.0以上版本的浏览器");
			} else {
				var formData = {
					userNameId: $('#userNameId').val(),
					password: $('#password').val(),
					code: $('#code').val()
				};
				$.ajax({
					type: 'POST',
					url: '/system/jwtLogin',
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify(formData),
					//data: formData,
					success: function (data) {
						if (data.statusCode == 200) {
							location.href = '/system/index/index';
							//location.href = data.referer;
							//将token存储到本地

						} else {
							//location.href = '/system/index/index';
							$("#alertMsg").html(data.message);
							$('#myModal').modal();
							//alert("用户名或密码错误！");
						}
					},
					error: function () {

					}
				});
			}
		}

		$("#reset").on("click", function () {
			$("#userNameId").val("");
			$("#password").val("");
		});
	});
</script>

<script>
	var codes = "8335";
	var code
	var codeList = []

	for (var i in codes) {
		codeList.push(codes[i])
	}

	drawPic()

	//生成随机数
	function randomNum(min, max) {
		return Math.floor(Math.random() * (max - min) + min);
	}

	//生成随机颜色RGB分量
	function randomColor(min, max) {
		var _r = randomNum(min, max);
		var _g = randomNum(min, max);
		var _b = randomNum(min, max);
		return "rgb(" + _r + "," + _g + "," + _b + ")";
	}

	//先阻止画布默认点击发生的行为再执行drawPic()方法
	document.getElementById("canvas").onclick = function (e) {
		e.preventDefault();
		AgainCode();
	};

	function drawPic() {
		//获取到元素canvas
		var $canvas = document.getElementById("canvas");
		var _picTxt = "";//随机数
		var _num = 4;//4个随机数字
		var _width = $canvas.width;
		var _height = $canvas.height;
		var ctx = $canvas.getContext("2d");//获取 context 对象
		ctx.textBaseline = "bottom";//文字上下对齐方式--底部对齐
		ctx.fillStyle = '#fff';//填充画布颜色
		ctx.fillRect(0, 0, _width, _height);//填充矩形--画画
		for (var i = 0; i < _num; i++) {
			var x = (_width - 10) / _num * i + 10;
			var y = randomNum(_height / 1.5, _height);
			var deg = randomNum(-15, 15);
			var txt = codeList[i];
			_picTxt += codeList[i];//获取一个随机数
			ctx.fillStyle = '#000';//填充随机颜色
			ctx.font = randomNum(24, 28) + "px SimHei";//设置随机数大小，字体为SimHei
			ctx.translate(x, y);//将当前xy坐标作为原始坐标
			ctx.rotate(deg * Math.PI / 180);//旋转随机角度
			ctx.fillText(txt, 0, 0);//绘制填色的文本
			ctx.rotate(-deg * Math.PI / 180);
			ctx.translate(-x, -y);
		}

		// for (var i = 0; i < _num * 10; i++) {
		//     ctx.fillStyle = randomColor(0, 255);
		//     ctx.beginPath();
		//     //随机画原，填充颜色
		//     ctx.arc(randomNum(0, _width), randomNum(0, _height), 1, 0, 2 * Math.PI);
		//     ctx.fill();
		// }
		return _picTxt;//返回随机数字符串
	}


	//刷新验证码
	function AgainCode() {
		$.ajax({
			url: '/system/genCodes',
			data: {},
			dataType: 'TEXT',
			type: 'POST',
			async: false,
			success: function (res) {
				code = []
				code = res.replace(/\s*/g, "");
				codeList = []
				for (var i in code) {
					codeList.push(code[i])
				}

				if (myBrowser() || myBrowser() == "IE8") {
					$('#ttt').html(newCode)
				} else {
					drawPic()
				}
			}

		});

	}

	function myBrowser() {
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
		var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
		var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
		var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
		if (isIE) {
			var IE5 = IE55 = IE6 = IE7 = IE8 = false;
			var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
			reIE.test(userAgent);
			var fIEVersion = parseFloat(RegExp["$1"]);
			IE55 = fIEVersion == 5.5;
			IE6 = fIEVersion == 6.0;
			IE7 = fIEVersion == 7.0;
			IE8 = fIEVersion == 8.0;
			if (IE55) {
				return "IE55";
			}
			if (IE6) {
				return "IE6";
			}
			if (IE7) {
				return "IE7";
			}
			if (IE8) {
				return "IE8";
			}
		}//isIE end
		if (isFF) {
			return "FF";
		}
		if (isOpera) {
			return "Opera";
		}
	}//myBrowser() end
	//以下是调用上面的函数

	if (myBrowser() == "IE8") {
		$("#canvas").css('display', 'none')
		$.post('validateCode.php', '', function (newCode) {
			code = newCode.replace(/\s*/g, "");
			codeList = []
			for (var i in code) {
				codeList.push(code[i])
			}
			$('#ttt').html(newCode)
		})
	}

	//判断是否显示验证码
	function hide() {
		var codeState = getCookie('codeState');
		if (codeState == 'true') {
			//是true就显示验证码
			$("#canvas").css('display', 'block')
			$('#ttt').css('display', 'block')
			$('#code').css('display', 'block')
		}
	}

	//取指定cookie数据
	function getCookie(cookieName) {
		var strCookie = document.cookie;
		var arrCookie = strCookie.split("; ");
		for (var i = 0; i < arrCookie.length; i++) {
			var arr = arrCookie[i].split("=");
			if (cookieName == arr[0]) {
				return arr[1];
			}
		}
		return "";
	}


</script>

<!--钉钉扫码登录-->
<script src="https://g.alicdn.com/dingding/dinglogin/0.0.5/ddLogin.js"></script>
<script>

	var redirectUri = "http://demo.misboot.com/system/dingding/ddLogin";
	var appid = "dingoayss4hkqc2dygm554";


	//解释一下goto参数，参考以下例子：
	var url = encodeURIComponent(redirectUri + '?type=login');
	var goto = encodeURIComponent('https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=' + appid + '&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=' + url)
	console.log(url)
	var obj = DDLogin({
		id: "login_container",//这里需要你在自己的页面定义一个HTML标签并设置id，例如<div id="login_container"></div>或<span id="login_container"></span>
		goto: goto, //请参考注释里的方式
		style: "border:none;background-color:#fff;",
		width: "315",
		height: "345"
	});
	var handleMessage = function (event) {
		var origin = event.origin;
		if (origin == "https://login.dingtalk.com") { //判断是否来自ddLogin扫码事件。
			var loginTmpCode = event.data;

			//拿到loginTmpCode后就可以在这里构造跳转链接进行跳转了
			window.location.href =
					"https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=" + appid + "&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=" + redirectUri + "&loginTmpCode=" +
					loginTmpCode;
		}
	};
	if (typeof window.addEventListener != 'undefined') {
		window.addEventListener('message', handleMessage, false);
	} else if (typeof window.attachEvent != 'undefined') {
		window.attachEvent('onmessage', handleMessage);
	}


</script>
<script>

	//获取style样式的css属性,考虑兼容;ie,火狐/谷歌;
	function getStyle(parm1, parm2) {
		return parm1.currentStyle ? parm1.currentStyle[parm2] : getComputedStyle(parm1)[parm2];  //parm1,要改变的元素代替名;parm2,要改变的属性名
	}

	$(".tab .tab-item").off("click").on("click", function () {
		var index = $(this).index();
		//改变钉钉二维码高度
		if (1 == index) {
			document.getElementById("myDiv").style.height = 370 + "px";
		} else {
			document.getElementById("myDiv").style.height = 300 + "px";
		}
		$(this).addClass("on").siblings().removeClass("on");
		$(".content-box .form").eq(index).addClass("active").siblings().removeClass("active");
		if (index == 2) {
			weixinLogin();
		}
	});

	//微信登录
	function weixinLogin() {
		$.ajax({
			url: '/system/weixin/weixinUrl',
			type: 'POST',
			success: function (res) {
				window.location.href = res;
			}
		});

	}
</script>
<script>
	$(function () {
		var isExistence = 'no';
		if (isExistence == 'yes') {
			$("#alertMsg").html("没有绑定账号！");
			$('#myModal').modal();

			$('.dingding-tab').addClass("on").siblings().removeClass("on");
			$(".content-box .dingding").addClass("active").siblings().removeClass("active");
		}
	})
</script>
<script>
	//进入界面隐藏忘记密码form表单
	document.getElementById('retrievePassword').style.display = 'none';
	//点击忘记密码隐藏登录form表单显示忘记密码表单
	$("#forgetThePassword").on("click", function () {
		document.getElementById('modifyPassword').style.display = 'none';
		document.getElementById('retrievePassword').style.display = '';
		$('.forget').html('找回密码')
	});
	//点击返回按钮显示登录form表单
	//点击忘记密码隐藏登录form表单显示忘记密码表单
	$("#goBack").on("click", function () {
		document.getElementById('retrievePassword').style.display = 'none';
		document.getElementById('modifyPassword').style.display = '';
		$('.forget').html('账号密码登录')
	});


	//找回密码
	$("#modifies").on("click", function () {
		//获取手机号
		var userNameId = $("#lookForUserNameId").val();
		var email = $("#email").val();
		if (userNameId == '' || email == ''){
			$("#alertMsg").html("请输入账号跟邮箱！");
			$('#myModal').modal();
			setTimeout(function () {
				$('#myModal').modal('hide')
			}, 1500)
			return;
		}
		var data = {
			userNameId: userNameId,
			email: email
		};
		document.getElementById('load').style.display = 'block';
		$.ajax({
			type: 'POST',
			url: '/mdata/user/ResetPassword',
			data: data,
			success: function (data) {
				document.getElementById('load').style.display = 'none';
				if (data.statusCode == 200) {
					document.getElementById('retrievePassword').style.display = 'none';
					document.getElementById('modifyPassword').style.display = '';
					$("#alertMsgSuccess").html(data.message);
					$('#myModalSuccess').modal();
					setTimeout(function () {
						$('#myModalSuccess').modal('hide')
					}, 2000)
				} else {
					$("#alertMsg").html(data.message);
					$('#myModal').modal();
					setTimeout(function () {
						$('#myModal').modal('hide')
					}, 1500)
				}
			}
		});
	});

</script>
</body>
</html>
