package com.prowidgetstudio.gitstatsmvp.utils;

/**
 * Created by Dzano on 1.12.2018.
 */

public enum Errors {

    ENTER_USERNAME(0),
    USERNAME_PASS_INVALID(1),
    ENTER_PASSWORD(2),
    ENTER_URL(3),
    WRONG_URL(4),
    LOGIN_FAILED(5);

    private final int id;

    Errors(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
