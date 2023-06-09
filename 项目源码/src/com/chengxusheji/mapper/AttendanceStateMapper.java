package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.AttendanceState;

public interface AttendanceStateMapper {
	/*添加出勤状态信息*/
	public void addAttendanceState(AttendanceState attendanceState) throws Exception;

	/*按照查询条件分页查询出勤状态记录*/
	public ArrayList<AttendanceState> queryAttendanceState(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有出勤状态记录*/
	public ArrayList<AttendanceState> queryAttendanceStateList(@Param("where") String where) throws Exception;

	/*按照查询条件的出勤状态记录数*/
	public int queryAttendanceStateCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条出勤状态记录*/
	public AttendanceState getAttendanceState(int attendanceStateId) throws Exception;

	/*更新出勤状态记录*/
	public void updateAttendanceState(AttendanceState attendanceState) throws Exception;

	/*删除出勤状态记录*/
	public void deleteAttendanceState(int attendanceStateId) throws Exception;

}
