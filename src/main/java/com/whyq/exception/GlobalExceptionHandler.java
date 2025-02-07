package com.whyq.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle all uncaught exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, HttpServletRequest request, Model model) {
        log.error("Exception occurred at URL: " + request.getRequestURL(), ex);
        
        model.addAttribute("errorTitle", "Something went wrong!");
        model.addAttribute("errorMessage", ex.getMessage());
//        model.addAttribute("trace", ex.getStackTrace());
        model.addAttribute("errorURL", request.getRequestURL());
        
        return "errorPage"; // Redirect to Thymeleaf error page
    }

    // Handle specific RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException ex, HttpServletRequest request, Model model) {
        log.error("RuntimeException occurred at URL: " + request.getRequestURL(), ex);

        model.addAttribute("errorTitle", "An Error Occurred!");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorURL", request.getRequestURL());

        return "errorPage"; // Redirect to a generic error page
    }
}
