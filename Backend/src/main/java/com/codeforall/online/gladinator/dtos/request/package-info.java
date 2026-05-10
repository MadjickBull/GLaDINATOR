/**
 * Request DTOs of the application.
 *
 * This package contains the classes that represent the data sent by the frontend
 * to the backend through the REST API.
 *
 * Request DTOs are used to:
 * - carry user input into the application;
 * - validate required request fields;
 * - keep the controller layer decoupled from the internal models.
 *
 * Main DTOs:
 *
 * AnswerRequestDto
 * - represents the user's answer to an AI question or guess;
 * - carries the selected AnswerType value.
 *
 * ChoosePersonalityRequestDto
 * - represents the user's choice of AI personality;
 * - carries the selected PersonalityType value.
 *
 * Validation:
 * - request DTOs may use Jakarta validation annotations such as @NotNull;
 * - validation is triggered in the controllers through @Valid.
 *
 * -----------------------------------------------------------------------------
 *
 * DTOs de request da aplicação.
 *
 * Este package contém as classes que representam os dados enviados pelo frontend
 * para o backend através da API REST.
 *
 * Os request DTOs servem para:
 * - transportar input do utilizador para a aplicação;
 * - validar campos obrigatórios do pedido;
 * - manter a camada de controllers desacoplada dos modelos internos.
 *
 * DTOs principais:
 *
 * AnswerRequestDto
 * - representa a resposta do utilizador a uma pergunta ou guess da IA;
 * - transporta o valor selecionado de AnswerType.
 *
 * ChoosePersonalityRequestDto
 * - representa a escolha da personalidade da IA feita pelo utilizador;
 * - transporta o valor selecionado de PersonalityType.
 *
 * Validação:
 * - os request DTOs podem usar anotações Jakarta de validação, como @NotNull;
 * - a validação é ativada nos controllers através de @Valid.
 */
package com.codeforall.online.gladinator.dtos.request;
