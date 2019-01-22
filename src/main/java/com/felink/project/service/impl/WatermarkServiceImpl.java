package com.felink.project.service.impl;

import com.felink.project.dao.WatermarkMapper;
import com.felink.project.model.Watermark;
import com.felink.project.service.WatermarkService;
import com.felink.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/12/27.
 */
@Service
@Transactional
public class WatermarkServiceImpl extends AbstractService<Watermark> implements WatermarkService {
    @Resource
    private WatermarkMapper watermarkMapper;

}
