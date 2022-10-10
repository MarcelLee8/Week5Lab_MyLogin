package services;

import models.User;

/**
 *
 * @author marce
 */
public class AccountService {

    public AccountService() {
    }

    public User login(String userInput, String passInput) {

        if ((userInput.equals("abe") || userInput.equals("barb"))
                && passInput.equals("password")) {

            User newUser = new User(userInput, null);
            return newUser;
        }
        else {
            return null;
        }
    }
}
