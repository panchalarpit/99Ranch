package com.tawasupermarket.customermicroservice.exception;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String message){super(message);}
}
