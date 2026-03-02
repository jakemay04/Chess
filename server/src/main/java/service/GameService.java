package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.GameData;

import java.util.Collection;

;

public class GameService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResult createGame(CreateGameRequest req) throws DataAccessException {
        //get auth data
        authDAO.getAuth(req.authToken());
        if (req.gameName() == null) {
            throw new DataAccessException("bad request");
        }
        //create blank gamedata obj
        GameData game = new GameData(0,null,null,req.gameName(),new ChessGame());
        int id = gameDAO.insertGame(game);
        return new CreateGameResult(id);
    }

    public ListGamesResult listGames(ListGamesRequest req) throws DataAccessException {
        authDAO.getAuth(req.authToken());
        return new ListGamesResult(gameDAO.gameList());
    }

    public void joinGame(JoinGameRequest req) throws DataAccessException {
        AuthData user = authDAO.getAuth(req.authToken());
        String username = user.username();

        if (username == null || req.playerColor() == null) {
            throw new DataAccessException("bad request");
        }
        gameDAO.getGame(req.gameID());
        gameDAO.updateGame(req.playerColor(), req.gameID(), username);
    }
}
