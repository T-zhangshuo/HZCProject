package com.hzcharmander.controller;

import com.hzcharmander.BaseData;
import com.hzcharmander.Utils.TimeUtil;
import com.hzcharmander.actable.command.PageResultCommand;
import com.hzcharmander.actable.manager.common.BaseMysqlService;
import com.hzcharmander.model.Test;
import com.hzcharmander.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    //简单一点的可以直接用BaseMySqlService即可，复杂一点的可以用自定义的Dao层和Service 来实现。

    @Autowired
    private BaseMysqlService baseMysqlService;
    @Autowired
    private ITestService iTestService;

    @RequestMapping("/add")
    @ResponseBody
    public BaseData<String> addTest(){
        BaseData<String> baseData=new BaseData<>();
        //具体使用如下，如果为普通的增删改查，用BaseMySqlService即可。
        Test test=new Test();
        test.setName("测试:"+TimeUtil.DateformatTime(new Date()));

        //如果test.id存在值，则这个调用就为更新。如果id不存在值，这个调用就为新增
        Long saveId = baseMysqlService.save(test);
        baseData.setData("ID:"+saveId);
        return baseData;
    }

    @RequestMapping("/list1")
    @ResponseBody
    public BaseData<List<Test>> list1(){
        //这里展示自定义service+自定义Dao层 里面的效果
        List<Test> testList = iTestService.list1();
        BaseData<List<Test>> data=new BaseData<>();
        data.setData(testList);
        return data;
    }

    @RequestMapping("/list2")
    @ResponseBody
    public BaseData<List<Test>> list2(){
        //这里用自定义好的service 查询
        Test test=new Test();
        //test里面设置的字段值，均会变成查询条件
        PageResultCommand<Test> query = baseMysqlService.query(test);
        BaseData<List<Test>> data=new BaseData<>();
        data.setData(query.getData());
        return data;
    }
}
