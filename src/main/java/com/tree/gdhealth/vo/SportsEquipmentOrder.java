package com.tree.gdhealth.vo;

import com.tree.gdhealth.sportsequipment.vo.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**<p>{@link OrderStatus} using {@link com.tree.gdhealth.mybatis.typehandler.OrderStatusTypeHandler} </p>
 * @author 정인호
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SportsEquipmentOrder {
    private int orderNo;
    private int employeeOrderer;
    private int branchNo;
    private int sportsEquipmentNo;
    private int quantity;
    private int totalPrice;
    private int employeeApprover;
    private OrderStatus orderStatus;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;
}
