package com.codeforall.online.gladinator.converters;

import static org.junit.Assert.*;

import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import org.junit.Test;

/**
 * Unit tests for the AI decision converter.
 */
public class AiDecisionToNextStepResponseDtoTest {

  /**
   * Verifies that the converter copies the AI decision fields into the response DTO.
   */
  @Test
  public void convertShouldMapDecisionFields() {
    AiDecisionToNextStepResponseDto converter = new AiDecisionToNextStepResponseDto();

    NextStepResponseDto dto = converter.convert(new AiDecision(AiDecisionType.QUESTION, "Question text"));

    assertEquals(AiDecisionType.QUESTION, dto.getType());
    assertEquals("Question text", dto.getContent());
  }
}
