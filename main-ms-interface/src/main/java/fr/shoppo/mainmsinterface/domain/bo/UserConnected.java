package fr.shoppo.mainmsinterface.domain.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserConnected {

    String token;

    String role;

    public UserConnected() {
        /*binding*/
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserConnected)) return false;

        UserConnected that = (UserConnected) o;

        return new EqualsBuilder()
                .append(getToken(), that.getToken())
                .append(getRole(), that.getRole())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getToken())
                .append(getRole())
                .toHashCode();
    }
}
