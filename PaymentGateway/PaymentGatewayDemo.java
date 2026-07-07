package PaymentGateway;

import java.util.Random;

class PaymentRequest {
    String sender;
    String receiver;
    double amount;
    String currency;

    public PaymentRequest(String sender, String receiver, double amount, String currency) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }
}

interface BankingSystem {
    boolean processPayment(PaymentRequest request);
}

class PaytmBankingSystem implements BankingSystem {

    private Random random = new Random();

    @Override
    public boolean processPayment(PaymentRequest request) {
        return random.nextInt(100) < 80;
    }
}

class RazorPayBankingSystem implements BankingSystem {

    private Random random = new Random();

    @Override
    public boolean processPayment(PaymentRequest request) {
        return random.nextInt(100) < 70;
    }
}

abstract class PaymentGateway {

    protected BankingSystem bankingSystem;

    public boolean processPayment(PaymentRequest request) {

        if (!validatePaymentRequest(request)) {
            System.out.printf("[PaymentGateway] Validation failed for %s%n", request.sender);
            return false;
        }

        if (!initiatePayment(request)) {
            System.out.printf("[PaymentGateway] Payment initiation failed for %s%n", request.sender);
            return false;
        }

        if (!confirmPayment(request)) {
            System.out.printf("[PaymentGateway] Payment confirmation failed for %s%n", request.sender);
            return false;
        }

        return true;
    }

    protected abstract boolean validatePaymentRequest(PaymentRequest request);

    protected abstract boolean initiatePayment(PaymentRequest request);

    protected abstract boolean confirmPayment(PaymentRequest request);
}

class PaytmGatewayImpl extends PaymentGateway {

    public PaytmGatewayImpl() {
        this.bankingSystem = new PaytmBankingSystem();
    }

    @Override
    protected boolean validatePaymentRequest(PaymentRequest request) {

        System.out.printf("[Paytm] Validating payment for %s%n", request.sender);

        if (request.amount <= 0 || !"INR".equals(request.currency)) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {

        System.out.printf("[Paytm] Initiating payment for %s%n", request.sender);

        return bankingSystem.processPayment(request);
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {

        System.out.printf("[Paytm] Payment confirmed for %s%n", request.sender);

        return true;
    }
}

class RazorpayGatewayImpl extends PaymentGateway {

    public RazorpayGatewayImpl() {
        this.bankingSystem = new RazorPayBankingSystem();
    }

    @Override
    protected boolean validatePaymentRequest(PaymentRequest request) {

        System.out.printf("[Razorpay] Validating payment for %s%n", request.sender);

        return request.amount > 0;
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {

        System.out.printf("[Razorpay] Initiating payment for %s%n", request.sender);

        return bankingSystem.processPayment(request);
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {

        System.out.printf("[Razorpay] Payment confirmed for %s%n", request.sender);

        return true;
    }
}

class PaymentGatewayProxy extends PaymentGateway {

    private PaymentGateway realPaymentGateway;
    private int retryCount;

    public PaymentGatewayProxy(PaymentGateway realPaymentGateway, int retryCount) {
        this.realPaymentGateway = realPaymentGateway;
        this.retryCount = retryCount;
    }

    @Override
    public boolean processPayment(PaymentRequest request) {

        for (int i = 1; i <= retryCount; i++) {

            if (realPaymentGateway.processPayment(request)) {
                return true;
            }

            System.out.printf("[Proxy] Retry %d for %s%n", i, request.sender);
        }

        System.out.printf("[Proxy] Payment failed after %d retries for %s%n",
                retryCount,
                request.sender);

        return false;
    }

    @Override
    protected boolean validatePaymentRequest(PaymentRequest request) {
        return realPaymentGateway.validatePaymentRequest(request);
    }

    @Override
    protected boolean initiatePayment(PaymentRequest request) {
        return realPaymentGateway.initiatePayment(request);
    }

    @Override
    protected boolean confirmPayment(PaymentRequest request) {
        return realPaymentGateway.confirmPayment(request);
    }
}

enum PaymentGatewayType {
    PAYTM,
    RAZORPAY
}

class GatewayFactory {

    private GatewayFactory() {
    }

    public static PaymentGateway createPaymentGateway(PaymentGatewayType type) {

        switch (type) {

            case PAYTM:
                return new PaymentGatewayProxy(new PaytmGatewayImpl(), 3);

            case RAZORPAY:
                return new PaymentGatewayProxy(new RazorpayGatewayImpl(), 2);

            default:
                throw new IllegalArgumentException("Invalid Gateway");
        }
    }
}

class PaymentService {

    private static final PaymentService instance = new PaymentService();

    private PaymentGateway paymentGateway;

    private PaymentService() {
    }

    public static PaymentService getInstance() {
        return instance;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public boolean processPayment(PaymentRequest request) {

        if (paymentGateway == null) {
            throw new IllegalStateException("Payment Gateway not set.");
        }

        return paymentGateway.processPayment(request);
    }
}

class PaymentController {

    private static final PaymentController instance = new PaymentController();

    private PaymentController() {
    }

    public static PaymentController getInstance() {
        return instance;
    }

    public boolean processPayment(PaymentRequest request, PaymentGatewayType type) {

        PaymentGateway paymentGateway = GatewayFactory.createPaymentGateway(type);

        PaymentService.getInstance().setPaymentGateway(paymentGateway);

        return PaymentService.getInstance().processPayment(request);
    }
}

public class PaymentGatewayDemo {

    public static void main(String[] args) {

        PaymentRequest request1 =
                new PaymentRequest("Akhil", "Nikhil", 100, "INR");

        boolean result1 =
                PaymentController.getInstance().processPayment(
                        request1,
                        PaymentGatewayType.PAYTM);

        System.out.println("\nProcessing via Paytm");
        System.out.println("Payment Result : " + result1);

        System.out.println("--------------------------------");

        PaymentRequest request2 =
                new PaymentRequest("Nikhil", "Akhil", 200, "USD");

        boolean result2 =
                PaymentController.getInstance().processPayment(
                        request2,
                        PaymentGatewayType.RAZORPAY);

        System.out.println("\nProcessing via Razorpay");
        System.out.println("Payment Result : " + result2);
    }
}