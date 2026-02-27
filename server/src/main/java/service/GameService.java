package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;


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

        gameDAO.insertGame(req.UserData, game) {

        }
    }
}
