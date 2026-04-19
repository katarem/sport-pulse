package com.bytecodes.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("No existe un equipo con el ID proporcionado");
    }
}
