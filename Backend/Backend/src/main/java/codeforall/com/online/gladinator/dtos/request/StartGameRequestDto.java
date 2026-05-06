package codeforall.com.online.gladinator.dtos.request;

import codeforall.com.online.gladinator.model.enums.PersonalityType;

//Serve para iniciar o jogo com a personalidade escolhida.
public class StartGameRequestDto {

    private PersonalityType personalityType;

    public StartGameRequestDto() {}

    public StartGameRequestDto(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }
}
