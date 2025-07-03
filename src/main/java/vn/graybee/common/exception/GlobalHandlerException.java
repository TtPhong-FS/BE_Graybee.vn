package vn.graybee.common.exception;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.NoHandlerFoundException;
import vn.graybee.auth.exception.AuthException;
import vn.graybee.common.utils.DatetimeFormatted;
import vn.graybee.common.utils.MessageSourceUtil;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    private final Logger logger = LoggerFactory.getLogger(GlobalHandlerException.class);

    private final MessageSourceUtil messageSource;

    public GlobalHandlerException(MessageSourceUtil messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AuthException.class)
    public ProblemDetail handleAuthException(AuthException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.UNAUTHORIZED
        );
        problemDetail.setProperty(ex.getErrorCode(), ex.getMessage());
        problemDetail.setTitle(messageSource.get("common.error.401.title"));

        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerInValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    String message = error.getDefaultMessage();
                    errorMap.put(error.getField(), message);
                }
        );
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessCustomException.class)
    public ResponseEntity<Map<String, String>> handlerBusinessException(BusinessCustomException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ex.getField(), ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorMap);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomServerException.class)
    public ResponseEntity<Map<String, String>> handlerBusinessException(CustomServerException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ex.getField(), ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorMap);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipleException.class)
    public ResponseEntity<Map<String, String>> handlerMultipleException(MultipleException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getFieldErrors());
    }


    @ExceptionHandler(CustomNotFoundException.class)
    public ProblemDetail handlerResourceNotFoundException(CustomNotFoundException ex) {
        CustomNotFoundProblemDetail problemDetail = new CustomNotFoundProblemDetail(HttpStatus.NOT_FOUND.value(), messageSource.get("common.error.404.title"));
        problemDetail.setProperty(ex.getField(), ex.getMessage());
        problemDetail.setProperty("timestamp", DatetimeFormatted.formatted_datetime());
        return problemDetail;
    }


    @ExceptionHandler({
            DataAccessException.class,
            CannotGetJdbcConnectionException.class,
            DataAccessResourceFailureException.class,
            CannotCreateTransactionException.class,
            JpaSystemException.class,
            TransactionSystemException.class,
            PersistenceException.class,
            SQLException.class,
            ConnectException.class,
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
        logger.error("Service not ready: {}", ex.getMessage());
        problemDetail.setTitle(messageSource.get("common.error.503.title"));
        return problemDetail;
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ProblemDetail handleNotFound(NoHandlerFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                messageSource.get("common.error.404.detail")
        );
        logger.error("Endpoint not found: {}", ex.getMessage());
        problemDetail.setTitle(messageSource.get("common.error.404.title"));
        return problemDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                messageSource.get("common.method.not-support.detail")
        );

        problemDetail.setTitle(messageSource.get("common.method.not-support.title"));
        return problemDetail;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleConnectFailed(HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.get("common.error.400.detail")
        );
        logger.error("Error missing body: {}", ex.getMessage());
        problemDetail.setTitle(messageSource.get("common.error.400.title"));
        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingRequestParams(MissingServletRequestParameterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.get("common.error.400.detail")
        );
        logger.error("Error missing params: {}", ex.getMessage());
        problemDetail.setTitle(messageSource.get("common.error.400.title"));
        return problemDetail;
    }


}
