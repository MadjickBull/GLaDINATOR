GLaDINATOR is a full-stack web application inspired by the classic Akinator-style guessing game, reimagined through the personality of GLaDOS from *Portal*.

The player thinks of a character, and the system attempts to identify it through a sequence of AI-generated YES/NO questions, culminating in a final guess.

The experience combines AI-driven dialogue, session-based game logic, a custom text-to-speech engine, and an immersive Aperture Science-inspired UI.

---

## Features

- AI-powered character guessing gameplay  
- GLaDOS-style personality and dialogue system  
- Dynamic question generation with controlled game flow  
- Final AI guess with win/loss outcome  
- Session-based state management  
- Local text-to-speech integration (GLaDOS voice engine)  
- Single Page Application frontend  
- REST API backend architecture  
- Backend unit and integration tests  

---

## Tech Stack

**Backend**
- Java
- Spring MVC
- Maven
- Tomcat 10
- OpenAI API

**Frontend**
- HTML
- CSS
- JavaScript (SPA architecture)

**Voice / TTS**
- Python
- Flask
- Flask-CORS
- Custom GLaDOS TTS engine

---

## How It Works

1. A game session is created when the user starts a new game  
2. The backend initializes session state in memory  
3. OpenAI generates the next question or final guess  
4. The frontend renders the response in the UI  
5. The response can optionally be sent to the local TTS engine  
6. The user answers YES or NO  
7. The backend updates the game state accordingly  
8. The game ends when the AI correctly guesses the character or fails

Use the provided `config.properties.example` as a reference and configure the following values:

- OpenAI API key  
- OpenAI model selection  

This file is required for the backend to communicate with the OpenAI API.

---

### Deploying to Tomcat

To install glados-TTS, head over [Here](https://github.com/R2D2FISH/glados-tts) for the full installation and edit.

To deploy the application using Maven and Tomcat, run:

```bash
mvn tomcat7:undeploy clean tomcat7:deploy


