package atmmodel;

import javax.swing.SwingUtilities;

public class ATMMain {
    public static void main(String[] args) {

        ATMUser user = new ATMUser("admin", "1234");
        ATMService service = new ATMService();

        SwingUtilities.invokeLater(() -> new ATMGUI(user, service));
    }
}
