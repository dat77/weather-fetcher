package com.dat.wether_fetcher.exceptions.mappers;

import com.dat.wether_fetcher.exceptions.UnableRetrieveDataException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnableRetrieveDataExceptionMapper implements ExceptionMapper<UnableRetrieveDataException> {

  @Override
  public Response toResponse(UnableRetrieveDataException exception) {
    return Response.status(Status.BAD_REQUEST)
        .entity("Unable retrieve data | " + exception.getMessage()).build();
  }
}
