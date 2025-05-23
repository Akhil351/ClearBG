package org.akhil.bg.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RemoveBgResponse {
    private boolean success;
    private HttpStatus statusCode;
    private Object data;

}
