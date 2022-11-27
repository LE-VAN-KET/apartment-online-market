package com.cdcn.apartmentonlinemarket.exception.handler;

import com.cdcn.apartmentonlinemarket.common.util.MessageSourceUtil;
import com.cdcn.apartmentonlinemarket.common.util.WebUtil;
import com.cdcn.apartmentonlinemarket.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalHandleException {

    private final MessageSourceUtil messageSourceUtil;
    private final WebUtil webUtil;

    @Autowired
    public GlobalHandleException(MessageSourceUtil messageSourceUtil, WebUtil webUtil) {
        this.messageSourceUtil = messageSourceUtil;
        this.webUtil = webUtil;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x ->
                        String.format("'%s' %s", x.getField(), messageSourceUtil.getMessage(x.getDefaultMessage())
                        ))
                .collect(Collectors.toList());


        addErrorLog(HttpStatus.BAD_REQUEST, StringUtils.join(errors, ';'), "MethodArgumentNotValidException");
        ErrorResponse commonErrorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors);
        return new ResponseEntity<>(commonErrorResponse, HttpStatus.BAD_REQUEST);
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, String exceptionType) {
        addErrorLog(httpStatus.value(), errorMessage, exceptionType);
    }

    protected void addErrorLog(Integer errorCode, String errorMessage, String exceptionType) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}",
                errorCode, exceptionType, webUtil.getRequestUri(),
                 webUtil.getElapsedTime(), errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {

        List<String> violations = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + messageSourceUtil.getMessage(v.getMessage()))
                .collect(Collectors.toList());

        String errMsg = violations.isEmpty() ? ex.getMessage(): StringUtils.join(violations, ";");
        addErrorLog(HttpStatus.BAD_REQUEST, errMsg, "ConstraintViolationException");
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errMsg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAll(Exception ex) {
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "INTERNAL_SERVER_ERROR");
        String errMsg = "Unexpected internal server error occurs";
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                errMsg);
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleTransactionException(TransactionSystemException ex) throws Throwable {
        Throwable cause = ex.getCause();
        if (!(cause instanceof RollbackException))
            throw cause;
        if (!(cause.getCause() instanceof ConstraintViolationException))
            throw cause.getCause();
        ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();
        List<String> errors = validationException.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        String msgLog = errors.isEmpty() ? ex.getMessage(): StringUtils.join(errors, ";");
        addErrorLog(HttpStatus.BAD_REQUEST, msgLog, "ConstraintViolationException");
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors);
    }

}
