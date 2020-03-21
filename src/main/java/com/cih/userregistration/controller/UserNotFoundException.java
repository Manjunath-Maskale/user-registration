package com.cih.userregistration.controller;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Object obj){
        super("Could not find user " + obj);
    }
}
