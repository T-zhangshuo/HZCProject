package com.hzcharmander.actable.command;


import com.hzcharmander.actable.annotation.Column;

import java.io.Serializable;


/**
 * 分页的基本属性
 *
 * @author zhangjie
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -2467322075253424352L;

    //基本的ID
    //  TODO 当声明为 isKey=true，isAutoIncrement为false时，数据存的为不重复的增长的16位的Long值。
    //	TODO 当声明为 isKey=true,isAutoIncrement为true时，数据存的值为自增
    @Column(isKey = true)
    public Long id;

    @Column(isNull = false,isUpdateTime = true)
    public String updateDate;

    @Column(isNull = false,isNowTime = true)
    public String createDate;

    @Column(name = "isDel", isNull = false, defaultValue = "0")
    public Integer isDel = 0;

    /**
     * 当前页码,请先设置pageSize,否则使用默认的10
     */
    public int currentPage;

    /**
     * 每页显示多少条，默认10条
     */
    public int pageSize = 10;

    public int start;// (currentPage-1)*pageSize

    public String orderField;

    public String sortStr = DESC;

    public static String DESC = "desc";

    public static String ASC = "asc";

    public int getCurrentPage() {
        return currentPage;
    }


    public void setCurrentPage(int currentPage) {

    }

    public void setPage(int currentPage,int pageSize){
        this.currentPage = currentPage;
        this.pageSize=pageSize;
        this.setStart((currentPage - 1) * getPageSize());
    }

    public void setOrder(String orderField,String sortStr){
        setOrderField(orderField);
        setSortStr(sortStr);
    }


    public int getPageSize() {
        return pageSize;
    }



    public int getStart() {
        this.setStart((currentPage - 1) * getPageSize());
        return start;
    }


    public void setStart(int start) {
        this.start = start;
    }


    public String getOrderField() {
        return orderField;
    }


    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }


    public String getSortStr() {
        return sortStr;
    }


    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
