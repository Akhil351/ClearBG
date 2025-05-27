package org.akhil.bg.service;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.util.Map;

public interface RazorPayService {
    Order createOrder(BigDecimal amount, String currency) throws RazorpayException;
    Map<String,Object> verifyPayment(String razorPayOrderId) throws RazorpayException;
}
