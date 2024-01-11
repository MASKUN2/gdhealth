package com.tree.gdhealth.employee.api;

import com.tree.gdhealth.employee.dto.EmployeeInformation;
import com.tree.gdhealth.employee.dto.EmployeeRetrieveCriteria;
import com.tree.gdhealth.utils.exception.TooManyResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** <p>직원 도메인 API 서비스</p>
 * @author 정인호
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmployeeApiService {
    private final EmployeeApiMapper mapper;

    public EmployeeInformation getEmployeeOneByEmployeeNo(int employeeNo) throws TooManyResultsException{

        List<EmployeeInformation> dtoList = mapper.selectEmployeeListByCriteria(EmployeeRetrieveCriteria.employeeNo(employeeNo));
        if(dtoList.size() > 1) {
            throw new TooManyResultsException("1개를 조회하려했으나 2개 이상이 조회되었습니다. 값 ="+ dtoList.toString());
        }

        return dtoList.isEmpty()? null : dtoList.get(0);
    }

    public List<EmployeeInformation> getEmployeeListIsHeadOffice() {

        return mapper.selectEmployeeListByCriteria(EmployeeRetrieveCriteria.isHeadOffice());
    }

    public List<EmployeeInformation> getEmployeeListByBranchNo(int branchNo) {

        return mapper.selectEmployeeListByCriteria(EmployeeRetrieveCriteria.branchNo(branchNo));
    }
}
