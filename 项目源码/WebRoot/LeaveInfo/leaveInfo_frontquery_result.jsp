<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LeaveInfo" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ page import="com.chengxusheji.po.LeaveType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<LeaveInfo> leaveInfoList = (List<LeaveInfo>)request.getAttribute("leaveInfoList");
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    //获取所有的leaveTypeObj信息
    List<LeaveType> leaveTypeList = (List<LeaveType>)request.getAttribute("leaveTypeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    LeaveType leaveTypeObj = (LeaveType)request.getAttribute("leaveTypeObj");
    String leaveTitle = (String)request.getAttribute("leaveTitle"); //请假标题查询关键字
    Employee employeeObj = (Employee)request.getAttribute("employeeObj");
    String addTime = (String)request.getAttribute("addTime"); //提交时间查询关键字
    String shenHeState = (String)request.getAttribute("shenHeState"); //审核状态查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>请假查询</title>
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
			    	<li role="presentation" class="active"><a href="#leaveInfoListPanel" aria-controls="leaveInfoListPanel" role="tab" data-toggle="tab">请假列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>LeaveInfo/leaveInfo_frontAdd.jsp" style="display:none;">添加请假</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="leaveInfoListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>请假类型</td><td>请假标题</td><td>请假时长</td><td>请假员工</td><td>提交时间</td><td>审核状态</td><td>负责人回复</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<leaveInfoList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		LeaveInfo leaveInfo = leaveInfoList.get(i); //获取到请假对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=leaveInfo.getLeaveTypeObj().getLeaveTypeName() %></td>
 											<td><%=leaveInfo.getLeaveTitle() %></td>
 											<td><%=leaveInfo.getDays() %></td>
 											<td><%=leaveInfo.getEmployeeObj().getName() %></td>
 											<td><%=leaveInfo.getAddTime() %></td>
 											<td><%=leaveInfo.getShenHeState() %></td>
 											<td><%=leaveInfo.getReplyContent() %></td>
 											<td>
 												<a href="<%=basePath  %>LeaveInfo/<%=leaveInfo.getLeaveId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="leaveInfoEdit('<%=leaveInfo.getLeaveId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="leaveInfoDelete('<%=leaveInfo.getLeaveId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>请假查询</h1>
		</div>
		<form name="leaveInfoQueryForm" id="leaveInfoQueryForm" action="<%=basePath %>LeaveInfo/frontlist" class="mar_t15">
            <div class="form-group">
            	<label for="leaveTypeObj_leaveTypeId">请假类型：</label>
                <select id="leaveTypeObj_leaveTypeId" name="leaveTypeObj.leaveTypeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(LeaveType leaveTypeTemp:leaveTypeList) {
	 					String selected = "";
 					if(leaveTypeObj!=null && leaveTypeObj.getLeaveTypeId()!=null && leaveTypeObj.getLeaveTypeId().intValue()==leaveTypeTemp.getLeaveTypeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=leaveTypeTemp.getLeaveTypeId() %>" <%=selected %>><%=leaveTypeTemp.getLeaveTypeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="leaveTitle">请假标题:</label>
				<input type="text" id="leaveTitle" name="leaveTitle" value="<%=leaveTitle %>" class="form-control" placeholder="请输入请假标题">
			</div>






            <div class="form-group">
            	<label for="employeeObj_employeeNo">请假员工：</label>
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
				<label for="addTime">提交时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择提交时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="shenHeState">审核状态:</label>
				<input type="text" id="shenHeState" name="shenHeState" value="<%=shenHeState %>" class="form-control" placeholder="请输入审核状态">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="leaveInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;请假信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="leaveInfoEditForm" id="leaveInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="leaveInfo_leaveId_edit" class="col-md-3 text-right">请假id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="leaveInfo_leaveId_edit" name="leaveInfo.leaveId" class="form-control" placeholder="请输入请假id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="leaveInfo_leaveTypeObj_leaveTypeId_edit" class="col-md-3 text-right">请假类型:</label>
		  	 <div class="col-md-9">
			    <select id="leaveInfo_leaveTypeObj_leaveTypeId_edit" name="leaveInfo.leaveTypeObj.leaveTypeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_leaveTitle_edit" class="col-md-3 text-right">请假标题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_leaveTitle_edit" name="leaveInfo.leaveTitle" class="form-control" placeholder="请输入请假标题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_leaveContent_edit" class="col-md-3 text-right">请假内容:</label>
		  	 <div class="col-md-9">
			    <textarea id="leaveInfo_leaveContent_edit" name="leaveInfo.leaveContent" rows="8" class="form-control" placeholder="请输入请假内容"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_days_edit" class="col-md-3 text-right">请假时长:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_days_edit" name="leaveInfo.days" class="form-control" placeholder="请输入请假时长">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_employeeObj_employeeNo_edit" class="col-md-3 text-right">请假员工:</label>
		  	 <div class="col-md-9">
			    <select id="leaveInfo_employeeObj_employeeNo_edit" name="leaveInfo.employeeObj.employeeNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_addTime_edit" class="col-md-3 text-right">提交时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date leaveInfo_addTime_edit col-md-12" data-link-field="leaveInfo_addTime_edit">
                    <input class="form-control" id="leaveInfo_addTime_edit" name="leaveInfo.addTime" size="16" type="text" value="" placeholder="请选择提交时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_shenHeState_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_shenHeState_edit" name="leaveInfo.shenHeState" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_replyContent_edit" class="col-md-3 text-right">负责人回复:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_replyContent_edit" name="leaveInfo.replyContent" class="form-control" placeholder="请输入负责人回复">
			 </div>
		  </div>
		</form> 
	    <style>#leaveInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxLeaveInfoModify();">提交</button>
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
    document.leaveInfoQueryForm.currentPage.value = currentPage;
    document.leaveInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.leaveInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.leaveInfoQueryForm.currentPage.value = pageValue;
    documentleaveInfoQueryForm.submit();
}

/*弹出修改请假界面并初始化数据*/
function leaveInfoEdit(leaveId) {
	$.ajax({
		url :  basePath + "LeaveInfo/" + leaveId + "/update",
		type : "get",
		dataType: "json",
		success : function (leaveInfo, response, status) {
			if (leaveInfo) {
				$("#leaveInfo_leaveId_edit").val(leaveInfo.leaveId);
				$.ajax({
					url: basePath + "LeaveType/listAll",
					type: "get",
					success: function(leaveTypes,response,status) { 
						$("#leaveInfo_leaveTypeObj_leaveTypeId_edit").empty();
						var html="";
		        		$(leaveTypes).each(function(i,leaveType){
		        			html += "<option value='" + leaveType.leaveTypeId + "'>" + leaveType.leaveTypeName + "</option>";
		        		});
		        		$("#leaveInfo_leaveTypeObj_leaveTypeId_edit").html(html);
		        		$("#leaveInfo_leaveTypeObj_leaveTypeId_edit").val(leaveInfo.leaveTypeObjPri);
					}
				});
				$("#leaveInfo_leaveTitle_edit").val(leaveInfo.leaveTitle);
				$("#leaveInfo_leaveContent_edit").val(leaveInfo.leaveContent);
				$("#leaveInfo_days_edit").val(leaveInfo.days);
				$.ajax({
					url: basePath + "Employee/listAll",
					type: "get",
					success: function(employees,response,status) { 
						$("#leaveInfo_employeeObj_employeeNo_edit").empty();
						var html="";
		        		$(employees).each(function(i,employee){
		        			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
		        		});
		        		$("#leaveInfo_employeeObj_employeeNo_edit").html(html);
		        		$("#leaveInfo_employeeObj_employeeNo_edit").val(leaveInfo.employeeObjPri);
					}
				});
				$("#leaveInfo_addTime_edit").val(leaveInfo.addTime);
				$("#leaveInfo_shenHeState_edit").val(leaveInfo.shenHeState);
				$("#leaveInfo_replyContent_edit").val(leaveInfo.replyContent);
				$('#leaveInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除请假信息*/
function leaveInfoDelete(leaveId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "LeaveInfo/deletes",
			data : {
				leaveIds : leaveId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#leaveInfoQueryForm").submit();
					//location.href= basePath + "LeaveInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交请假信息表单给服务器端修改*/
function ajaxLeaveInfoModify() {
	$.ajax({
		url :  basePath + "LeaveInfo/" + $("#leaveInfo_leaveId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#leaveInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#leaveInfoQueryForm").submit();
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

    /*提交时间组件*/
    $('.leaveInfo_addTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
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

