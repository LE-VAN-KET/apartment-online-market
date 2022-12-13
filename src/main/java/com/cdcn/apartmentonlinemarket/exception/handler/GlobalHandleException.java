package com.cdcn.apartmentonlinemarket.exception.handler;

import com.cdcn.apartmentonlinemarket.common.util.MessageSourceUtil;
import com.cdcn.apartmentonlinemarket.common.util.WebUtil;
import com.cdcn.apartmentonlinemarket.exception.*;
import com.cdcn.apartmentonlinemarket.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
                .getAllErrors()
                .stream()
                .map(x ->
                        String.format("'%s' %s", x instanceof FieldError ? ((FieldError) x).getField():
                                        x.getObjectName(), messageSourceUtil.getMessage(x.getDefaultMessage())
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleBadRequestException(Exception ex, String exceptionType) {
        this.addErrorLog(HttpStatus.BAD_REQUEST, ex.getMessage(), exceptionType);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNotFoundException(Exception ex, String exceptionType) {
        this.addErrorLog(HttpStatus.NOT_FOUND, ex.getMessage(), exceptionType);
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    protected ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return handleNotFoundException(ex, "UserNotFoundException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserBadCredentialsException.class)
    protected ErrorResponse handleUserBadCredentialsException(UserBadCredentialsException ex) {
        return handleBadRequestException(ex, "UserBadCredentialsException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UsernameAlreadyExist.class, EmailAlreadyExist.class})
    protected ErrorResponse handleUsernameAlreadyExistException(Exception ex) {
        return handleBadRequestException(ex, "DataAlreadyExist");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    protected ErrorResponse handleRoleNotFoundException(RoleNotFoundException ex) {
        return handleNotFoundException(ex, "RoleNotFoundException");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ProductNotFoundException.class, InventoryNotFoundException.class,
            CartItemNotFoundException.class, CartNotFoundException.class, CategoryNotFoundException.class})
    protected ErrorResponse handleProductNotFoundException(Exception ex) {
        return handleNotFoundException(ex, "NotFoundException");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        this.addErrorLog(HttpStatus.FORBIDDEN, ex.getMessage(), "AccessDeniedException");
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ProductNotEnoughException.class})
    protected ErrorResponse handleProductNotEnoughException(ProductNotEnoughException ex) {
        return handleBadRequestException(ex, "ProductNotEnoughException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRefreshTokenException.class})
    protected ErrorResponse handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        return handleBadRequestException(ex, "InvalidRefreshTokenException");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorResponse(417, "One or more files are too large!"));
    }

}
