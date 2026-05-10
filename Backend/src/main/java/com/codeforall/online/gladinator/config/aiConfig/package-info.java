/**
 * Spring factory classes for OpenAI-related beans.
 *
 * This package contains FactoryBean implementations responsible for creating
 * the OpenAI integration objects used by the application.
 *
 * These factories are referenced by the Spring XML configuration and help
 * keep the OpenAI setup separated from service and controller logic.
 *
 * Main classes:
 *
 * OpenAiApiFactory
 * - creates the OpenAiApi client using the configured API key.
 *
 * OpenAiChatOptionsFactory
 * - creates the default OpenAI chat options, such as model and temperature.
 *
 * OpenAiChatModelFactory
 * - creates the OpenAI chat model by combining the API client
 *   with the configured chat options.
 *
 * Responsibility overview:
 * - this package belongs to the Spring configuration layer;
 * - it wires OpenAI-related beans for the AI service;
 * - it does not contain game rules or business logic.
 *
 * -----------------------------------------------------------------------------
 *
 * Classes factory Spring para beans relacionados com a OpenAI.
 *
 * Este package contém implementações de FactoryBean responsáveis por criar
 * os objetos de integração com a OpenAI usados pela aplicação.
 *
 * Estas factories são referenciadas pela configuração XML do Spring e ajudam
 * a manter a configuração da OpenAI separada da lógica de services e controllers.
 *
 * Classes principais:
 *
 * OpenAiApiFactory
 * - cria o cliente OpenAiApi usando a API key configurada.
 *
 * OpenAiChatOptionsFactory
 * - cria as opções base do chat da OpenAI, como o modelo e a temperatura.
 *
 * OpenAiChatModelFactory
 * - cria o modelo de chat da OpenAI combinando o cliente da API
 *   com as opções configuradas de chat.
 *
 * Em termos de responsabilidade:
 * - este package pertence à camada de configuração Spring;
 * - faz o wiring dos beans relacionados com a OpenAI para o serviço de IA;
 * - não contém regras do jogo nem lógica de negócio.
 */
package com.codeforall.online.gladinator.config.aiConfig;