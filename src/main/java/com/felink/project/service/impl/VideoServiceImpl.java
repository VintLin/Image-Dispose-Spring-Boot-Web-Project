package com.felink.project.service.impl;

import com.felink.project.dao.VideoMapper;
import com.felink.project.model.Video;
import com.felink.project.service.VideoService;
import com.felink.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/12/27.
 */
@Service
@Transactional
public class VideoServiceImpl extends AbstractService<Video> implements VideoService {
    @Resource
    private VideoMapper videoMapper;

}
