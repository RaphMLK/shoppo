package fr.shoppo.mainmsinterface.infrastructure.mapper;

import org.springframework.security.core.userdetails.User;

public class UserDetailsMapper {

    public static fr.shoppo.mainmsinterface.domain.bo.User
    toUser(User userDetails){
        fr.shoppo.mainmsinterface.domain.bo.User user =
                new fr.shoppo.mainmsinterface.domain.bo.User();
        user.setEmail(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        return user;
    }

    public static fr.shoppo.mainmsinterface.domain.bo.User
    toUserFromPrincipal(Object principal){
        return toUser((User) principal);
    }
}
