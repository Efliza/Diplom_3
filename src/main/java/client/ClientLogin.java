package client;

import lombok.Data;

@Data
public class ClientLogin {

    private String email;
    private String password;

    public ClientLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private ClientLogin() {
    }
}
