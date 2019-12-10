package com.aarrd.room_designer.storage;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorController
{
     /**
     * .
     * @param request .
     * @param response .
     * @throws Exception .
     */
    @ExceptionHandler(Exception.class)
    public void handleConflict(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        response.setStatus(400);
        response.getWriter().println(e.getMessage());
    }
}
