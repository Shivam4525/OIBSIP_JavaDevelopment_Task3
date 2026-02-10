package atmmodel;

public class ATMUser {
    private String userId;
    private String pin;

    public ATMUser(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

