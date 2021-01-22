<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>卫星智能处理系统</title>
	<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../../demo/demo.css">
	<script type="text/javascript" src="../../jquery.min.js"></script>
	<script type="text/javascript" src="../../jquery.easyui.min.js"></script>

</head>
<body class="easyui-layout">
<div class="easyui-panel" data-options="region:'north'" style="height:74px; background-image: url('../../bg.png');">
	<table border="0" style="width: 98%">
		<tr><!-- 其中m是个临时变量，像for(User u : userList)那样中的u-->
			<td align="left" width="200px">
				<a class="easyui-linkbutton" style="width:200px;height: 98%;" data-options="iconCls:'icon-logo',size:'large',plain:true" onclick="reload();">
					卫星智能处理系统
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
		<div data-options="iconCls:'icon-man'" onclick="logout();">个人资料</div>
		<div data-options="iconCls:'icon-edit'" onclick="logout();">修改密码</div>
	</div>
	<SCRIPT th:inline="javascript">
		function reload(){//刷新页面
			window.location.href = "/index/main";
		}

		function getMenuLevel(id) {//点击主菜单加载子菜单栏
			var parentId = id;
			// $.messager.alert("消息提醒", "菜单主ID为:" + parentId, "warning")；

		}
		//注销登录
		function logout() {
			window.location.href = "/user/logout";
		}
	</SCRIPT>

</div>
<div data-options="region:'west',split:true,collapsed:false,hideCollapsedContent:false" title="菜单栏" style="width:207px;">
	<div id="mn" class="easyui-accordion" data-options="fit:true,border:false">
		<!--
		<div title="Title1" data-options="selected:true" style="padding:10px;">
			content1
		</div>
		<div title="Title2"  style="padding:10px;">
			content2
		</div>
		<div title="Title3" style="padding:10px">
			content3
		</div>-->
	</div>
	<script th:inline="javascript">
		var menuList = [[${menuList}]];
		function getSelected(obj){
			if(!/^\s*$/.test(obj))
			{
				document.getElementById("bodyIfm").src = "forwardToPage?url="+ obj.url + "&text=" + obj.text + "&menu=" + obj.menu;
			}
		}
	</script>
</div>

<!--/*@thymesVar id="main" type=""*/-->
<div data-options="region:'center',iconCls:'icon-ok'" th:title="${main}">
	<iframe width="100%" height="99%"  frameborder="no" border="0" marginwidth="1" SCROLLING="auto" src="middlePage" id="bodyIfm">
	</iframe>
</div>
<div data-options="region:'east',title:'消息栏',split:true,collapsed:true,hideCollapsedContent:false" style="width:207px;">
</div>
<div data-options="region:'south',split:true" style="height:50px;background:#eee;">
</div>
</body>
</html>