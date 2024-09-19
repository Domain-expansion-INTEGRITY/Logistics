package com.domain_expansion.integrity.hub.application.client.decoder;

import com.domain_expansion.integrity.hub.common.exception.ClientException;
import com.domain_expansion.integrity.hub.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.body() == null) {
            return new ClientException(HttpStatus.resolve(response.status()), "인증권한을 다시 한번 확인해주세요.");
        }

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);

            if (!errorResponse.success()) {

                return new ClientException(HttpStatus.resolve(response.status()),
                        errorResponse.message());
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ErrorDecoder.Default().decode(methodKey, response);
    }
}
