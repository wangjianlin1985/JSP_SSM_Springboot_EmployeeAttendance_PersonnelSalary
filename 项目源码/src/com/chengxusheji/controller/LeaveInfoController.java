package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.LeaveInfoService;
import com.chengxusheji.po.LeaveInfo;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;
import com.chengxusheji.service.LeaveTypeService;
import com.chengxusheji.po.LeaveType;

//LeaveInfo管理控制层
@Controller
@RequestMapping("/LeaveInfo")
public class LeaveInfoController extends BaseController {

    /*业务层对象*/
    @Resource LeaveInfoService leaveInfoService;

    @Resource EmployeeService employeeService;
    @Resource LeaveTypeService leaveTypeService;
	@InitBinder("leaveTypeObj")
	public void initBinderleaveTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("leaveTypeObj.");
	}
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("leaveInfo")
	public void initBinderLeaveInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("leaveInfo.");
	}
	/*跳转到添加LeaveInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new LeaveInfo());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		/*查询所有的LeaveType信息*/
		List<LeaveType> leaveTypeList = leaveTypeService.queryAllLeaveType();
		request.setAttribute("leaveTypeList", leaveTypeList);
		return "LeaveInfo_add";
	}

	/*客户端ajax方式提交添加请假信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        leaveInfoService.addLeaveInfo(leaveInfo);
        message = "请假添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式提交添加请假信息*/
	@RequestMapping(value = "/empAdd", method = RequestMethod.POST)
	public void empAdd(LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		Employee employeeObj = new Employee(); 
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		leaveInfo.setEmployeeObj(employeeObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		leaveInfo.setAddTime(sdf.format(new java.util.Date()));
		
		leaveInfo.setShenHeState("待审核");
		
		leaveInfo.setReplyContent("--");
		
		
        leaveInfoService.addLeaveInfo(leaveInfo);
        message = "请假添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("leaveTypeObj") LeaveType leaveTypeObj,String leaveTitle,@ModelAttribute("employeeObj") Employee employeeObj,String addTime,String shenHeState,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (leaveTitle == null) leaveTitle = "";
		if (addTime == null) addTime = "";
		if (shenHeState == null) shenHeState = "";
		if(rows != 0)leaveInfoService.setRows(rows);
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(leaveTypeObj, leaveTitle, employeeObj, addTime, shenHeState, page);
	    /*计算总的页数和总的记录数*/
	    leaveInfoService.queryTotalPageAndRecordNumber(leaveTypeObj, leaveTitle, employeeObj, addTime, shenHeState);
	    /*获取到总的页码数目*/
	    int totalPage = leaveInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(LeaveInfo leaveInfo:leaveInfoList) {
			JSONObject jsonLeaveInfo = leaveInfo.getJsonObject();
			jsonArray.put(jsonLeaveInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryAllLeaveInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LeaveInfo leaveInfo:leaveInfoList) {
			JSONObject jsonLeaveInfo = new JSONObject();
			jsonLeaveInfo.accumulate("leaveId", leaveInfo.getLeaveId());
			jsonLeaveInfo.accumulate("leaveTitle", leaveInfo.getLeaveTitle());
			jsonArray.put(jsonLeaveInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("leaveTypeObj") LeaveType leaveTypeObj,String leaveTitle,@ModelAttribute("employeeObj") Employee employeeObj,String addTime,String shenHeState,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (leaveTitle == null) leaveTitle = "";
		if (addTime == null) addTime = "";
		if (shenHeState == null) shenHeState = "";
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(leaveTypeObj, leaveTitle, employeeObj, addTime, shenHeState, currentPage);
	    /*计算总的页数和总的记录数*/
	    leaveInfoService.queryTotalPageAndRecordNumber(leaveTypeObj, leaveTitle, employeeObj, addTime, shenHeState);
	    /*获取到总的页码数目*/
	    int totalPage = leaveInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveInfoService.getRecordNumber();
	    request.setAttribute("leaveInfoList",  leaveInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("leaveTypeObj", leaveTypeObj);
	    request.setAttribute("leaveTitle", leaveTitle);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("shenHeState", shenHeState);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<LeaveType> leaveTypeList = leaveTypeService.queryAllLeaveType();
	    request.setAttribute("leaveTypeList", leaveTypeList);
		return "LeaveInfo/leaveInfo_frontquery_result"; 
	}
	
	
	/*前台员工按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/empFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String empFrontlist(@ModelAttribute("leaveTypeObj") LeaveType leaveTypeObj,String leaveTitle,@ModelAttribute("employeeObj") Employee employeeObj,String addTime,String shenHeState,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (leaveTitle == null) leaveTitle = "";
		if (addTime == null) addTime = "";
		if (shenHeState == null) shenHeState = "";
		employeeObj = new Employee();
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(leaveTypeObj, leaveTitle, employeeObj, addTime, shenHeState, currentPage);
	    /*计算总的页数和总的记录数*/
	    leaveInfoService.queryTotalPageAndRecordNumber(leaveTypeObj, leaveTitle, employeeObj, addTime, shenHeState);
	    /*获取到总的页码数目*/
	    int totalPage = leaveInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveInfoService.getRecordNumber();
	    request.setAttribute("leaveInfoList",  leaveInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("leaveTypeObj", leaveTypeObj);
	    request.setAttribute("leaveTitle", leaveTitle);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("shenHeState", shenHeState);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<LeaveType> leaveTypeList = leaveTypeService.queryAllLeaveType();
	    request.setAttribute("leaveTypeList", leaveTypeList);
		return "LeaveInfo/leaveInfo_empFrontquery_result"; 
	}

	

     /*前台查询LeaveInfo信息*/
	@RequestMapping(value="/{leaveId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer leaveId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoService.getLeaveInfo(leaveId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        List<LeaveType> leaveTypeList = leaveTypeService.queryAllLeaveType();
        request.setAttribute("leaveTypeList", leaveTypeList);
        request.setAttribute("leaveInfo",  leaveInfo);
        return "LeaveInfo/leaveInfo_frontshow";
	}

	/*ajax方式显示请假修改jsp视图页*/
	@RequestMapping(value="/{leaveId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer leaveId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoService.getLeaveInfo(leaveId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLeaveInfo = leaveInfo.getJsonObject();
		out.println(jsonLeaveInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新请假信息*/
	@RequestMapping(value = "/{leaveId}/update", method = RequestMethod.POST)
	public void update(@Validated LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			leaveInfoService.updateLeaveInfo(leaveInfo);
			message = "请假更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "请假更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除请假信息*/
	@RequestMapping(value="/{leaveId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer leaveId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  leaveInfoService.deleteLeaveInfo(leaveId);
	            request.setAttribute("message", "请假删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "请假删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条请假记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String leaveIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = leaveInfoService.deleteLeaveInfos(leaveIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出请假信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("leaveTypeObj") LeaveType leaveTypeObj,String leaveTitle,@ModelAttribute("employeeObj") Employee employeeObj,String addTime,String shenHeState, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(leaveTitle == null) leaveTitle = "";
        if(addTime == null) addTime = "";
        if(shenHeState == null) shenHeState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(leaveTypeObj,leaveTitle,employeeObj,addTime,shenHeState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "LeaveInfo信息记录"; 
        String[] headers = { "请假类型","请假标题","请假时长","请假员工","提交时间","审核状态","负责人回复"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveInfoList.size();i++) {
        	LeaveInfo leaveInfo = leaveInfoList.get(i); 
        	dataset.add(new String[]{leaveInfo.getLeaveTypeObj().getLeaveTypeName(),leaveInfo.getLeaveTitle(),leaveInfo.getDays(),leaveInfo.getEmployeeObj().getName(),leaveInfo.getAddTime(),leaveInfo.getShenHeState(),leaveInfo.getReplyContent()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LeaveInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
