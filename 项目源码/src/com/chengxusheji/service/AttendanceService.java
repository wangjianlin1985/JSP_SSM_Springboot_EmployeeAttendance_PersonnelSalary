package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.AttendanceState;
import com.chengxusheji.po.Attendance;

import com.chengxusheji.mapper.AttendanceMapper;
@Service
public class AttendanceService {

	@Resource AttendanceMapper attendanceMapper;
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

    /*添加员工出勤记录*/
    public void addAttendance(Attendance attendance) throws Exception {
    	attendanceMapper.addAttendance(attendance);
    }

    /*按照查询条件分页查询员工出勤记录*/
    public ArrayList<Attendance> queryAttendance(String attendanceDate,Employee employeeObj,AttendanceState attendanceState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!attendanceDate.equals("")) where = where + " and t_attendance.attendanceDate like '%" + attendanceDate + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_attendance.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != attendanceState && attendanceState.getAttendanceStateId()!= null && attendanceState.getAttendanceStateId()!= 0)  where += " and t_attendance.attendanceState=" + attendanceState.getAttendanceStateId();
    	int startIndex = (currentPage-1) * this.rows;
    	return attendanceMapper.queryAttendance(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Attendance> queryAttendance(String attendanceDate,Employee employeeObj,AttendanceState attendanceState) throws Exception  { 
     	String where = "where 1=1";
    	if(!attendanceDate.equals("")) where = where + " and t_attendance.attendanceDate like '%" + attendanceDate + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_attendance.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != attendanceState && attendanceState.getAttendanceStateId()!= null && attendanceState.getAttendanceStateId()!= 0)  where += " and t_attendance.attendanceState=" + attendanceState.getAttendanceStateId();
    	return attendanceMapper.queryAttendanceList(where);
    }

    /*查询所有员工出勤记录*/
    public ArrayList<Attendance> queryAllAttendance()  throws Exception {
        return attendanceMapper.queryAttendanceList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String attendanceDate,Employee employeeObj,AttendanceState attendanceState) throws Exception {
     	String where = "where 1=1";
    	if(!attendanceDate.equals("")) where = where + " and t_attendance.attendanceDate like '%" + attendanceDate + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_attendance.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != attendanceState && attendanceState.getAttendanceStateId()!= null && attendanceState.getAttendanceStateId()!= 0)  where += " and t_attendance.attendanceState=" + attendanceState.getAttendanceStateId();
        recordNumber = attendanceMapper.queryAttendanceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取员工出勤记录*/
    public Attendance getAttendance(int attendanceId) throws Exception  {
        Attendance attendance = attendanceMapper.getAttendance(attendanceId);
        return attendance;
    }

    /*更新员工出勤记录*/
    public void updateAttendance(Attendance attendance) throws Exception {
        attendanceMapper.updateAttendance(attendance);
    }

    /*删除一条员工出勤记录*/
    public void deleteAttendance (int attendanceId) throws Exception {
        attendanceMapper.deleteAttendance(attendanceId);
    }

    /*删除多条员工出勤信息*/
    public int deleteAttendances (String attendanceIds) throws Exception {
    	String _attendanceIds[] = attendanceIds.split(",");
    	for(String _attendanceId: _attendanceIds) {
    		attendanceMapper.deleteAttendance(Integer.parseInt(_attendanceId));
    	}
    	return _attendanceIds.length;
    }
}
