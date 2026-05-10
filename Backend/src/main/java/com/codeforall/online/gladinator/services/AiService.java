package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameSession;

/**
 * Common interface for AI services, provides methods to generate
 * structured AI decisions based on the current game session.
 */
public interface AiService {

    /**
     * Returns the next AI decision based on the current game session state.
     *
     * @param gameSession the current game session
     * @return the next decision produced by the AI
     */
    AiDecision getNextStep(GameSession gameSession);
}
