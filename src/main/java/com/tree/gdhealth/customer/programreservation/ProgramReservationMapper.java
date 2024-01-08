package com.tree.gdhealth.customer.programreservation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.vo.Branch;

@Mapper
public interface ProgramReservationMapper {
	
	List<Map<String, Object>> selectProgramByMonth(Map<String, Object> paramMap);
	
	List<Map<String, Object>> allCalendarList();
	
	List<Map<String, Object>> allCalendarListPage(Map<String, Object> paramMap);
	
	List<Map<String, Object>> myCalendarList(Map<String, Object> paramMap);
	
	int lastPage(Map<String, Object> paramMap);

	Map<String, Object> proRsOne(Map<String, Object> paramMap);
	
	String customerId(int customerNo);
	
	Map<String, Object> customerPayment (int customerNo);
	
	int programReservationAdd (Map<String, Object> paramap);
	
	List<Map<String, Object>> myreservation (int customerNo);
	
	int reservationdelete (Map<String, Object> paramap);
	
	List<Branch> branchList ();
	
	Integer  reservationDate (Map<String, Object> paramap);
}
