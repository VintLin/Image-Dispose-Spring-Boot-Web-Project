package com.felink.project.service.impl;

import com.felink.project.dao.VideoTemplateMapper;
import com.felink.project.model.VideoTemplate;
import com.felink.project.service.VideoTemplateService;
import com.felink.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/03.
 */
@Service
@Transactional
public class VideoTemplateServiceImpl extends AbstractService<VideoTemplate> implements VideoTemplateService {
    @Resource
    private VideoTemplateMapper videoTemplateMapper;

}
