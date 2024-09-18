package com.domain_expansion.integrity.order.application.client.decoder;

import com.domain_expansion.integrity.order.common.exception.ClientException;
import com.domain_expansion.integrity.order.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);

            if (!errorResponse.success()) {

                return new ClientException(HttpStatus.resolve(response.status()),
                        errorResponse.message());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Default().decode(methodKey, response);
    }


}
