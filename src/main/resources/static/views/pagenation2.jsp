<%@page isELIgnored="false" contentType="text/html;charset=utf-8" %>
<div class="easyui-panel" title="" style="width:100%">
<table align="left" width="inherit" style="font-size:12px">
		<tr>
     		<td align="left" width="100%">
     			每页显示条数:
     			<select name="pageSizeSetParam2" style="font-size:12px" id="pageSizeSetParam2" onChange="pageSizeChange2(this.value);">
     				<option value="10">10</option>
     				<option value="20">20</option>
     				<option value="50">50</option>
     			</select>&nbsp;&nbsp;
     			<script>
     				setSelectVal("pageSizeSetParam2","${pageSize2}");
     			</script>
	     		<a href="javascript:void(0)" onclick="toPage2(-1);">上一页</a>&nbsp;
	  			当前第${pageNo2}页 &nbsp;
	  			<a href="javascript:void(0)" onclick="toPage2(1);">下一页</a>&nbsp;
	  			共${totalRecords2}条记录,共${totalPages2}页
    		</td>
   		</tr>
	</table>
	</div>
	<script>
		function toPage2(offset)
		{
			var pageNo = parseInt("${pageNo2}")+offset;
			var totalPages = parseInt("${totalPages2}");
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
			document.queryForm.pageSize.value = document.getElementById("pageSizeSetParam2").value;
			document.queryForm.pageNo.value = pageNo;
			document.queryForm.submit();
		}
		function pageSizeChange2(pageSize2)
		{
			document.queryForm.pageSize.value = pageSize2;
			document.queryForm.pageNo.value = 1;
			document.queryForm.submit();
		}
	</script>