package com.felink.project.service.impl;

import com.felink.project.dao.ImageMapper;
import com.felink.project.model.Image;
import com.felink.project.service.ImageService;
import com.felink.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/11/22.
 */
@Service
@Transactional
public class ImageServiceImpl extends AbstractService<Image> implements ImageService {
    @Resource
    private ImageMapper imageMapper;

}
