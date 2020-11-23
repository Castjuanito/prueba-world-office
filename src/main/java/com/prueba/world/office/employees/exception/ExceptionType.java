package com.prueba.world.office.employees.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    GENERAL_EXCEPTION("general.exception"),
    FILE_EXCEPTION("file.exception"),
    VALIDATION_EXCEPTION("validation.exception"),
    STORE_FILE_EXCEPTION("store.file.exception");

    String value;
}
