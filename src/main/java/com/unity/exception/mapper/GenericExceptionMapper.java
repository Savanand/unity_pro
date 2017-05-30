package com.unity.exception.mapper;

import com.unity.exception.Error;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger log = LoggerFactory.getLogger(GenericExceptionMapper.class);
    @Override
    public Response toResponse(Throwable ex) {
        Response.StatusType type = getStatusType(ex);
        log.error("Exception occurred in application : {}", ex.getMessage());
       ;
        Error error = new Error(
                type.getStatusCode(),
                type.getReasonPhrase(),
                ex.getLocalizedMessage());
        log.error("Details : {}", ex.getLocalizedMessage()+
        		"StatusCode :" + error.getStatusCode()
        		+ "Error Entity :"+ error
        		+ "");
        return Response.status(error.getStatusCode())
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Response.StatusType getStatusType(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return((WebApplicationException)ex).getResponse().getStatusInfo();
        } else {
            return Response.Status.BAD_REQUEST;
        }
    }
}
