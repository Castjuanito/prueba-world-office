package com.prueba.world.office.employees.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.prueba.world.office.employees.util.DateUtils;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseError {
    private final Date timeStamp;
    private final String message;
    private final List<String> details;

    public static ResponseError createErrorFromException(Exception ex, Optional<List<String>> details) {
        ResponseErrorBuilder responseError = ResponseError.builder()
                .timeStamp(DateUtils.now())
                .message(ex.getMessage());
        details.ifPresent(value -> responseError.details(value));
        return responseError.build();
    }
}
