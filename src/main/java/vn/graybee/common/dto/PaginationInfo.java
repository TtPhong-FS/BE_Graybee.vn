package vn.graybee.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PaginationInfo {

    private int currentPage;

    private int totalPages;

    private long totalItems;

    private int pageSize;

}
