package vn.graybee.common.utils;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;

public class MessageBuilder {

    public static <T> BasicMessageResponse<T> ok(T data, String message) {
        return new BasicMessageResponse<>(200, message, data);
    }

    public static <T> MessageResponse<T> ok(T data, String message, PaginationInfo paginationInfo, SortInfo sortInfo) {
        return new MessageResponse<>(200, message, data, paginationInfo, sortInfo);
    }

}
