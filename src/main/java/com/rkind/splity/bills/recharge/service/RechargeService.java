package com.rkind.splity.bills.recharge.service;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.rkind.splity.bills.recharge.dto.*;
import com.rkind.splity.bills.recharge.entity.RechargeCircle;
import com.rkind.splity.bills.recharge.entity.RechargeHistory;
import com.rkind.splity.bills.recharge.entity.RechargeOperator;
import com.rkind.splity.bills.recharge.entity.RechargePlan;
import com.rkind.splity.bills.recharge.enums.RechargeStatus;
import com.rkind.splity.bills.recharge.enums.RechargeType;
import com.rkind.splity.bills.recharge.mapper.RechargeMapper;
import com.rkind.splity.bills.recharge.repository.RechargeCircleRepository;
import com.rkind.splity.bills.recharge.repository.RechargeHistoryRepository;
import com.rkind.splity.bills.recharge.repository.RechargeOperatorRepository;
import com.rkind.splity.bills.recharge.repository.RechargePlanRepository;
import com.rkind.splity.expense.entity.PaymentMethod;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RechargeService {

    private final RechargeOperatorRepository rechargeOperatorRepository;

    private final RechargeCircleRepository rechargeCircleRepository;

    private final RechargeHistoryRepository rechargeHistoryRepository;

    private final RechargePlanRepository rechargePlanRepository;
    private final RechargeMapper rechargeMapper;

    public RechargeService(
            RechargeOperatorRepository rechargeOperatorRepository,
            RechargeCircleRepository rechargeCircleRepository,
            RechargeHistoryRepository rechargeHistoryRepository,
            RechargePlanRepository rechargePlanRepository,
            RechargeMapper rechargeMapper) {
        this.rechargeOperatorRepository = rechargeOperatorRepository;
        this.rechargeCircleRepository = rechargeCircleRepository;
        this.rechargeHistoryRepository = rechargeHistoryRepository;
        this.rechargePlanRepository = rechargePlanRepository;
        this.rechargeMapper = rechargeMapper;
    }

    public List<RechargePlanResponse> getRechargePlans(GetRechargePlanRequest request) {
        RechargeOperator operator = rechargeOperatorRepository.findById(request.getOperatorId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        RechargeCircle circle = rechargeCircleRepository.findById(request.getCircleId())
                .orElseThrow(() -> new RuntimeException("Circle not found"));

        if (!operator.isActive()) {
            throw new RuntimeException("Operator is not active");
        }

        if (!circle.getActive()) {
            throw new RuntimeException("Circle is not active");
        }

        List<RechargePlan> plans = rechargePlanRepository.findByOperatorAndCircleAndActiveTrue(operator, circle);

        if (plans.isEmpty()) {
            throw new RuntimeException("No recharge plans available");
        }

        List<RechargePlanResponse> responses = new ArrayList<>();

        for (RechargePlan plan : plans) {

            RechargePlanResponse response =
                    rechargeMapper.toRechargePlanResponse(plan);

            responses.add(response);
        }

        return responses;
    }

    public RechargeResponse recharge(RechargeRequest request) {
        validateMobileNumber(request.getMobileNumber());

        RechargePlan plan = rechargePlanRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        if (!plan.isActive()) {
            throw new RuntimeException("Plan is not active");
        }

        RechargeOperator operator = rechargeOperatorRepository.findById(request.getOperatorId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        if (!operator.isActive()) {
            throw new RuntimeException("Operator is not active");
        }

        RechargeCircle circle = rechargeCircleRepository.findById(request.getCircleId())
                .orElseThrow(() -> new RuntimeException("Circle not found"));

        if (!circle.getActive()) {
            throw  new RuntimeException("Circle is not active");
        }

        String transactionId = generateTransactionId();

        RechargeHistory history = new RechargeHistory();

        history.setTransactionId(transactionId);
        history.setOperatorId(operator.getId());
        history.setCircleId(circle.getId());
        history.setPlanId(plan.getId());
        history.setMobileNumber(request.getMobileNumber());
        history.setRechargeType(RechargeType.PREPAID);
        history.setAmount(plan.getAmount());
        history.setStatus(RechargeStatus.PROCESSING);
        history.setUserId(1L);
        history.setPaymentMode(PaymentMethod.UPI.name());

        RechargeHistory savedHistory = rechargeHistoryRepository.save(history);

        AggregatorRechargeResponse apiResponse =
                callAggregatorApi(savedHistory, plan);

        updateRechargeHistory(savedHistory, apiResponse);

        //===========================================

        // SET MAPPER

        //===========================================

        RechargeResponse response =
                rechargeMapper.toRechargeResponse(
                        savedHistory,
                        plan,
                        operator,
                        circle
                );

        return response;

    }

    private void updateRechargeHistory(RechargeHistory savedHistory, AggregatorRechargeResponse apiResponse) {

    }

    private AggregatorRechargeResponse callAggregatorApi(RechargeHistory savedHistory, RechargePlan plan) {
        return null;
    }

    private String generateTransactionId() {
        String prefix = "SPL-RCH";
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = currentDate.format(formatter);

        Random random = new Random();
        int number = 100000 + random.nextInt(9900000);

        return prefix + "-" + date + "-" + number;
    }

    private void validateMobileNumber(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.length() != 10) {
            throw new RuntimeException("Invalid mobile number");
        }

        char firstDigit = mobileNumber.charAt(0);

        if (firstDigit != '6' &&
                firstDigit != '7' &&
                firstDigit != '8' &&
                firstDigit != '9') {

            throw new RuntimeException("Enter a valid mobile number");
        }

        for (int i = 0; i < mobileNumber.length(); i++) {

            if (!Character.isDigit(mobileNumber.charAt(i))) {
                throw new RuntimeException("Mobile number must contain only digits");
            }
        }
    }
}