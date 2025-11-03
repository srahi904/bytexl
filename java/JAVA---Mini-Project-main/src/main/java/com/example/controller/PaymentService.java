package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Transactional
    public void payFees(int studentId, double amount) {
        paymentDao.savePayment(studentId, amount, "Paid");
    }

    @Transactional(rollbackFor = Exception.class)
    public void refundFees(int studentId, double amount) {
        paymentDao.updatePayment(studentId, "Refunded");
        if (amount > 10000)
            throw new RuntimeException("Refund limit exceeded — Transaction Rolled Back!");
    }
}
