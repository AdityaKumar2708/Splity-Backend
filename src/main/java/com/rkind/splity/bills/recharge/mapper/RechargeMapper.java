package com.rkind.splity.bills.recharge.mapper;

import com.rkind.splity.bills.recharge.dto.RechargePlanResponse;
import com.rkind.splity.bills.recharge.dto.RechargeResponse;
import com.rkind.splity.bills.recharge.entity.RechargeCircle;
import com.rkind.splity.bills.recharge.entity.RechargeHistory;
import com.rkind.splity.bills.recharge.entity.RechargeOperator;
import com.rkind.splity.bills.recharge.entity.RechargePlan;
import org.springframework.stereotype.Component;

@Component
public class RechargeMapper {

    public RechargeResponse toRechargeResponse(
            RechargeHistory history,
            RechargePlan plan,
            RechargeOperator operator,
            RechargeCircle circle
    ) {

        if (history == null) {
            return null;
        }

        RechargeResponse response = new RechargeResponse();

        response.setTransactionId(history.getTransactionId());

        response.setMobileNumber(history.getMobileNumber());

        response.setOperatorName(operator.getDisplayName());

        response.setCircleName(circle.getName());

        response.setPlanName(plan.getPlanName());

        response.setAmount(plan.getAmount());

        response.setValidity(plan.getValidity());

        response.setStatus(history.getStatus());

        response.setMessage(history.getMessage());

        response.setRechargeTime(history.getUpdatedAt());

        return response;
    }

    public RechargePlanResponse toRechargePlanResponse(
            RechargePlan plan
    ) {

        if (plan == null) {
            return null;
        }

        RechargePlanResponse response = new RechargePlanResponse();

        response.setPlanId(plan.getId());
        response.setPlanName(plan.getPlanName());
        response.setAmount(plan.getAmount());
        response.setValidity(plan.getValidity());
        response.setSmsBenefit(plan.getSmsBenefit());
        response.setVoiceBenefit(plan.getVoiceBenefit());
        response.setDataBenefit(plan.getDataBenefit());
        response.setOttBenefit(plan.getOttBenefit());
        response.setDescription(plan.getDescription());

        return response;
    }
}