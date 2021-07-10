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
        $(function(){
            //屏蔽右键菜单
            $(document).bind("contextmenu",function(e){ return false; });
            docManager();
        })
    </SCRIPT>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDoc()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteDoc()">删除</a>
    <input class="easyui-textbox" id="queryBt" data-options="buttonText:'查询',buttonAlign:'left',buttonIcon:'icon-search',prompt:'输入关键字...'" style="width:200px;height:32px;">
    </div>
    <div id="docManager" data-options="region:'center',split:true"></div>
    <div id="dlg" class="easyui-dialog" style="width:500px; height: 360px"
         data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
        <form id="fm" method="post" action="/doc/doc/fileUpload" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:40px">
                <input id="docName" class="easyui-textbox" type="text" name="docName" data-options="required:true" label="资料名:" labelPosition="left" style="width:400px;">
            </div>
            <div style="margin-bottom:40px">
                <input id="file" name="file" class="easyui-filebox" label="文件:" labelPosition="left"  style="width:400px">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveDoc()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    <script type="text/javascript" th:inline="none">
        function docManager() {//展示列表
            var queryBt = $('#queryBt').textbox('getValue');
            $('#docManager').datagrid({
                url:'/doc/doc/docManager',//参数
                method: 'post',
                //携带参数
                queryParams: {
                    "request":queryBt
                },
                fitColumns:true,
                striped:true,
                pagination:true,
                rownumbers:true,
                singleSelect:true,
                checkbox:false,
                remoteFilter: true,
                clientPaging: false,
                nowrap:false,//自动换行
                toolbar:'#toolbar',
                columns:[[
                    {field:'id',title:'编号',width:20,align:'center'},
                    {field:'docName',title:'资料名称',width:60,align:'center'},
                    {field:'fileName',title:'文件名称',formatter:function(value,row,index)
                        {
                            return "<a href='/doc/doc/fileDownload/"+row.fileName+"'>"+row.fileName+"</a>";
                        },
                        width:100,align:'center'},
                    {field:'userName',title:'分享人',width:30,align:'center'},
                    {field:'userNo',title:'分享工号',width:30,align:'center'},
                    {field:'createTime',title:'上传时间',width:150,align:'center'}
                ]]
            });
        }


    //查询按钮
    $("#queryBt").textbox({onClickButton:function(){
      docManager();
    }})

        //添加资料信息
        function addDoc(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增资料');
            $('#fm').form('clear');
        }

        function saveDoc(){
            $('#fm').form('submit',{
                url: '/doc/doc/fileUpload',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if(result.success){
                        $.messager.alert("消息提醒", result.data, "info",function (){
                            $('#dlg').dialog('close');        // close the dialog
                            $('#docManager').datagrid('reload');    // reload the user data
                        });
                    }else {
                        $.messager.alert("消息提醒",result.msg);
                    }
                }
            });
        }
        function deleteDoc(){
            var row = $('#docManager').datagrid('getSelected');
            if (row){
                $.messager.confirm('确认提醒','确定删除该资料?',function(r){
                    if (r){
                        $.post('/doc/doc/deleteDoc',{id:row.id},function(result){
                            if (result.success){
                                $('#docManager').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.alert("消息提醒",result.msg);
                            }
                        },'json');
                    }
                });
            }
        }
    </script>
</div>
</body>
</html>