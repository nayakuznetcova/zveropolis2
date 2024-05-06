package ru.skypro.zveropolis.exception;

public class PetAlreadyExistsException extends RuntimeException{
    public PetAlreadyExistsException(String message) {
        super(message);
    }
}
