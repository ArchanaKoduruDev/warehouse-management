package com.fulfilment.application.monolith.warehouses.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseException;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Handling exception globally", exception);

        int status;
        String message;

        if (exception instanceof WarehouseException we) {
            status = 400; // Bad Request for domain validation errors
            message = we.getMessage();
        } else if (exception instanceof WebApplicationException webEx) {
            status = webEx.getResponse().getStatus();
            message = webEx.getMessage();
        } else {
            status = 500;
            message = "Internal Server Error";
        }

        ObjectNode response = objectMapper.createObjectNode();
        response.put("error", message);
        response.put("exceptionType", exception.getClass().getSimpleName());
        response.put("code", status);

        return Response.status(status).entity(response).build();
    }
}
