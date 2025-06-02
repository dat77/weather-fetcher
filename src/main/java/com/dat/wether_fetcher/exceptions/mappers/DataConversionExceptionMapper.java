package com.dat.wether_fetcher.exceptions.mappers;

import com.dat.wether_fetcher.exceptions.DataConversionException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataConversionExceptionMapper implements ExceptionMapper<DataConversionException> {

  @Override
  public Response toResponse(DataConversionException dataConversionException) {
    return Response.status(Status.INTERNAL_SERVER_ERROR)
        .entity("Data conversion Error | " + dataConversionException.getMessage()).build();
  }
}
