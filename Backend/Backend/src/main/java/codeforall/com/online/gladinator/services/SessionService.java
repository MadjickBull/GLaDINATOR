package codeforall.com.online.gladinator.services;

import codeforall.com.online.gladinator.model.enums.PersonalityType;
import codeforall.com.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Service;

//gestão técnica da sessão
public interface SessionService {

    GameSession createSession ();
    GameSession getSessionById(String sessionId);
    void updateSession(GameSession gameSession);
    void deleteSessionById(String sessionId);
    boolean sessionExists(String sessionId);

}
