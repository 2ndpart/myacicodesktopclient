package com.myacico.util;

import java.math.BigDecimal;

public class TransactionDetail {
    
	private BigDecimal grandTotal;
    private String invoiceNumber;
    private String orderNumber;
    private String transactionTime;
    private String fromName;
    private String fromAddress;
    private String fromPhone;
    private String toName;
    private String toAddress;
    private String toPhone;
    private String detail;
    private String paymentMethod;
    private String courier;
    private BigDecimal courierAmount;
    private String email;
    

    /**
     * @return the grandTotal
     */
    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    /**
     * @param grandTotal the grandTotal to set
     */
    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the transactionTime
     */
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * @param transactionTime the transactionTime to set
     */
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * @return the fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName the fromName to set
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return the fromAddress
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * @param fromAddress the fromAddress to set
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * @return the fromPhone
     */
    public String getFromPhone() {
        return fromPhone;
    }

    /**
     * @param fromPhone the fromPhone to set
     */
    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    /**
     * @return the toName
     */
    public String getToName() {
        return toName;
    }

    /**
     * @param toName the toName to set
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     * @return the toAddress
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * @param toAddress the toAddress to set
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * @return the toPhone
     */
    public String getToPhone() {
        return toPhone;
    }

    /**
     * @param toPhone the toPhone to set
     */
    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return the courier
     */
    public String getCourier() {
        return courier;
    }

    /**
     * @param courier the courier to set
     */
    public void setCourier(String courier) {
        this.courier = courier;
    }

    /**
     * @return the courierAmount
     */
    public BigDecimal getCourierAmount() {
        return courierAmount;
    }

    /**
     * @param courierAmount the courierAmount to set
     */
    public void setCourierAmount(BigDecimal courierAmount) {
        this.courierAmount = courierAmount;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
