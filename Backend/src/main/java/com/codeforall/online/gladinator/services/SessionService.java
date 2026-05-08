package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.model.session.GameSession;

//gestão técnica da sessão
public interface SessionService {

    GameSession createSession ();
    GameSession getSessionById(String sessionId);
    void updateSession(GameSession gameSession);
    void deleteSessionById(String sessionId);
    boolean sessionExists(String sessionId);

}
