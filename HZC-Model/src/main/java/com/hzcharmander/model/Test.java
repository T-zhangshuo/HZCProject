package com.hzcharmander.model;

import com.hzcharmander.actable.annotation.Column;
import com.hzcharmander.actable.annotation.Table;
import com.hzcharmander.actable.command.BaseModel;

/**
 * @author zhangjie
 * 数据库实体类，需要继承BaseModel。其中id，updateTime，createTime，isDel(1为删除，0为不删除），分页数据 已经在BaseModel中声明
 */
@Table
public class Test extends BaseModel {
    //  @Column                   默认字段名 （数据库值可为空
    //  @Column(isNull = false)   默认字段名 （数据库值不为空
    //  @Column(name = "nameStr") 字段名为nameStr
    //  其他的看 Column这个注解类
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
