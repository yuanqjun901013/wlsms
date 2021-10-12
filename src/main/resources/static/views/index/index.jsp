<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>系统平台</title>
	<link rel="stylesheet" type="text/css" href="../../themes/material-teal/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../../demo/demo.css">
	<script type="text/javascript" src="../../jquery.min.js"></script>
	<script type="text/javascript" src="../../jquery.easyui.min.js"></script>
	<SCRIPT th:inline="javascript">
		var oInterval = "";
		// var welcomeValue = [[${welcomeValue}]]; //判断是否需要欢迎页
		var menuLevel = [[${menuLevel}]];//默认加载parentId为1的菜单列表
		$(function(){
			// if(welcomeValue == "1"){//需要欢迎页面
			//欢迎页
			// $("#mWind").window("open");
			// oInterval = setInterval(CountDown, 1000);
			// }else {//不需要
			// 	$("#mWind").window('close');
			// }
			$("#userInfo").window("close");
			$("#pwdW").window("close");
			//首页默认选项卡
			$("#myTabs").tabs("add",{
				title: '首页',
				iconCls:'icon-tip',
				closable:false,
				content:'<iframe frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="middlePage" id="mainBody" style="vertical-align:bottom;" width="100%" height="100%"></iframe>'
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
			//关闭左边的选项卡
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
						content:'<iframe id="'+obj.id+'" frameborder="0" style="vertical-align:bottom;" width="100%" height="100%" border="0" marginwidth="1" SCROLLING="auto"></iframe>'
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

		// var index = 0;
		// function addTab(node){
		// 	index++;
		// 	$('#tt').tabs('add',{
		// 		title: 'Tab'+index,
		// 		content: '<div style="padding:10px">Content'+index+'</div>',
		// 		closable: true
		// 	});
		// }

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

		function editPwd(){//修改个人密码
			$("#pwdW").window("open");
		}
		function getUserInfo() {//打开个人资料
			$("#userInfo").window("open");
		}
	</SCRIPT>
	<style type="text/css">
		body{margin:0; padding:0;}
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
					系统平台
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
<div data-options="region:'west',split:true,collapsed:false,hideCollapsedContent:false" title="菜单栏" style="width:170px;">
	<div id="layout_west_accordion" class="easyui-accordion" data-options="fit:true,border:false,nimate:true,lines:true">
	</div>
</div>

<!--/*@thymesVar id="main" type=""*/-->
<!--<div data-options="region:'center'" th:title="${main}">-->
<div data-options="region:'center'">
	<div id="myTabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
		<div id="mmTab" class="easyui-menu" style="width:120px;">
			<div id="closeAll" data-options="iconCls:'icon-cancel'">关闭全部</div>
			<div id="closeOthers" data-options="iconCls:'icon-no'">关闭其他</div>
			<div id="closeCurrent" data-options="iconCls:'icon-no'">关闭当前</div>
			<div id="closeRight" data-options="iconCls:'icon-right'">右侧全部关闭</div>
			<div id="closeLeft" data-options="iconCls:'icon-left'">左侧全部关闭</div>
		</div>
	</div>
 <!-- <iframe width="100%" height="99%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="middlePage" id="bodyIfm"></iframe> -->
</div>

</div>
<div data-options="region:'east',title:'任务状态栏',split:true,collapsed:false,hideCollapsedContent:false" style="width:190px;">
<iframe style="vertical-align:bottom;" width="100%" height="100%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="messagePage" id="messageIfm"></iframe>
</div>
<div data-options="region:'south'" style="height:40px;background:#eee;">
	<table width=100%>
		<tr height =50%>
			<td></td>
		</tr>
		<tr height =50%>
			<td align="middle">
				<span style="font-family:arial;">&copy;</span>
				<span style="font-size:12px;color: #000000;"><strong>2020-<label id="logInfo"/></strong></span>&nbsp;
				<span style="font-size:12px;color: #000000;">&nbsp;<strong>版权所有</strong></span>
			</td>
		</tr>
	</table>
</div>
<!--
<div id="mWind" class="easyui-window" title=""
	 data-options="modal:true,resizable:false,collapsible:false,minimizable:false,maximizable:false,closable:false"
	 style="width:831px;height:600px;padding:10px;background-image: url('../../views/bg_welcome.png')">
		<div data-options="region:'center'">
			<span style="font-size:15px;color: #e2e2e2;">欢迎页将在&nbsp;<input style="font-size:18px;color: #000000;" type="button" id="number" value="5" disabled="disabled"/>&nbsp;秒后自动关闭</span>
		</div>
</div>
-->
<div id="userInfo" class="easyui-window" title="个人资料" style="width:800px;height:380px;padding:10px;"
	 data-options="iconCls:'icon-man',modal:true,resizable:false,minimizable:false,maximizable:false">
	<iframe style="vertical-align:bottom;" width="100%" height="100%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="userModalPage"></iframe>
</div>
<div id="pwdW" class="easyui-window" title="修改密码" style="width:580px;height:400px;padding:10px;"
	 data-options="iconCls:'icon-lock',modal:true,resizable:false,minimizable:false,maximizable:false">
	<iframe style="vertical-align:bottom;" width="100%" height="100%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="pwdModalPage"></iframe>
</div>
</body>
</html>