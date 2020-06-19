package com.bbu.shenma.util;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

    private Integer rows;//获取总行数
    private List<T> dataList;//获取 集合

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
