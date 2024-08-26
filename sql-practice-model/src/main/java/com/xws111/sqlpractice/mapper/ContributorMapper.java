package com.xws111.sqlpractice.mapper;

import com.xws111.sqlpractice.model.entity.Contributor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContributorMapper {
    List<Contributor> selectAll();
}
