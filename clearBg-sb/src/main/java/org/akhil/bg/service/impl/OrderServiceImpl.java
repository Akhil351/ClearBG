package org.akhil.bg.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.akhil.bg.model.OrderEntity;
import org.akhil.bg.repo.OrderRepo;
import org.akhil.bg.service.OrderService;
import org.akhil.bg.service.RazorPayService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final RazorPayService razorpayService;

    private static final Map<String,PlanDetails> PLAN_DETAILS=
            Map.of("Basic",new PlanDetails("Basic",100,new BigDecimal("999.00")),
                    "Premium",new PlanDetails("Premium",250,new BigDecimal("1999.00")),
                    "Ultimate",new PlanDetails("Ultimate",1000,new BigDecimal("3999.00")));

    private record PlanDetails(String name, int credits, BigDecimal amount){

    }

    @Override
    public Order createOrder(String planId, String clerkId) throws RazorpayException {
        PlanDetails planDetails = PLAN_DETAILS.get(planId);
        if(planDetails==null){
            throw new IllegalArgumentException("Plan id "+planId+" not exist");
        }
        try{
            Order razorPayOrder=razorpayService.createOrder(planDetails.amount(),"INR");
            OrderEntity orderEntity=OrderEntity.builder()
                    .clerkId(clerkId)
                    .plan(planDetails.name())
                    .credits(planDetails.credits())
                    .amount(planDetails.amount())
                    .orderId(razorPayOrder.get("id"))
                    .build();
            orderRepo.save(orderEntity);
            return razorPayOrder;
        } catch (RazorpayException e){
            throw new RazorpayException("Error while creating order",e);
        }

    }
}
