package com.xws111.sqlpractice.user.controller;

import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.entity.Contributor;
import com.xws111.sqlpractice.user.service.ContributorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/contributor")
@Slf4j
public class ContributorController {
    @Resource
    private ContributorService contributorService;

    @GetMapping
    public BaseResponse<List<Contributor>> getContributors() {
        return ResultUtils.success(contributorService.getContributors());
    }
}
