package com.tawasupermarket.customermicroservice.exception;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionMessage {
    private String error;
    private Date timestamp;
    private String path;
    private String status;
}

