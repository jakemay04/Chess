package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryGameDAO implements UserDAO{
    private final Map<String,List<String>> users = new HashMap<String,List<String>>();

    public void insertUser(UserData u) throws DataAccessException {
        if (u.email() != null || users.containsKey(u.email())) {
            users.put(u.email(),new ArrayList<>());
            users.get(u.email()).add(u.username());
            users.get(u.email()).add(u.password());


        }
    }
}
