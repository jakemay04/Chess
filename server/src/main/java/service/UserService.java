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
        if (req.username() == null || req.password() == null || req.email() == null) {
            throw new DataAccessException("Error: bad request");
        }
        try {
            userDAO.getUser(req.username());
            throw new DataAccessException("Error: already taken");
        } catch (DataAccessException e) {
            if (e.getMessage().equals("Error: already taken")) {
                throw e;
            }
        }
        userDAO.insertUser(new UserData(req.username(), req.password(), req.email()));
        String token = UUID.randomUUID().toString();
        authDAO.insertAuth(new AuthData(token,req.username()));
        return new RegisterResult(req.username(), token);
    }

    public LoginResult login(LoginRequest req) throws DataAccessException {
        UserData user = userDAO.getUser(req.username());
        if (!req.password().equals(user.password())) {
            throw new DataAccessException("Error: unauthorized");
        }
        String token = UUID.randomUUID().toString();
        authDAO.insertAuth(new AuthData(token,req.username()));
        return new LoginResult(req.username(), token);
    }

    public void logout(LogoutRequest req) throws DataAccessException {
        AuthData auth = authDAO.getAuth(req.authToken()); //verify token in db
        authDAO.deleteAuth(req.authToken());
    }


}
