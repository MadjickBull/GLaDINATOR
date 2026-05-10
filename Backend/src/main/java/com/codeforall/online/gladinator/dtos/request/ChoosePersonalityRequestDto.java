package com.codeforall.online.gladinator.dtos.request;

import com.codeforall.online.gladinator.model.enums.PersonalityType;

/**
 * Request DTO used to choose or update the AI personality.
 */
public class ChoosePersonalityRequestDto {

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
