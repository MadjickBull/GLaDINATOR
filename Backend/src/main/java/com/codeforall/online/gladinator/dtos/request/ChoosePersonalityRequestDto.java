package com.codeforall.online.gladinator.dtos.request;

import com.codeforall.online.gladinator.model.enums.PersonalityType;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO used to choose or update the AI personality.
 */
public class ChoosePersonalityRequestDto {

    @NotNull
    private PersonalityType personalityType;

    public ChoosePersonalityRequestDto() {
    }

    public ChoosePersonalityRequestDto(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }
}
