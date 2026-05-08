package com.codeforall.online.gladinator.storage;

import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
//classe de estado/armazenamento em memória das sessões
public class InMemorySessionStore {

    private final Map<String, GameSession> sessions;

    public InMemorySessionStore() {
        this.sessions = new HashMap<>();
    }

    //Guarda ou atualiza a sessão no Map
    public void save(GameSession gameSession) {
        sessions.put(gameSession.getSessionId(), gameSession);
    }

    //Vai buscar uma sessão pelo id
    public GameSession findById(String sessionId) {
        return sessions.get(sessionId);
    }

    //verificar se já existe uma sessão com aquele id.
    public boolean exists(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    //remove uma sessão do store
    public void delete(String sessionId) {
        sessions.remove(sessionId);
    }

    //não é essencial para o MVP, mas pode dar jeito para testes
    public Collection<GameSession> findAll() {
        return sessions.values();
    }
}
