package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.LeaveType;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.LeaveInfo;

import com.chengxusheji.mapper.LeaveInfoMapper;
@Service
public class LeaveInfoService {

	@Resource LeaveInfoMapper leaveInfoMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加请假记录*/
    public void addLeaveInfo(LeaveInfo leaveInfo) throws Exception {
    	leaveInfoMapper.addLeaveInfo(leaveInfo);
    }

    /*按照查询条件分页查询请假记录*/
    public ArrayList<LeaveInfo> queryLeaveInfo(LeaveType leaveTypeObj,String leaveTitle,Employee employeeObj,String addTime,String shenHeState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != leaveTypeObj && leaveTypeObj.getLeaveTypeId()!= null && leaveTypeObj.getLeaveTypeId()!= 0)  where += " and t_leaveInfo.leaveTypeObj=" + leaveTypeObj.getLeaveTypeId();
    	if(!leaveTitle.equals("")) where = where + " and t_leaveInfo.leaveTitle like '%" + leaveTitle + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_leaveInfo.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!addTime.equals("")) where = where + " and t_leaveInfo.addTime like '%" + addTime + "%'";
    	if(!shenHeState.equals("")) where = where + " and t_leaveInfo.shenHeState like '%" + shenHeState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return leaveInfoMapper.queryLeaveInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<LeaveInfo> queryLeaveInfo(LeaveType leaveTypeObj,String leaveTitle,Employee employeeObj,String addTime,String shenHeState) throws Exception  { 
     	String where = "where 1=1";
    	if(null != leaveTypeObj && leaveTypeObj.getLeaveTypeId()!= null && leaveTypeObj.getLeaveTypeId()!= 0)  where += " and t_leaveInfo.leaveTypeObj=" + leaveTypeObj.getLeaveTypeId();
    	if(!leaveTitle.equals("")) where = where + " and t_leaveInfo.leaveTitle like '%" + leaveTitle + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_leaveInfo.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!addTime.equals("")) where = where + " and t_leaveInfo.addTime like '%" + addTime + "%'";
    	if(!shenHeState.equals("")) where = where + " and t_leaveInfo.shenHeState like '%" + shenHeState + "%'";
    	return leaveInfoMapper.queryLeaveInfoList(where);
    }

    /*查询所有请假记录*/
    public ArrayList<LeaveInfo> queryAllLeaveInfo()  throws Exception {
        return leaveInfoMapper.queryLeaveInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(LeaveType leaveTypeObj,String leaveTitle,Employee employeeObj,String addTime,String shenHeState) throws Exception {
     	String where = "where 1=1";
    	if(null != leaveTypeObj && leaveTypeObj.getLeaveTypeId()!= null && leaveTypeObj.getLeaveTypeId()!= 0)  where += " and t_leaveInfo.leaveTypeObj=" + leaveTypeObj.getLeaveTypeId();
    	if(!leaveTitle.equals("")) where = where + " and t_leaveInfo.leaveTitle like '%" + leaveTitle + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_leaveInfo.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!addTime.equals("")) where = where + " and t_leaveInfo.addTime like '%" + addTime + "%'";
    	if(!shenHeState.equals("")) where = where + " and t_leaveInfo.shenHeState like '%" + shenHeState + "%'";
        recordNumber = leaveInfoMapper.queryLeaveInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取请假记录*/
    public LeaveInfo getLeaveInfo(int leaveId) throws Exception  {
        LeaveInfo leaveInfo = leaveInfoMapper.getLeaveInfo(leaveId);
        return leaveInfo;
    }

    /*更新请假记录*/
    public void updateLeaveInfo(LeaveInfo leaveInfo) throws Exception {
        leaveInfoMapper.updateLeaveInfo(leaveInfo);
    }

    /*删除一条请假记录*/
    public void deleteLeaveInfo (int leaveId) throws Exception {
        leaveInfoMapper.deleteLeaveInfo(leaveId);
    }

    /*删除多条请假信息*/
    public int deleteLeaveInfos (String leaveIds) throws Exception {
    	String _leaveIds[] = leaveIds.split(",");
    	for(String _leaveId: _leaveIds) {
    		leaveInfoMapper.deleteLeaveInfo(Integer.parseInt(_leaveId));
    	}
    	return _leaveIds.length;
    }
}
