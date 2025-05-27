package org.akhil.bg.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.akhil.bg.payload.OrderDto;
import org.akhil.bg.response.RemoveBgResponse;
import org.akhil.bg.service.OrderService;
import org.akhil.bg.service.RazorPayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final RazorPayService razorpayService;

    @PostMapping
    public ResponseEntity<RemoveBgResponse> createOrder(@RequestParam String planId, Authentication authentication) throws RazorpayException {
        if(authentication.getName().isEmpty() || authentication.getPrincipal() == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    RemoveBgResponse.builder()
                            .success(false)
                            .statusCode(HttpStatus.FORBIDDEN)
                            .data("User does not have permission to access this resource")
                            .build()
            );
        }

        try{
            Order order=orderService.createOrder(planId,authentication.getName());
            OrderDto orderDto=convertToDto(order);
            return ResponseEntity.ok(RemoveBgResponse.builder()
                            .success(true)
                            .data(orderDto)
                            .statusCode(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RemoveBgResponse.builder()
                        .success(false)
                        .data(e.getMessage())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
            }
        }

    }

    @PostMapping("/verify")
    public  ResponseEntity<?> verifyOrder(@RequestBody Map<String,Object> request) throws RazorpayException {
        try{
            String razorpayId= request.get("razorpay_order_id").toString();
            Map<String,Object> response=razorpayService.verifyPayment(razorpayId);
            return ResponseEntity.ok(response
            );

        } catch (RazorpayException e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("success",false);
            errorResponse.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }

    private OrderDto convertToDto(Order order) {
        return OrderDto.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(new BigDecimal(order.get("amount").toString()))
                .currency(order.get("currency").toString())
                .created_at(order.get("created_at")) // convert UNIX timestamp to Date
                .status(order.get("status").toString())
                .receipt(order.get("receipt").toString())
                .build();
    }

}
