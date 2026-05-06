package codeforall.com.online.gladinator.dtos.request;

import codeforall.com.online.gladinator.model.enums.PersonalityType;

//Serve para mudar a personalidade durante a sessão
public class UpdatePersonalityRequestDto {

    private PersonalityType personalityType;

    public UpdatePersonalityRequestDto() {
    }

    public UpdatePersonalityRequestDto(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

}
