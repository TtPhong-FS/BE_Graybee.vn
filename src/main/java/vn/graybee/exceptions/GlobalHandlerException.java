package vn.graybee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.graybee.utils.DatetimeFormatted;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerInValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                }
        );
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessCustomException.class)
    public Map<String, String> handlerBusinessException(BusinessCustomException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ex.getField(), ex.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ProblemDetail handlerResourceNotFoundException(CustomNotFoundException ex) {
        CustomNotFoundProblemDetail problemDetail = new CustomNotFoundProblemDetail(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getField());
        problemDetail.setProperty(ex.getField(), ex.getMessage());
        problemDetail.setProperty("timestamp", DatetimeFormatted.formatted_datetime());
        return problemDetail;
    }

}
