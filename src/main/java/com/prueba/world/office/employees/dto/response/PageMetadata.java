package com.prueba.world.office.employees.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageMetadata {
    private final int pageSize;
    private final int totalPages;
    private final int pageNumber;
}
