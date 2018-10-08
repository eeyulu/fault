<!DOCTYPE>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>登录界面</title>
</head>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<body>
	<div style="overflow-y:scroll;width: 50%;height:70%;float:left;">
		<p style="text-align: center;color: red">成员 <p>
<!-- 		<input id="phone" name="phone" type="text" value=""> -->
<!-- 		<input type="button" value="查询" onclick="search()"> -->
		<table id="info_self" width="100%" border="1" bordercolor="#227799"
			cellspacing="0">
			<tr style="font-weight: bold;font-size: 16px;">
				<td><input type="button" name="allchecker" id="allchecker"
					style="background-color: #53aecc;color:#ADFF2F;height:25px;width: 100%;"
					value="全选" onclick="checkAll()"></td>
				<td style="background-color: #F0F8FF">序号</td>
				<td>账号</td>
				<td>姓名</td>
				<td>科室</td>
				<td>操作</td>
			</tr>
		</table>
	</div>
	<div style="overflow-y:scroll;width: 50%;height:70%;float:left;">
		<p style="text-align: center;color: red">科室
		<p>
		<table id="depart" width="100%" border="1" bordercolor="#227799"
			cellspacing="0">
			<tr style="font-weight: bold;font-size: 16px;">
				<td><input type="button" name="allchecker_1" id="allchecker_1"
					style="background-color: #53aecc;color:#ADFF2F;height:25px;width: 100%;"
					value="全选" onclick="checkAllDept()"></td>
				<td style="background-color: #F0F8FF">序号</td>
				<td>科室</td>
			</tr>

		</table>
	</div>
	<div
		style=" width:100%;margin-top: 25px;text-align: center;padding-top: 20px;float:left;">
		<input type="button" onclick="submit()" style="" value="提交" id="id"
			name="id" />
	</div>

</body>
<script type="text/javascript">

function delUser(id){
		$.ajax({
			type : "POST",
			url : "../bgm/delPacket?id="+id,
			dataType:"json",
			error : function() {
				alert("操作失败");
			},
			success : function(data) {
				alert('删除成功！');
			}
		});

}


$(function() {		
			var tsSize=#(tsData).length;				
			if(tsSize>0){				
				for(var i=0;i<tsSize;i++){
				$("#info_self").append("<tr>"+
					"<td><input type='checkbox' id='itemCheckbox' name='itemCheckbox' value='"+#(tsData)[i].id+"'></td>"+
					"<td style='background-color: #F0F8FF'>"+(i+1)+"</td>"+
					"<td style='background-color: #E0FFFF'>"+#(tsData)[i].username+"</td>"+
					"<td style='background-color: #E0FFFF'>"+#(tsData)[i].realname+"</td>"+
					"<td style='background-color: #E0FFFF'>"+#(tsData)[i].packetName+"</td>"+
					"<td><input type='button' onclick='delUser(\""+#(tsData)[i].id+"\")' value='删除科室及好友'></td>"+
					"</tr>");
				}
			}else{
				$("#info_self").append("暂无数据!");
			}
			
			var packetSize=#(packetData).length;				
			if(packetSize>0){				
				for(var i=0;i<packetSize;i++){
				$("#depart").append("<tr>"+
					"<td><input type='checkbox' id='itemCheckbox_1' name='itemCheckbox_1' value='"+#(packetData)[i].id+"'></td>"+
					"<td style='background-color: #F0F8FF'>"+(i+1)+"</td>"+
					"<td style='background-color: #E0FFFF'>"+#(packetData)[i].packetName+"</td>"+					
					"</tr>");
				}
			}else{
				$("#depart").append("暂无数据!");
			}

		$("input[name='itemCheckbox']").click(function() {
			var checkboxs = $("input[name='itemCheckbox']");
			var checked = $("input[name='itemCheckbox']:checked");
			if (checkboxs.length == checked.length) {
				$("#allchecker").val("取消");
			} else {
				$("#allchecker").val("全选");
			}
		});
		$("input[name='itemCheckbox_1']").click(function() {
			var checkboxs = $("input[name='itemCheckbox_1']");
			var checked = $("input[name='itemCheckbox_1']:checked");
			if (checkboxs.length == checked.length) {
				$("#allchecker").val("取消");
			} else {
				$("#allchecker").val("全选");
			}
		});
	})
	
function checkAll() {
	var checkboxs = $("input[name='itemCheckbox']");
	var checked = $("input[name='itemCheckbox']:checked");
	if (checkboxs.length != checked.length) {
		checkboxs.prop("checked", "checked");
		$("#allchecker").val("取消");
	} else {
		checkboxs.removeAttr("checked");
		$("#allchecker").val("全选");
	}
}
function checkAllDept() {
	var checkboxs = $("input[name='itemCheckbox_1']");
	var checked = $("input[name='itemCheckbox_1']:checked");
	if (checkboxs.length != checked.length) {
		checkboxs.prop("checked", "checked");
		$("#allchecker_1").val("取消");
	} else {
		checkboxs.removeAttr("checked");
		$("#allchecker_1").val("全选");
	}
}
function search(){
	var phone=$("#phone").val().trim();	
	 phone={phone:phone};
	 if(phone!=""){
		 	$.ajax({
			type : "POST",
			data :phone,
			url : "../bgm/searchPhone",
			dataType:"json",
			error : function() {
				alert("操作失败");
			},
			success : function(data) {
			$("#info_self").empty();
			var t=JSON.stringify(data.tsData);
				alert(JSON.stringify(data.tsData));
				$("#info_self").append("<tr>"+
					"<td><input type='checkbox' id='itemCheckbox' name='itemCheckbox' value='"+data.tsData[0].id+"'></td>"+
					"<td style='background-color: #F0F8FF'>"+(i+1)+"</td>"+
					"<td style='background-color: #E0FFFF'>"+data.tsData[0].username+"</td>"+
					"<td style='background-color: #E0FFFF'>"+data.tsData[0].realname+"</td>"+
					"<td style='background-color: #E0FFFF'>"+data.tsData[0].packetName+"</td>"+
					"</tr>");
			}
		});
	 }else{
	 	alert("请输入手机号");
	 }
	

}

function submit(){
//获取选中的值
var chk_info_value ='';
var chk_dept_value=''; 
$('input[name="itemCheckbox"]:checked').each(function(){ 
chk_info_value+=$(this).val()+","; 
}); 
$('input[name="itemCheckbox_1"]:checked').each(function(){ 
chk_dept_value+=$(this).val()+",";  
}); 
if(chk_info_value.length==0&&chk_dept_value.length==0){
	alert("请选择");
}else if(chk_info_value.length==0&&chk_dept_value.length>0){
	alert("你还没有选择任何人！");
}else if(chk_info_value.length>0&&chk_dept_value.length==0){
	alert("你还没有选择任何科室！");
}else{
	var id={infoId:chk_info_value,deptId:chk_dept_value};
	console.log(JSON.stringify(id));
	$.ajax({
		type : "POST",
		data :id,
		url : "../bgm/savePacket",
		dataType:"json",
		error : function() {
			alert("操作失败");
		},
		success : function(data) {
			
		}
	});
	
	alert("提交数据");
}

}




</script>
</html>


