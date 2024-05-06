package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xws111.sqlpractice.mapper.TagMapper;
import com.xws111.sqlpractice.model.entity.Tag;
import com.xws111.sqlpractice.question.service.TagService;
import org.springframework.stereotype.Service;

/**
* @author xg
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2024-05-03 22:04:17
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

}




