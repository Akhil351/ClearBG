package org.akhil.bg.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.akhil.bg.model.OrderEntity;
import org.akhil.bg.payload.UserDto;
import org.akhil.bg.repo.OrderRepo;
import org.akhil.bg.service.RazorPayService;
import org.akhil.bg.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RazorPayServiceImpl implements RazorPayService {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    private final OrderRepo orderRepo;
    private final UserService userService;
    @Override
    public Order createOrder(BigDecimal amount, String currency) throws RazorpayException {
       try{
           RazorpayClient razorpayClient=new RazorpayClient(razorpayKeyId,razorpayKeySecret);
           JSONObject obj=new JSONObject();
           obj.put("amount",amount.multiply(new BigDecimal(100)));
           // 100 paisa = 1 rupee
           obj.put("currency",currency);
           obj.put("receipt","order_rcpt_id"+System.currentTimeMillis());
           obj.put("payment_capture",1);
           return razorpayClient.orders.create(obj);
       } catch(RazorpayException e){
           throw new RazorpayException("Razorpay exception",e);
       }
    }

    @Override
    public Map<String, Object> verifyPayment(String razorPayOrderId) throws RazorpayException {
        Map<String,Object> map=new HashMap<>();
        try{
            RazorpayClient razorpayClient=new RazorpayClient(razorpayKeyId,razorpayKeySecret);
            Order order=razorpayClient.orders.fetch(razorPayOrderId);
            if(order.get("status").toString().equalsIgnoreCase("paid")){
                OrderEntity orderEntity=orderRepo.findByOrderId(razorPayOrderId)
                        .orElseThrow(() -> new RazorpayException("Order not found"));
                if(orderEntity.getPayment()){
                    map.put("success",false);
                     map.put("message","Payment failed");
                     return map;
                }
                UserDto userDto=userService.getUserByClerkId(orderEntity.getClerkId());
                userDto.setCredits(userDto.getCredits()+orderEntity.getCredits());
                userService.saveUser(userDto);
                orderEntity.setPayment(true);
                orderRepo.save(orderEntity);
                map.put("success",true);
                map.put("message","credits added");
                return map;

            }
        } catch (RazorpayException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"" +
                    "Error while verifying the payments");
        }
        return map;
    }
}
