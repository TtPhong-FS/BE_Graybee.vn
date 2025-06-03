package vn.graybee.common.exception;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import vn.graybee.auth.exception.AuthException;
import vn.graybee.common.utils.DatetimeFormatted;
import vn.graybee.common.utils.MessageSourceUtil;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    private final MessageSourceUtil messageSource;

    public GlobalHandlerException(MessageSourceUtil messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AuthException.class)
    public ProblemDetail handleAuthException(AuthException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
        problemDetail.setProperty("code", ex.getErrorCode());
        problemDetail.setTitle(messageSource.get("common.error.401.title"));

        return problemDetail;
    }

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

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.get("common.error.500.detail")
        );
        problemDetail.setProperty("description", ex.getMessage());
        problemDetail.setTitle(messageSource.get("common.error.500.title"));
        return problemDetail;
    }


    @ExceptionHandler(CustomNotFoundException.class)
    public ProblemDetail handlerResourceNotFoundException(CustomNotFoundException ex) {
        CustomNotFoundProblemDetail problemDetail = new CustomNotFoundProblemDetail(HttpStatus.NOT_FOUND.value(), messageSource.get("common.error.404.title"));
        problemDetail.setProperty(ex.getField(), ex.getMessage());
        problemDetail.setProperty("timestamp", DatetimeFormatted.formatted_datetime());
        return problemDetail;
    }

    @ExceptionHandler({
            CannotCreateTransactionException.class,
            RedisConnectionFailureException.class,
            ElasticsearchException.class,
            ResourceAccessException.class
    })
    public ProblemDetail handleConnectFailed(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                messageSource.get("common.error.503.detail")
        );
        problemDetail.setTitle(messageSource.get("common.error.503.title"));
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleConnectFailed(HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.get("common.error.400.detail")
        );
        problemDetail.setTitle(messageSource.get("common.error.400.title"));
        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingRequestParams(MissingServletRequestParameterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.get("common.error.400.detail")
        );
        problemDetail.setTitle(messageSource.get("common.error.400.title"));
        return problemDetail;
    }


}
