package com.vgg.controller;

import com.vgg.exceptions.impl.NotFoundException;
import com.vgg.model.*;
import com.vgg.service.ActionsService;
import com.vgg.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/actions")
public class ActionsController {


    @Autowired
    private ActionsService actionsService;


    @GetMapping
    public ApiResponse<List<Actions>> listActions(){
        return new ApiResponse<>(HttpStatus.OK.value(), "Actions list fetched successfully.",actionsService.findAll());
    }

    @GetMapping("/{actionId}")
    public ApiResponse<Actions> getOne(@PathVariable int actionId){
        return new ApiResponse<>(HttpStatus.OK.value(), "Action fetched successfully.",actionsService.findById(actionId));
    }


}
