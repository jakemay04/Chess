package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;


public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest req) throws DataAccessException {
        userDAO.insertUser(new UserData(req.username(), req.email(), req.password()));
        String token = UUID.randomUUID().toString();
        authDAO.insertAuth(new AuthData(token,req.username()));
        return new RegisterResult(req.username(), token);
    }

    public LoginResult login(LoginRequest req) throws DataAccessException {
        UserData user = userDAO.getUser(req.username());
        if (!req.password().equals(user.password())) {
            throw new DataAccessException("Invalid password");
        }
        String token = UUID.randomUUID().toString();
        authDAO.insertAuth(new AuthData(token,req.username()));
        return new LoginResult(req.username(), token);
    }

    public void logout(LogoutRequest req) throws DataAccessException {
        AuthData auth = authDAO.getAuth(req.authToken()); //verify token in db
        System.out.println("Found auth: " + auth);
        authDAO.deleteAuth(req.authToken());
    }


}
