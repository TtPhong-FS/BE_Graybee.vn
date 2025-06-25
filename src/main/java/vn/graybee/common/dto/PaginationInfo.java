package vn.graybee.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfo {

    private int currentPage;

    private int totalPages;

    private long totalItems;

    private int pageSize;

}
