package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

record RegisterRequest(String username, String email, String password){};
record RegisterResult(String username, String authToken){};

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest req) throws DataAccessException {
        userDAO.insertUser(new UserData(req.username(),req.email(), req.password()));
        String token = UUID.randomUUID().toString();
        authDAO.insertAuth(new AuthData(req.username(), token));
        return new RegisterResult(req.username(),token);
    }
}
