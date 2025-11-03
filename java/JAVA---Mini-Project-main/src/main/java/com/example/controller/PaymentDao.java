package com.example.controller;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void savePayment(int studentId, double amount, String status) {
        Payment payment = new Payment();
        payment.setStudentId(studentId);
        payment.setAmount(amount);
        payment.setStatus(status);
        sessionFactory.getCurrentSession().save(payment);
    }

    public void updatePayment(int studentId, String status) {
        Payment payment = (Payment) sessionFactory.getCurrentSession()
                .createQuery("from Payment where studentId=:sid")
                .setParameter("sid", studentId)
                .uniqueResult();
        if (payment != null) {
            payment.setStatus(status);
            sessionFactory.getCurrentSession().update(payment);
        }
    }
}
