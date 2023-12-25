package com.tree.gdhealth.branch.sportsequipment;

import com.tree.gdhealth.sportsequipment.vo.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 정인호
 */
@Builder
@AllArgsConstructor
@Data
public class SportsEquipmentOrderAddDto {
    @Positive
    private final int employeeOrderer;
    @Positive
    private final int branchNo;
    @Positive
    private final int sportsEquipmentNo;
    @Positive
    private final int quantity;
    @Positive
    private final int employeeApprover;
}
