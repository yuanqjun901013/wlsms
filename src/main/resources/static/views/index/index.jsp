<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>WLSMS测试预览平台</title>
	<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../../demo/demo.css">
	<script type="text/javascript" src="../../jquery.min.js"></script>
	<script type="text/javascript" src="../../jquery.easyui.min.js"></script>

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
		<div data-options="iconCls:'icon-man'" onclick="logout();">个人资料</div>
		<div data-options="iconCls:'icon-edit'" onclick="logout();">修改密码</div>
	</div>
	<SCRIPT th:inline="javascript">
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
			// $.messager.alert("消息提醒", "菜单主ID为:" + parentId, "warning")；
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
									addTab(node);
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
			window.location.href = "/user/logout";
		}
	</SCRIPT>
</div>
<div data-options="region:'west',split:true,collapsed:false,hideCollapsedContent:false" title="菜单栏" style="width:207px;">
	<div id="layout_west_accordion" class="easyui-accordion" data-options="fit:true,border:false,nimate:true,lines:true">
	</div>
	<script th:inline="javascript">
		var menuLevel = [[${menuLevel}]];//默认加载parentId为1的菜单列表
		$(function() {
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
							addTab(node);
						}
					}
				});
			});
		});

		var menuList = [[${menuList}]]; //用户权限下菜单树
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

</div>
<div data-options="region:'east',title:'消息栏',split:true,collapsed:true,hideCollapsedContent:false" style="width:207px;">
</div>
<div data-options="region:'south',split:true" style="height:50px;background:#eee;">
</div>
</body>
</html>