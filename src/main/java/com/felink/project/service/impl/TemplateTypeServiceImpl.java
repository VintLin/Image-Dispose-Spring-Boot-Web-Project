package com.felink.project.service.impl;

import com.felink.project.dao.TemplateTypeMapper;
import com.felink.project.model.TemplateType;
import com.felink.project.service.TemplateTypeService;
import com.felink.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/03.
 */
@Service
@Transactional
public class TemplateTypeServiceImpl extends AbstractService<TemplateType> implements TemplateTypeService {
    @Resource
    private TemplateTypeMapper templateTypeMapper;

}
