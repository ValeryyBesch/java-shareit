package ru.practicum.shareit.booking;

import ru.practicum.shareit.exception.StatusException;

public enum State {

    ALL,

    CURRENT,

    PAST,

    FUTURE,

    WAITING,

    REJECTED;

    public static State getEnumValue(String state) {
    try {
        return State.valueOf(state);
    } catch (Exception e) {
        throw new StatusException("Unknown state: " + state);
    }

    }
}
