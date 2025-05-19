package vn.graybee.exceptions;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.graybee.utils.DatetimeFormatted;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
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


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleGlobalException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Đã xảy ra lỗi không mong muốn");
        errorMap.put("message", ex.getMessage());
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

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(RedisConnectionFailureException.class)
    public ProblemDetail handleConnectFailed(RedisConnectionFailureException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                ex.getMessage()
        );
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setTitle("Unconnected to services");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleConnectFailed(HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Request body is missing or malformed."
        );
        problemDetail.setTitle("Bad request");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingRequestParams(MissingServletRequestParameterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Required request parameter is missing."
        );
        problemDetail.setTitle("Bad request");
        return problemDetail;
    }


}
