package vn.graybee.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BasicMessageResponse<T> {

    private int status;

    private String message;

    private T data;


}
