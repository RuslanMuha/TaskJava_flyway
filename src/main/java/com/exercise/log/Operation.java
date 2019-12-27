package com.exercise.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum Operation {


    CREATE(1), UPDATE(2), DELETE(3);


    private Integer id;

    public static Operation getById(Integer id) {
        return Arrays.stream(values())
                .filter(operation -> operation.getId().equals(id))
                .findFirst()
                .get();
    }

    public static Operation getOperation(String httpMethod) {

        Operation operation = null;
        switch (httpMethod) {
            case "POST":
                operation = CREATE;
                break;
            case "DELETE":
                operation = DELETE;
                break;
            case "PUT":
                operation = UPDATE;
        }
        return operation;
    }
}
