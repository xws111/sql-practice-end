package com.xws111.sqlpractice.user.service.impl;

import com.xws111.sqlpractice.mapper.ContributorMapper;
import com.xws111.sqlpractice.model.entity.Contributor;
import com.xws111.sqlpractice.user.service.ContributorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class ContributorServiceImpl implements ContributorService {
    @Resource
    private ContributorMapper contributorMapper;

    @Override
    public List<Contributor> getContributors() {
        return contributorMapper.selectAll();
    }
}
