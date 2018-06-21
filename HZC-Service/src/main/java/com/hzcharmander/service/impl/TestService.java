package com.hzcharmander.service.impl;

import com.hzcharmander.actable.command.PageResultCommand;
import com.hzcharmander.actable.dao.common.BaseMysqlCRUDMapper;
import com.hzcharmander.actable.manager.common.BaseMysqlService;
import com.hzcharmander.dao.ITestMapper;
import com.hzcharmander.model.Test;
import com.hzcharmander.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService implements ITestService {
    @Autowired
    private ITestMapper iTestMapper;

    @Override
    public List<Test> list1() {
        //自定义的Dao层的使用
        return iTestMapper.list();
    }

}
