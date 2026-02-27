package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryUserDAO implements UserDAO{
    private Map<String, List<String>> users = new HashMap<String, List<String>>();

    public void clear() {
        users = null;
    }

    public void insertUser(UserData u) throws DataAccessException {
        if (u.username() != null || users.containsKey(u.username())) {
            users.put(u.username(), new ArrayList<>());
            users.get(u.username()).add(u.email());
            users.get(u.username()).add(u.password());
        }
        else {
            throw new DataAccessException("User already exists");
        }
    }

    public UserData getUser(UserData u) throws DataAccessException {
        if ((u.username() != null && users.containsKey(u.username()))) {
            return new UserData(u.username(), users.get(u.username()).getFirst(), users.get(u.username()).getLast());
        }
        else {
            throw new DataAccessException("Invalid login");
        }
    }
}
