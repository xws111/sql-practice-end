package com.xws111.sqlpractice.user.service;

import com.xws111.sqlpractice.model.entity.Contributor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ContributorService {
    List<Contributor> getContributors();
}
