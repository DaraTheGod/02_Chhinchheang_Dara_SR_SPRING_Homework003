package com.example.springexception.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Instant timestamp;
    private String message;
    private String status;
    private T payload;

//    for success
    public static <T> ApiResponse<T> success(String message, T payload) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .timestamp(Instant.now())
                .message(message)
                .status(HttpStatus.OK.name())
                .payload(payload)
                .build();
    }
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .message(message)
                .status(HttpStatus.OK.name())
                .payload(null)
                .build();
    }
    public static <T> ApiResponse<T> created(String message, T payload) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .message(message)
                .status(HttpStatus.CREATED.name())
                .payload(payload)
                .build();
    }

//    for error response
//    public static <T> ApiResponse<T> badRequest(String message) {
//        return ApiResponse.<T>builder()
//                .message(message)
//                .status(HttpStatus.BAD_REQUEST.name())
//                .payload(null)
//                .timestamp(Instant.now())
//                .build();
//    }
//    public static <T> ApiResponse<T> notFound(String message) {
//        return ApiResponse.<T>builder()
//                .message(message)
//                .status(HttpStatus.NOT_FOUND.name())
//                .payload(null)
//                .timestamp(Instant.now())
//                .build();
//    }
//    public static <T> ApiResponse<T> conflict(String message) {
//        return ApiResponse.<T>builder()
//                .message(message)
//                .status(HttpStatus.CONFLICT.name())
//                .payload(null)
//                .timestamp(Instant.now())
//                .build();
//    }

//    for custom error
//    public static <T> ApiResponse<T> error(String status, String message) {
//        return ApiResponse.<T>builder()
//                .message(message)
//                .status(status)
//                .payload(null)
//                .timestamp(Instant.now())
//                .build();
//    }
}
