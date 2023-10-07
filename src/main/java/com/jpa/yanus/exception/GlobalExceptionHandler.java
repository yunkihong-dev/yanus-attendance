package com.jpa.yanus.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoProductException.class)
    protected RedirectView handleViewProductException(NoProductException noProductException){
        return new RedirectView("/error/500");
    }
}
