package com.vehicleTracker.vehicleTracker.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception){
        ProblemDetail errorDetail = null;
        exception.printStackTrace();

        if(exception instanceof VehicleNotFoundException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),exception.getMessage());
errorDetail.setProperty("description","Vehicle Not found");
return errorDetail;
        }

        if(exception instanceof PolygonNotFoundException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),exception.getMessage());
            errorDetail.setProperty("description","Polygon Not found");
            return errorDetail;
        }




        return errorDetail;

    }


}
