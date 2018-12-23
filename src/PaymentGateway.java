interface PaymentService {
    Object pay();
}

interface FraudCheckService {
    Object fraudCheck() throws PaymentException;
}

interface ThreeDSecureCheckService {
    Object threeDCheck();
}

interface PaymentProcessService {
    Object process() throws PaymentException;
}

class PaymentException extends Exception {

    public PaymentException(Object msg) {
        System.out.println(msg.toString());
    }
}

class StandardPayment {
    private int transId;
    private double amt;
    private String currencyType;
    private String endPointUrl;
    private String returnEndPointUrl;
    private String notifyEndPointUrl;
    private String submitMethod;
    private String merchantKey;

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getEndPointUrl() {
        return endPointUrl;
    }

    public void setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
    }

    public String getReturnEndPointUrl() {
        return returnEndPointUrl;
    }

    public void setReturnEndPointUrl(String returnEndPointUrl) {
        this.returnEndPointUrl = returnEndPointUrl;
    }

    public String getNotifyEndPointUrl() {
        return notifyEndPointUrl;
    }

    public void setNotifyEndPointUrl(String notifyEndPointUrl) {
        this.notifyEndPointUrl = notifyEndPointUrl;
    }

    public String getSubmitMethod() {
        return submitMethod;
    }

    public void setSubmitMethod(String submitMethod) {
        this.submitMethod = submitMethod;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }
}

class AbcPay extends StandardPayment implements PaymentService, PaymentProcessService, ThreeDSecureCheckService {

    public AbcPay() {
    }

    public Object pay() {
        return this;
    }

    public Object threeDCheck() {

        return this;
    }

    public Object process() {
        this.threeDCheck();
        this.pay();

        return this;
    }

}

class XyzPay extends StandardPayment implements PaymentService, PaymentProcessService, FraudCheckService {

    protected int customePorperties;

    public Object fraudCheck() throws PaymentException {
        if (0 != this.getTransId()) {
            throw new PaymentException("Id required");
        }
        if (this.getAmt() <= 0) {
            throw new PaymentException("amt required");
        }

        return this;
    }

    public Object pay() {//TODO
        return this;
    }

    public Object process() throws PaymentException {
        this.fraudCheck();
        this.pay();

        return this;
    }
}

class EtcPay extends StandardPayment implements PaymentService, PaymentProcessService {

    public Object process() throws PaymentException {
        return this.pay();
    }

    public Object pay() {//TODO
        return this;
    }

}

class PaymentGateway {
    public Object takePayment(PaymentProcessService paymentProcessService) throws PaymentException {
        return paymentProcessService.process();
    }
}

//make db call for specific payment gateway & its setting from db
class TestPayment {
    public static void main(String[] args) {
        try {
            PaymentGateway paymentGateway = new PaymentGateway();
            paymentGateway.takePayment(new AbcPay());
            paymentGateway.takePayment(new EtcPay());
            paymentGateway.takePayment(new XyzPay());

        } catch (PaymentException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("All done!");
        }
    }
}



