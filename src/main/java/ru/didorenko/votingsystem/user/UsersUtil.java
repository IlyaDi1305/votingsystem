package ru.didorenko.votingsystem.user;

import lombok.experimental.UtilityClass;
import ru.didorenko.votingsystem.user.model.Role;
import ru.didorenko.votingsystem.user.model.User;
import ru.didorenko.votingsystem.user.to.UserTo;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}