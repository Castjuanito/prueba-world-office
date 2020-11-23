package com.prueba.world.office.employees.exception;

import com.prueba.world.office.employees.config.PropertiesConfig;
import com.prueba.world.office.employees.exception.custom_exceptions.EntityNotFoundException;
import com.prueba.world.office.employees.exception.custom_exceptions.FileException;
import com.prueba.world.office.employees.exception.custom_exceptions.GeneralException;
import com.prueba.world.office.employees.exception.custom_exceptions.ValidationException;
import java.text.MessageFormat;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppException {
    private static PropertiesConfig propertiesConfig;

    @Autowired
    public AppException(PropertiesConfig propertiesConfig) {
        AppException.propertiesConfig = propertiesConfig;
    }

    public static RuntimeException throwException(String template, String... args) {
        return new RuntimeException(formatExceptionMessage(template, args));
    }

    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
        String messageTemplateName = entityType.name() + "." + exceptionType.getValue();
        return throwException(exceptionType, messageTemplateName.toLowerCase(), args);
    }

    private static RuntimeException throwException(ExceptionType exceptionType, String template, String... args) {
        String exceptionMessage = formatExceptionMessage(template, args);
        switch (exceptionType) {
            case FILE_EXCEPTION:
                return new FileException(exceptionMessage);

            case ENTITY_NOT_FOUND:
                return new EntityNotFoundException(exceptionMessage);

            case VALIDATION_EXCEPTION:
                return new ValidationException(exceptionMessage);

            case GENERAL_EXCEPTION:
                return new GeneralException(exceptionMessage);

        }
        return new RuntimeException(exceptionMessage);
    }

    private static String formatExceptionMessage(String template, String... args) {
        Optional<String> templateMessage = Optional.ofNullable(propertiesConfig.getConfigValue(template));
        return templateMessage
                .map(value -> MessageFormat.format(value, (Object[]) args))
                .orElse(String.format(template, (Object[]) args));
    }
}
