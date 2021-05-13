package fr.shoppo.mainmsinterface.domain.bo;

public class ConnectedUser {

    Object ConnectedUser;

    String token;

    String role;

    public Object getConnectedUser() {
        return ConnectedUser;
    }

    public void setConnectedUser(Object connectedUser) {
        ConnectedUser = connectedUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
