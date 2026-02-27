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

    public UserData getUser(String u) throws DataAccessException {
        if ((u != null && users.containsKey(u))) {
            return new UserData(u, users.get(u).getFirst(), users.get(u).getLast());
        }
        else {
            throw new DataAccessException("Invalid login");
        }
    }
}
