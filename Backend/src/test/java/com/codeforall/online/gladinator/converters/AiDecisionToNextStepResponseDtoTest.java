package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link AiDecisionToNextStepResponseDto}.
 */
public class AiDecisionToNextStepResponseDtoTest {

    private AiDecisionToNextStepResponseDto converter;

    @Before
    public void setUp() {
        converter = new AiDecisionToNextStepResponseDto();
    }

    /**
     * Verifies that the AI decision fields are copied into the response DTO.
     */
    @Test
    public void convertShouldMapAiDecisionFields() {
        AiDecision aiDecision = new AiDecision(AiDecisionType.QUESTION, "Is your character human?");

        NextStepResponseDto result = converter.convert(aiDecision);

        assertEquals(AiDecisionType.QUESTION, result.getType());
        assertEquals("Is your character human?", result.getContent());
    }
}
