package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.LeaveType;

public interface LeaveTypeMapper {
	/*添加请假类型信息*/
	public void addLeaveType(LeaveType leaveType) throws Exception;

	/*按照查询条件分页查询请假类型记录*/
	public ArrayList<LeaveType> queryLeaveType(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有请假类型记录*/
	public ArrayList<LeaveType> queryLeaveTypeList(@Param("where") String where) throws Exception;

	/*按照查询条件的请假类型记录数*/
	public int queryLeaveTypeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条请假类型记录*/
	public LeaveType getLeaveType(int leaveTypeId) throws Exception;

	/*更新请假类型记录*/
	public void updateLeaveType(LeaveType leaveType) throws Exception;

	/*删除请假类型记录*/
	public void deleteLeaveType(int leaveTypeId) throws Exception;

}
