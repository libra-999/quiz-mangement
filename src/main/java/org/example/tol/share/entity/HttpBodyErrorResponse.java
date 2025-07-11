package org.example.tol.share.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class HttpBodyErrorResponse {

    private String type;
    private String code;
    private String message;
    private String error;
    private Map<String, String> bodyRequestError;

}
