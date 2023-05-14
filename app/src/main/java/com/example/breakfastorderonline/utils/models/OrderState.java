package com.example.breakfastorderonline.utils.models;

import java.io.Serializable;

public enum OrderState implements Serializable {
    MAKING("MAKING"),
    WAITING_TAKING("WAITING_TAKING"),
    COMPLETE("COMPLETE");

    private String stateStr;

    OrderState(String stateStr) {
        this.stateStr = stateStr;
    }

    String getStateStr() {
        return stateStr;
    }
}
