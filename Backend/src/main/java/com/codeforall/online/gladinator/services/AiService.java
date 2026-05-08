package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameSession;

public interface AiService {

    //Dada a sessão atual do jogo, devolve a próxima decisão da IA.
        //AiDecision representa a decisão da IA - tipo de decisão e conteúdo
        //getNextStep: perguntar à IA qual o próximo passo
        //para isso temos de lhe passar a sessão atual do jogo, onde está:
            //o contexto
            //quantas vidas restam
            //quantas perguntas já foram feitas
            //qual é a personalidade
            //qual é o estado da sessão
            //o histórico de respostas
    AiDecision getNextStep (GameSession gameSession);


}
