package org.example.tol.share.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tol.share.entity.HttpBodyPagingResponse;
import org.example.tol.share.entity.HttpBodyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> ResponseEntity<HttpBodyResponse<T>> responsePaging(
        T data, HttpBodyPagingResponse pagingResponse) {
        return responsePaging(HttpStatus.OK, HttpStatus.OK.name(), data, pagingResponse);
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> responsePaging(
        HttpStatus httpStatus, String message, T data, HttpBodyPagingResponse pagingResponse) {
        return response(httpStatus, message, data, pagingResponse);
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> responseSucceed(String message, T data) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                HttpBodyResponse.<T>builder()
                    .status(HttpStatus.OK.value())
                    .message(message)
                    .data(data)
                    .build());
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> responseSucceed(T data) {
        return responseSucceed(HttpStatus.OK.name(), data);
    }

    public static ResponseEntity<HttpBodyResponse<?>> responseSucceed() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                HttpBodyResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.name())
                    .build());
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> responseCreated(T data) {
        return responseCreated(HttpStatus.CREATED.name(), data);
    }

    public static ResponseEntity<HttpBodyResponse<Object>> responseCreated() {
        return responseCreated(HttpStatus.CREATED.name());
    }

    public static ResponseEntity<HttpBodyResponse<Object>> responseCreated(String message) {
        return response(HttpStatus.CREATED, message);
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> responseCreated(String message, T data) {
        return response(HttpStatus.CREATED, message, data);
    }

    public static ResponseEntity<Void> responseDeleted() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public static ResponseEntity<HttpBodyResponse<Object>> response(
        HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
            .body(HttpBodyResponse.builder().status(httpStatus.value()).message(message).build());
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> response(
        HttpStatus httpStatus, String message, T data) {
        return ResponseEntity.status(httpStatus)
            .body(
                HttpBodyResponse.<T>builder()
                    .status(httpStatus.value())
                    .message(message)
                    .data(data)
                    .build());
    }

    public static <T> ResponseEntity<HttpBodyResponse<T>> response(
        HttpStatus httpStatus, String message, T data, HttpBodyPagingResponse pagingResponse) {
        return ResponseEntity.status(httpStatus)
            .body(
                HttpBodyResponse.<T>builder()
                    .status(httpStatus.value())
                    .message(message)
                    .data(data)
                    .paging(pagingResponse)
                    .build());
    }

    public static <T> String toJsonString(T data) throws JsonProcessingException {
        return data instanceof String ? (String) data : objectMapper.writeValueAsString(data);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJsonString(String data, Class<T> classData) throws JsonProcessingException {
        return classData.equals(String.class) ? (T) data : objectMapper.readValue(data, classData);
    }

}
