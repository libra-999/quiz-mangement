package org.example.tol.util.constant;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(1), FEMALE(2);

    private final int type;

    Gender(int type) {
        this.type = type;
    }
}
