package vn.graybee.messages;

import vn.graybee.messages.other.PaginationInfo;
import vn.graybee.messages.other.SortInfo;

public class MessageResponse<T> {

    private int status;

    private String message;

    private T data;

    private PaginationInfo paginationInfo;

    private SortInfo sortInfo;

    public MessageResponse(int status, String message, T data, PaginationInfo paginationInfo, SortInfo sortInfo) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.paginationInfo = paginationInfo;
        this.sortInfo = sortInfo;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }

    public SortInfo getSortInfo() {
        return sortInfo;
    }

    public void setSortInfo(SortInfo sortInfo) {
        this.sortInfo = sortInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
