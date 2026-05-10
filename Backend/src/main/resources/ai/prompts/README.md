# AI Prompts

This folder contains the prompt templates used to guide the AI behavior during the game.

## Main prompt

### `game-prompt.st`
This is the main prompt template used by the backend to generate the AI next step.

It defines:
- the default AI persona;
- personality overrides injected from the selected `PersonalityType`;
- the current game state;
- the prior session history;
- the gameplay rules the AI must follow;
- the output style for questions, guesses, and final messages.

## Purpose

The prompt is responsible for shaping:
- how the AI speaks;
- how the AI behaves in each phase of the game;
- how the AI uses the available session context.

It helps ensure that the AI:
- stays in character;
- asks only one question at a time;
- avoids repeating previous questions;
- makes a final guess when the round reaches its limit;
- reacts consistently to win/lose outcomes.

## Important note

The prompt does not control the whole game flow by itself.

The backend still decides:
- the official session state;
- how many questions have already been used;
- when the AI is in question mode or guess mode;
- how the session is updated after each answer.

In other words:
- the prompt controls wording and behavior style;
- the backend controls the game rules and state transitions.

---

# Prompts da IA

Esta pasta contém os templates de prompt usados para orientar o comportamento da IA durante o jogo.

## Prompt principal

### `game-prompt.st`
Este é o template principal usado pelo backend para gerar o próximo passo da IA.

Ele define:
- a persona base da IA;
- os overrides de personalidade vindos do `PersonalityType` selecionado;
- o estado atual do jogo;
- o histórico anterior da sessão;
- as regras de jogo que a IA deve seguir;
- o estilo de output para perguntas, guesses e mensagens finais.

## Objetivo

O prompt é responsável por moldar:
- a forma como a IA fala;
- a forma como a IA se comporta em cada fase do jogo;
- a forma como a IA usa o contexto disponível da sessão.

Ajuda a garantir que a IA:
- mantém a personagem;
- faz apenas uma pergunta de cada vez;
- evita repetir perguntas anteriores;
- faz uma guess final quando a ronda atinge o limite;
- reage de forma consistente aos cenários de vitória e derrota.

## Nota importante

O prompt não controla sozinho todo o fluxo do jogo.

O backend continua a decidir:
- o estado oficial da sessão;
- quantas perguntas já foram usadas;
- quando a IA está em modo de pergunta ou em modo de guess;
- como a sessão é atualizada após cada resposta.

Ou seja:
- o prompt controla a forma de falar e o estilo de comportamento;
- o backend controla as regras do jogo e as transições de estado.
