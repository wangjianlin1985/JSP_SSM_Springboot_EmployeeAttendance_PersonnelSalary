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
import com.chengxusheji.service.LeaveTypeService;
import com.chengxusheji.po.LeaveType;

//LeaveType管理控制层
@Controller
@RequestMapping("/LeaveType")
public class LeaveTypeController extends BaseController {

    /*业务层对象*/
    @Resource LeaveTypeService leaveTypeService;

	@InitBinder("leaveType")
	public void initBinderLeaveType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("leaveType.");
	}
	/*跳转到添加LeaveType视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new LeaveType());
		return "LeaveType_add";
	}

	/*客户端ajax方式提交添加请假类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LeaveType leaveType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        leaveTypeService.addLeaveType(leaveType);
        message = "请假类型添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询请假类型信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)leaveTypeService.setRows(rows);
		List<LeaveType> leaveTypeList = leaveTypeService.queryLeaveType(page);
	    /*计算总的页数和总的记录数*/
	    leaveTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = leaveTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveTypeService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(LeaveType leaveType:leaveTypeList) {
			JSONObject jsonLeaveType = leaveType.getJsonObject();
			jsonArray.put(jsonLeaveType);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询请假类型信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LeaveType> leaveTypeList = leaveTypeService.queryAllLeaveType();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LeaveType leaveType:leaveTypeList) {
			JSONObject jsonLeaveType = new JSONObject();
			jsonLeaveType.accumulate("leaveTypeId", leaveType.getLeaveTypeId());
			jsonLeaveType.accumulate("leaveTypeName", leaveType.getLeaveTypeName());
			jsonArray.put(jsonLeaveType);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询请假类型信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<LeaveType> leaveTypeList = leaveTypeService.queryLeaveType(currentPage);
	    /*计算总的页数和总的记录数*/
	    leaveTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = leaveTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveTypeService.getRecordNumber();
	    request.setAttribute("leaveTypeList",  leaveTypeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "LeaveType/leaveType_frontquery_result"; 
	}

     /*前台查询LeaveType信息*/
	@RequestMapping(value="/{leaveTypeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer leaveTypeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键leaveTypeId获取LeaveType对象*/
        LeaveType leaveType = leaveTypeService.getLeaveType(leaveTypeId);

        request.setAttribute("leaveType",  leaveType);
        return "LeaveType/leaveType_frontshow";
	}

	/*ajax方式显示请假类型修改jsp视图页*/
	@RequestMapping(value="/{leaveTypeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer leaveTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键leaveTypeId获取LeaveType对象*/
        LeaveType leaveType = leaveTypeService.getLeaveType(leaveTypeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLeaveType = leaveType.getJsonObject();
		out.println(jsonLeaveType.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新请假类型信息*/
	@RequestMapping(value = "/{leaveTypeId}/update", method = RequestMethod.POST)
	public void update(@Validated LeaveType leaveType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			leaveTypeService.updateLeaveType(leaveType);
			message = "请假类型更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "请假类型更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除请假类型信息*/
	@RequestMapping(value="/{leaveTypeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer leaveTypeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  leaveTypeService.deleteLeaveType(leaveTypeId);
	            request.setAttribute("message", "请假类型删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "请假类型删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条请假类型记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String leaveTypeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = leaveTypeService.deleteLeaveTypes(leaveTypeIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出请假类型信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<LeaveType> leaveTypeList = leaveTypeService.queryLeaveType();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "LeaveType信息记录"; 
        String[] headers = { "请假类型id","请假类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveTypeList.size();i++) {
        	LeaveType leaveType = leaveTypeList.get(i); 
        	dataset.add(new String[]{leaveType.getLeaveTypeId() + "",leaveType.getLeaveTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveType.xls");//filename是下载的xls的名，建议最好用英文 
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
