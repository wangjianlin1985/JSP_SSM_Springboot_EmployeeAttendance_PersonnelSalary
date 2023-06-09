package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.LeaveType;

import com.chengxusheji.mapper.LeaveTypeMapper;
@Service
public class LeaveTypeService {

	@Resource LeaveTypeMapper leaveTypeMapper;
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

    /*添加请假类型记录*/
    public void addLeaveType(LeaveType leaveType) throws Exception {
    	leaveTypeMapper.addLeaveType(leaveType);
    }

    /*按照查询条件分页查询请假类型记录*/
    public ArrayList<LeaveType> queryLeaveType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return leaveTypeMapper.queryLeaveType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<LeaveType> queryLeaveType() throws Exception  { 
     	String where = "where 1=1";
    	return leaveTypeMapper.queryLeaveTypeList(where);
    }

    /*查询所有请假类型记录*/
    public ArrayList<LeaveType> queryAllLeaveType()  throws Exception {
        return leaveTypeMapper.queryLeaveTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = leaveTypeMapper.queryLeaveTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取请假类型记录*/
    public LeaveType getLeaveType(int leaveTypeId) throws Exception  {
        LeaveType leaveType = leaveTypeMapper.getLeaveType(leaveTypeId);
        return leaveType;
    }

    /*更新请假类型记录*/
    public void updateLeaveType(LeaveType leaveType) throws Exception {
        leaveTypeMapper.updateLeaveType(leaveType);
    }

    /*删除一条请假类型记录*/
    public void deleteLeaveType (int leaveTypeId) throws Exception {
        leaveTypeMapper.deleteLeaveType(leaveTypeId);
    }

    /*删除多条请假类型信息*/
    public int deleteLeaveTypes (String leaveTypeIds) throws Exception {
    	String _leaveTypeIds[] = leaveTypeIds.split(",");
    	for(String _leaveTypeId: _leaveTypeIds) {
    		leaveTypeMapper.deleteLeaveType(Integer.parseInt(_leaveTypeId));
    	}
    	return _leaveTypeIds.length;
    }
}
