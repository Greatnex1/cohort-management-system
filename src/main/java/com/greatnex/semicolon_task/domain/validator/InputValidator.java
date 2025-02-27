package com.greatnex.semicolon_task.domain.validator;

import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import org.apache.commons.lang3.StringUtils;

public class InputValidator {

    public static void validateInput(String input, String fieldName) {
        if(StringUtils.isEmpty(input)|| StringUtils.isBlank(input)|| StringUtils.isEmpty(input.trim())
                ||input.equals(ErrorMessages.UNDEFINED)) {
            throw new IllegalArgumentException(String.format(ErrorMessages.EMPTY_INPUT_ERROR, fieldName));

        }
    }

    public static void validateInput(String input) {
        if(StringUtils.isEmpty(input)|| StringUtils.isBlank(input)|| StringUtils.isEmpty(input.trim())
                ||input.equals(ErrorMessages.UNDEFINED)) {
            throw new IllegalArgumentException(String.format(ErrorMessages.EMPTY_INPUT_ERROR));

        }
    }
}
