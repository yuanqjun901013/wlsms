<%@page isELIgnored="false" contentType="text/html;charset=utf-8" %>
<div class="easyui-panel" title="" style="width:100%">
<table align="left" width="inherit" style="font-size:12px">
		<tr>
     		<td align="left" width="100%">
     			每页显示条数:
     			<select name="pageSizeSetParam" style="font-size:12px" id="pageSizeSetParam" onChange="pageSizeChange(this.value);">
     				<option value="10">10</option>
     				<option value="20">20</option>
     				<option value="50">50</option>
     			</select>&nbsp;&nbsp;
     			<script>
     				setSelectVal("pageSizeSetParam","${pageSize}");
     			</script>
	     		<a href="javascript:void(0)" onclick="toPage(-1);">上一页</a>&nbsp;
	  			当前第${pageNo}页 &nbsp;
	  			<a href="javascript:void(0)" onclick="toPage(1);">下一页</a>&nbsp;
	  			共${totalRecords}条记录,共${totalPages}页
    		</td>
   		</tr>
	</table>
	</div>
	<script>
		function toPage(offset)
		{
			var pageNo = parseInt("${pageNo}")+offset;
			var totalPages = parseInt("${totalPages}");
			if(pageNo < 1) 
			{	
				alert("已是第一页");
				return;
			}
			if(pageNo > totalPages)
			{
				alert("已是最后一页");
				return;
			}
			document.queryForm.pageSize.value = document.getElementById("pageSizeSetParam").value;
			document.queryForm.pageNo.value = pageNo;
			document.queryForm.submit();
		}
		function pageSizeChange(pageSize)
		{
			document.queryForm.pageSize.value = pageSize;
			document.queryForm.pageNo.value = 1;
			document.queryForm.submit();
		}
	</script>