package com.domain_expansion.integrity.company.application.client.decoder;

import com.domain_expansion.integrity.company.common.exception.ClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.domain_expansion.integrity.company.common.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);

            if (!errorResponse.success()) {

                return new ClientException(HttpStatus.resolve(response.status()),
                        errorResponse.message());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ErrorDecoder.Default().decode(methodKey, response);
    }
}
