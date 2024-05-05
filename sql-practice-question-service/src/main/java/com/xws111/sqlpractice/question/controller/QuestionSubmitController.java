package com.xws111.sqlpractice.question.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date 2024/4/29 0:44
 * @Version 1.0
 * @Author xg
 */
@RestController
@Slf4j
@RequestMapping("/submit")
@CrossOrigin(origins="http://localhost:8000", allowCredentials = "true")
public class QuestionSubmitController {

}