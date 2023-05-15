package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.Service.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private User_Service user_service;

}
