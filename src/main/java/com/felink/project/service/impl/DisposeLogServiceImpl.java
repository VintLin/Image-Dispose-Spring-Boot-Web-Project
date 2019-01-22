package com.felink.project.service.impl;

import com.felink.project.core.AbstractService;
import com.felink.project.dao.DisposeLogMapper;
import com.felink.project.model.DisposeLog;
import com.felink.project.service.DisposeLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/11/22.
 */
@Service
@Transactional
public class DisposeLogServiceImpl extends AbstractService<DisposeLog> implements DisposeLogService {
    @Resource
    private DisposeLogMapper disposeLogMapper;
}
