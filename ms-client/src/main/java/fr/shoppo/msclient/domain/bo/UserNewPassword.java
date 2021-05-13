package fr.shoppo.msclient.domain.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserNewPassword {

    private String email;

    private String password;

    public UserNewPassword(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserNewPassword() {
        /*data binding*/
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserNewPassword)) return false;

        UserNewPassword that = (UserNewPassword) o;

        return new EqualsBuilder()
                .append(getEmail(), that.getEmail())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getEmail())
                .toHashCode();
    }
}
