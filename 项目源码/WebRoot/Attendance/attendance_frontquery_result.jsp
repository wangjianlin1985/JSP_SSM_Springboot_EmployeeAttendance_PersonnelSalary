﻿<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Attendance" %>
<%@ page import="com.chengxusheji.po.AttendanceState" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Attendance> attendanceList = (List<Attendance>)request.getAttribute("attendanceList");
    //获取所有的attendanceState信息
    List<AttendanceState> attendanceStateList = (List<AttendanceState>)request.getAttribute("attendanceStateList");
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String attendanceDate = (String)request.getAttribute("attendanceDate"); //出勤日期查询关键字
    Employee employeeObj = (Employee)request.getAttribute("employeeObj");
    AttendanceState attendanceState = (AttendanceState)request.getAttribute("attendanceState");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>员工出勤查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#attendanceListPanel" aria-controls="attendanceListPanel" role="tab" data-toggle="tab">员工出勤列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Attendance/attendance_frontAdd.jsp" style="display:none;">添加员工出勤</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="attendanceListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>出勤日期</td><td>考勤员工</td><td>出勤结果</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<attendanceList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Attendance attendance = attendanceList.get(i); //获取到员工出勤对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=attendance.getAttendanceDate() %></td>
 											<td><%=attendance.getEmployeeObj().getName() %></td>
 											<td><%=attendance.getAttendanceState().getAttendanceStateName() %></td>
 											<td>
 												<a href="<%=basePath  %>Attendance/<%=attendance.getAttendanceId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="attendanceEdit('<%=attendance.getAttendanceId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="attendanceDelete('<%=attendance.getAttendanceId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>员工出勤查询</h1>
		</div>
		<form name="attendanceQueryForm" id="attendanceQueryForm" action="<%=basePath %>Attendance/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="attendanceDate">出勤日期:</label>
				<input type="text" id="attendanceDate" name="attendanceDate" class="form-control"  placeholder="请选择出勤日期" value="<%=attendanceDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <div class="form-group">
            	<label for="employeeObj_employeeNo">考勤员工：</label>
                <select id="employeeObj_employeeNo" name="employeeObj.employeeNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Employee employeeTemp:employeeList) {
	 					String selected = "";
 					if(employeeObj!=null && employeeObj.getEmployeeNo()!=null && employeeObj.getEmployeeNo().equals(employeeTemp.getEmployeeNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=employeeTemp.getEmployeeNo() %>" <%=selected %>><%=employeeTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="attendanceState_attendanceStateId">出勤结果：</label>
                <select id="attendanceState_attendanceStateId" name="attendanceState.attendanceStateId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(AttendanceState attendanceStateTemp:attendanceStateList) {
	 					String selected = "";
 					if(attendanceState!=null && attendanceState.getAttendanceStateId()!=null && attendanceState.getAttendanceStateId().intValue()==attendanceStateTemp.getAttendanceStateId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=attendanceStateTemp.getAttendanceStateId() %>" <%=selected %>><%=attendanceStateTemp.getAttendanceStateName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="attendanceEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;员工出勤信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="attendanceEditForm" id="attendanceEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="attendance_attendanceId_edit" class="col-md-3 text-right">出勤id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="attendance_attendanceId_edit" name="attendance.attendanceId" class="form-control" placeholder="请输入出勤id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="attendance_attendanceDate_edit" class="col-md-3 text-right">出勤日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date attendance_attendanceDate_edit col-md-12" data-link-field="attendance_attendanceDate_edit"  data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="attendance_attendanceDate_edit" name="attendance.attendanceDate" size="16" type="text" value="" placeholder="请选择出勤日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_employeeObj_employeeNo_edit" class="col-md-3 text-right">考勤员工:</label>
		  	 <div class="col-md-9">
			    <select id="attendance_employeeObj_employeeNo_edit" name="attendance.employeeObj.employeeNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_attendanceState_attendanceStateId_edit" class="col-md-3 text-right">出勤结果:</label>
		  	 <div class="col-md-9">
			    <select id="attendance_attendanceState_attendanceStateId_edit" name="attendance.attendanceState.attendanceStateId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_attendanceMemo_edit" class="col-md-3 text-right">备注信息:</label>
		  	 <div class="col-md-9">
			    <textarea id="attendance_attendanceMemo_edit" name="attendance.attendanceMemo" rows="8" class="form-control" placeholder="请输入备注信息"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#attendanceEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxAttendanceModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.attendanceQueryForm.currentPage.value = currentPage;
    document.attendanceQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.attendanceQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.attendanceQueryForm.currentPage.value = pageValue;
    documentattendanceQueryForm.submit();
}

/*弹出修改员工出勤界面并初始化数据*/
function attendanceEdit(attendanceId) {
	$.ajax({
		url :  basePath + "Attendance/" + attendanceId + "/update",
		type : "get",
		dataType: "json",
		success : function (attendance, response, status) {
			if (attendance) {
				$("#attendance_attendanceId_edit").val(attendance.attendanceId);
				$("#attendance_attendanceDate_edit").val(attendance.attendanceDate);
				$.ajax({
					url: basePath + "Employee/listAll",
					type: "get",
					success: function(employees,response,status) { 
						$("#attendance_employeeObj_employeeNo_edit").empty();
						var html="";
		        		$(employees).each(function(i,employee){
		        			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
		        		});
		        		$("#attendance_employeeObj_employeeNo_edit").html(html);
		        		$("#attendance_employeeObj_employeeNo_edit").val(attendance.employeeObjPri);
					}
				});
				$.ajax({
					url: basePath + "AttendanceState/listAll",
					type: "get",
					success: function(attendanceStates,response,status) { 
						$("#attendance_attendanceState_attendanceStateId_edit").empty();
						var html="";
		        		$(attendanceStates).each(function(i,attendanceState){
		        			html += "<option value='" + attendanceState.attendanceStateId + "'>" + attendanceState.attendanceStateName + "</option>";
		        		});
		        		$("#attendance_attendanceState_attendanceStateId_edit").html(html);
		        		$("#attendance_attendanceState_attendanceStateId_edit").val(attendance.attendanceStatePri);
					}
				});
				$("#attendance_attendanceMemo_edit").val(attendance.attendanceMemo);
				$('#attendanceEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除员工出勤信息*/
function attendanceDelete(attendanceId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Attendance/deletes",
			data : {
				attendanceIds : attendanceId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#attendanceQueryForm").submit();
					//location.href= basePath + "Attendance/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交员工出勤信息表单给服务器端修改*/
function ajaxAttendanceModify() {
	$.ajax({
		url :  basePath + "Attendance/" + $("#attendance_attendanceId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#attendanceEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#attendanceQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*出勤日期组件*/
    $('.attendance_attendanceDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

