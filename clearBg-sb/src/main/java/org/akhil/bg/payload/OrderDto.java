package org.akhil.bg.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private String id;
    private String entity;
    private BigDecimal amount;
    private String status;
    private Date created_at;
    private String currency;
    private String receipt;

}
