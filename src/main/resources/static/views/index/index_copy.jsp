<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>WLSMS测试预览平台</title>
	<link rel="stylesheet" type="text/css" href="../../themes/material-teal/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../../demo/demo.css">
	<script type="text/javascript" src="../../jquery.min.js"></script>
	<script type="text/javascript" src="../../jquery.easyui.min.js"></script>
	<SCRIPT th:inline="javascript">
		var oInterval = "";
		var welcomeValue = [[${welcomeValue}]]; //判断是否需要欢迎页
		var menuLevel = [[${menuLevel}]];//默认加载parentId为1的菜单列表
		$(function(){
			if(welcomeValue == "1"){//需要欢迎页面
			//欢迎页
			$("#mWind").window("open");
			oInterval = setInterval(CountDown, 1000);
			}else {//不需要
				$("#mWind").window('close');
			}
			$("#userInfo").window("close");
			$("#pwdW").window("close");
			//首页默认选项卡
			$("#myTabs").tabs("add",{
				title: '首页',
				iconCls:'icon-tip',
				closable:false,
				content:'<iframe frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="middlePage" id="mainBody" height="99%" width="100%" ></iframe>'
			});
			//屏蔽右键菜单
			$(document).bind("contextmenu",function(e){ return false; });

			$.each(menuLevel, function(i, n) { //加载父类节点即一级菜单
				var id = n.id;
				if(i == 0) { //显示第一个一级菜单下的二级菜单
					$('#layout_west_accordion').accordion('add', {
						title: n.text,
						iconCls: n.iconCls,
						selected: true,
						//可在这加HTML代码，改变布局
						content: '<div style="padding:10px 0px"><ul id="tree' + id + '"></ul></div>',
					});
				} else {
					$('#layout_west_accordion').accordion('add', {
						title: n.text,
						iconCls: n.iconCls,
						selected: false,
						content: '<div style="padding:10px 0px"><ul id="tree' + id + '"></ul></div>',
					});
				}
				//加载树
				$("#tree" + id).tree({
					data: n.children,
					animate: true,
					//iconCls: icon-blank,
					//在树节点加图片
					formatter:function(node){
						return node.text;
					},
					// lines: true, //显示虚线效果
					onClick: function(node) { // 在用户点击一个子节点即二级菜单时触发addTab()方法,用于添加tabs
						//if(node.url){//判断url是否存在，存在则创建tabs
						if(node) {
							addMenuTab(node);
						}
					}
				});
			});

			//绑定右键菜单事件
			$(".easyui-tabs").bind('contextmenu',function(e){
				e.preventDefault();
				$('#mmTab').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			});
			//关闭所有标签页
			$("#closeAll").bind("click",function(){
				var tablist =$('#myTabs').tabs('tabs');
				console.log(tablist);
				//  return;
				for(var i=tablist.length-1;i>=1;i--){
					$('#myTabs').tabs('close',i);
				}
			});

			//关闭选中的标签
			$("#closeCurrent").click(function(){
				//获取选中的标签索引
				var tab = $('#myTabs').tabs('getSelected');
				var index = $('#myTabs').tabs('getTabIndex',tab);
				$("#myTabs").tabs("close",index);
			});

			//关闭其他页面（先关闭右侧，再关闭左侧）
			$("#closeOthers").bind("click",function(){
				var tablist = $('#myTabs').tabs('tabs');
				var tab = $('#myTabs').tabs('getSelected');
				var index = $('#myTabs').tabs('getTabIndex',tab);
				for(var i=tablist.length-1;i>index;i--){
					$('#myTabs').tabs('close',i);
				}
				var num = index-1;
				if(num < 1){
					return;
				}else{
					for(var i=num;i>=1;i--){
						$('#myTabs').tabs('close',i);
					}
					$("#myTabs").tabs("select", 1);
				}
			});

			//关闭右边的选项卡
			$("#closeRight").bind("click",function(){
				var tablist = $('#myTabs').tabs('tabs');
				var tab = $('#myTabs').tabs('getSelected');
				var index = $('#myTabs').tabs('getTabIndex',tab);
				for(var i=tablist.length-1;i>index;i--){
					$('#myTabs').tabs('close',i);
				}
			});
			//关闭右边的选项卡
			$("#closeLeft").bind("click",function(){
				var tablist = $('#myTabs').tabs('tabs');
				var tab = $('#myTabs').tabs('getSelected');
				var index = $('#myTabs').tabs('getTabIndex',tab);
				var num = index-1;
				if(num < 1){
					return;
				}else{
					for(var i=num;i>=1;i--){
						$('#myTabs').tabs('close',i);
					}
					$("#myTabs").tabs("select", 1);
				}
			});
		});

		//	var menuList = [[${menuList}]]; //用户权限下菜单树
		function addMenuTab(obj){
			if(!/^\s*$/.test(obj))
			{
				var e = $("#myTabs").tabs("exists", obj.text);
				if(e){
					//已经存在，选中就可以
					$("#myTabs").tabs("select", obj.text);
				}else{
					//调用tabs对象的add方法动态添加一个选项卡
					$("#myTabs").tabs("add",{
						title: obj.text,
						iconCls:'icon-tip',
						closable:true,
						content:'<iframe id="'+obj.id+'" frameborder="0" height="99%" width="100%" ></iframe>'
					});
					document.getElementById(obj.id).src = "forwardToPage?url="+ obj.url + "&text=" + obj.text + "&menu=" + obj.menu;
				}
			}
		}

		function CountDown() {
			var count = $("#number").val();
			count--;
			if (count > 0) {
				$("#number").val(count);
			} else {
				clearInterval(oInterval);
				$("#mWind").window('close');

			};
		}


		function reload(){//刷新页面
			window.location.href = "/index/main";
		}

		var userInfo = [[${userInfo}]];//获取用户信息
		var userNameCode = [[${userNameCode}]];
		function getUserInfo(){//获取用户详细资料
			if(userInfo){
				$("#userInfo").window("open");
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
		}

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
						$("#userInfo").window("close");
						window.location.href = "/index/main";
					});
				}
			});
		}

		function getMenuLevel(id) {//点击主菜单加载子菜单栏
			var panels = $("#layout_west_accordion").accordion("panels");//获取旧菜单数量
			// alert(panels.length);
			var len = panels.length;
			var i = 0;
			while(i<len)
			{
				$('#layout_west_accordion').accordion("remove", 0);//清空旧菜单
				i++;
			}
			var parentId = id;
			// $.messager.alert("消息提醒", "菜单主ID为:" + parentId, "warning");
			$.ajax({
				type: 'POST',
				async: false,
				dataType: "json",
				url: '/index/menu/getMenuLevel',//获取菜单
				data:{
					"parentId":parentId
				},
				success: function(data) {
					$.each(data, function(i, n) { //加载父类节点即一级菜单
						var id = n.id;
						var text1 = n.text;
						if(i == 0) { //显示第一个一级菜单下的二级菜单
							$('#layout_west_accordion').accordion('add', {
								title: n.text,
								iconCls: n.iconCls,
								selected: true,
								//可在这加HTML代码，改变布局
								content: '<div style="padding:10px 0px"><ul id="tree' + id + '"></ul></div>',
							});
						} else {
							$('#layout_west_accordion').accordion('add', {
								title: n.text,
								iconCls: n.iconCls,
								selected: false,
								content: '<div style="padding:10px 0px"><ul id="tree' + id + '"></ul></div>',
							});
						}

						//加载树
						$("#tree" + id).tree({
							data: n.children,
							animate: true,
							//iconCls: icon-blank,
							//在树节点加图片
							formatter:function(node){
								return node.text;
							},
							// lines: true, //显示虚线效果
							onClick: function(node) { // 在用户点击一个子节点即二级菜单时触发addTab()方法,用于添加tabs
								//if(node.url){//判断url是否存在，存在则创建tabs
								if(node) {
									addMenuTab(node);
								}
							}
						});

					})
				}

			});
		}

		var index = 0;
		function addTab(node){
			index++;
			$('#tt').tabs('add',{
				title: 'Tab'+index,
				content: '<div style="padding:10px">Content'+index+'</div>',
				closable: true
			});
		}

		//注销登录
		function logout() {
			//提示用户是否确定退出
			$.messager.confirm("确认对话框", "你真的要退出吗？", function(r) {
				//退出
				if (r) {
					window.location.href = "/user/logout";
				}
			})
		}

		function getCurDate()
		{
			var d = new Date();
			var week;
			switch (d.getDay()){
				case 1: week="星期一"; break;
				case 2: week="星期二"; break;
				case 3: week="星期三"; break;
				case 4: week="星期四"; break;
				case 5: week="星期五"; break;
				case 6: week="星期六"; break;
				default: week="星期天";
			}
			var years = d.getFullYear();
			var month = add_zero(d.getMonth()+1);
			var days = add_zero(d.getDate());
			var hours = add_zero(d.getHours());
			var minutes = add_zero(d.getMinutes());
			var seconds=add_zero(d.getSeconds());
//        var nDate = years+"年"+month+"月"+days+"日 "+hours+":"+minutes+":"+seconds+" "+week;
			var nDate = years;
			var divT=document.getElementById("logInfo");
			divT.innerHTML= nDate;
		}
		function add_zero(temp)
		{
			if(temp<10) return "0"+temp;
			else return temp;
		}
		setInterval("getCurDate()",100);

		function editPwd(){
			$("#oldPass").textbox('setValue', '');
			$("#pass").textbox('setValue', '');
			$("#passCfm").textbox('setValue', '');
			$("#pwdW").window("open");
		}
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
						$("#pwdW").window("close");
						window.location.href = "/user/logout";
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
				message:'密码中必须包含字母（不区分大小写）、数字，至少6个字符，最多16个字符'
			}
		});
	</SCRIPT>
	<style type="text/css">
		/*#number{filter:Alpha(opacity=0.0);-moz-opacity:0.0;opacity:0.0;}*/
		#number{
			background-color:transparent;
			border-style:none;
		}

		/*修改标题超链接样式*/

		#n_title a {
			text-decoration: none;
			color: white;
		}

		#n_title a:hover {
			color: orange;
		}
		/*修改密码样式*/

		#div_pwd table {
			margin: auto;
			margin-top: 10px;
		}

		#div_pwd table tr {
			height: 40px;
			text-align: center;
		}

	</style>
</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'" style="height:74px; background-image: url('../../bg.png');">
	<table border="0" style="width: 99%;height: 69px">
		<tr><!-- 其中m是个临时变量，像for(User u : userList)那样中的u-->
			<td align="left" width="200px">
				<a class="easyui-linkbutton" style="width:200px;height: 98%;" data-options="iconCls:'icon-logo',size:'large',plain:true" onclick="reload();" >
					WLSMS测试预览平台
				</a>
			</td>
			<!--/*@thymesVar id="menusParentList" type=""*/-->
			<td th:each="mp : ${menusParentList}" align="left" width="69px">
				<!--/*@thymesVar id="iconCls" type=""*/-->
				<a class="easyui-linkbutton" style="width:68px" th:onclick="'javascript:getMenuLevel('+${mp.id}+')' " th:iconCls="${mp.iconCls}" data-options="size:'large',iconAlign:'top',group:'g1',toggle:true,plain:true">
					<!--/*@thymesVar id="text" type=""*/-->
					<span th:text="${mp.text}"/>
				</a>
			<td/>

			<td align="right" width="50%">
				<a style="width:200px" iconCls="icon-man" class="easyui-menubutton" data-options="menu:'#mm',plain:true">
					<!--/*@thymesVar id="userNameCode" type=""*/-->
					<span th:text="${userNameCode}"/>
				</a>
				|
				<a style="width:68px" iconCls="icon-tip" class="easyui-linkbutton" plain="true" onclick="logout();">退出</a>
			</td>
		</tr>
	</table>
	<div id="mm" style="width:100px;">
		<div data-options="iconCls:'icon-man'" onclick="getUserInfo();">个人资料</div>
		<div data-options="iconCls:'icon-edit'" onclick="editPwd();">修改密码</div>
	</div>
</div>
<div data-options="region:'west',split:true,collapsed:false,hideCollapsedContent:false" title="菜单栏" style="width:207px;">
	<div id="layout_west_accordion" class="easyui-accordion" data-options="fit:true,border:false,nimate:true,lines:true">
	</div>
</div>

<!--/*@thymesVar id="main" type=""*/-->
<div data-options="region:'center',iconCls:'icon-ok',
			 tools: [{
        		iconCls:'icon-full',
       			handler:function(){full()}
    		},{
        		iconCls:'icon-unfull',
       			handler:function(){unFull()}
    		}]" th:title="${main}">
	<div id="mmTab" class="easyui-menu" style="width:120px;">
		<div id="closeAll" data-options="iconCls:'icon-cancel'">关闭全部</div>
		<div id="closeOthers" data-options="iconCls:'icon-no'">关闭其他</div>
		<div id="closeCurrent" data-options="iconCls:'icon-no'">关闭当前</div>
		<div id="closeRight" data-options="iconCls:'icon-right'">右侧全部关闭</div>
		<div id="closeLeft" data-options="iconCls:'icon-left'">左侧全部关闭</div>
	</div>
	<div id="myTabs" class="easyui-tabs" data-options="fit:true"></div>
 <!-- <iframe width="100%" height="99%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="middlePage" id="bodyIfm"></iframe> -->
</div>

</div>
<div data-options="region:'east',title:'消息栏',split:true,collapsed:true,hideCollapsedContent:false" style="width:207px;">
</div>
<div data-options="region:'south',split:true" style="height:40px;background:#eee;">

	<table width=100%>
		<tr height =50%>
			<td></td>
		</tr>
		<tr height =50%>
			<td align="middle">
				<span style="font-family:arial;">&copy;</span>
				<span style="font-size:12px;color: #000000;"><strong>2020-<label id="logInfo"/></strong></span>&nbsp;
				<span style="font-size:12px;color: #000000;">WLSMS&nbsp;<strong>版权所有</strong></span>
			</td>
		</tr>
	</table>
</div>
<div id="mWind" class="easyui-window" title=""
	 data-options="modal:true,resizable:false,collapsible:false,minimizable:false,maximizable:false,closable:false"
	 style="width:1071px;height:831px;padding:10px;background-image: url('../../views/bg_welcome.png')">
		<div data-options="region:'center'">
			<span style="font-size:15px;color: #e2e2e2;">欢迎页将在&nbsp;<input style="font-size:18px;color: #000000;" type="button" id="number" value="5" disabled="disabled"/>&nbsp;秒后自动关闭</span>
		</div>
</div>
<div id="userInfo" class="easyui-window" title="个人资料" style="width:800px;height:350px;padding:10px;"
	 data-options="iconCls:'icon-man',modal:true,resizable:false,minimizable:false,maximizable:false,tools:'#tt'">
	<div style="margin-bottom:20px"></div>
	<div style="margin-bottom:20px">
		<input id="userName" class="easyui-textbox" label="姓名:" labelPosition="left" style="width:45%;">&nbsp;&nbsp;
		<input id="userNo" class="easyui-textbox" label="工号:" labelPosition="left" style="width:45%;">
	</div>
	<div style="margin-bottom:20px">
	<form id="ff">
		<input id="age" class="easyui-textbox" label="年龄:" labelPosition="left" style="width:45%;">&nbsp;&nbsp;
		性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		男<input id="sexM" class="easyui-radiobutton" name="sex" value="1">&nbsp;&nbsp;
		女<input id="sexW" class="easyui-radiobutton" name="sex" value="2">
	</form>
	</div>
	<div style="margin-bottom:20px">
		<input id="job" class="easyui-textbox" label="岗位:" labelPosition="left" style="width:45%;" >&nbsp;&nbsp;
		<input id="tel" class="easyui-textbox" label="固定电话:" labelPosition="left" style="width:45%;">
	</div>
	<div style="margin-bottom:20px">
		<input id="phone" class="easyui-textbox" label="手机:" labelPosition="left" style="width:45%;">&nbsp;&nbsp;
		<input id="email" class="easyui-textbox" label="Email:" labelPosition="left"  style="width:45%;">
	</div>
	<div id="editDiv" style="margin-bottom:20px" align="center">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="saveUser();" style="width:80px">保存</a>
	<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="cancelUser();" style="width:80px">取消</a>
	</div>
	</div>
<div id="tt">
	<a class="icon-edit" onclick="editUserView();"></a>
</div>
<div id="pwdW" class="easyui-window" title="修改密码" style="width:435px;height:330px;padding:10px;"
	 data-options="iconCls:'icon-lock',modal:true,resizable:false,minimizable:false,maximizable:false">
	<div style="margin-bottom:20px"></div>
	<div style="margin-bottom:20px">
		<input id="oldPass" class="easyui-passwordbox" prompt="输入旧密码" iconWidth="28" validType="confirmOldPass" style="width:100%;height:34px;padding:10px;">
	</div>
	<div style="margin-bottom:20px">
		<input id="pass" class="easyui-passwordbox" prompt="输入新密码" iconWidth="28" validType="confirmPwdLength" style="width:100%;height:34px;padding:10px">
	</div>
	<div style="margin-bottom:20px">
		<input id="passCfm" class="easyui-passwordbox" prompt="新密码验证" iconWidth="28" validType="confirmPass['#pass']" style="width:100%;height:34px;padding:10px">
	</div>
	<div style="margin-bottom:20px" align="center">
		<a id="pwdBt" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="confirmPwd();" style="width:80px">保存</a>
	</div>
</div>
</body>
</html>