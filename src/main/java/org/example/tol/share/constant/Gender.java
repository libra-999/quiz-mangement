package org.example.tol.share.constant;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(1), FEMALE(2), NON(3);

    private final int type;

    Gender(int type) {
        this.type = type;
    }
}
