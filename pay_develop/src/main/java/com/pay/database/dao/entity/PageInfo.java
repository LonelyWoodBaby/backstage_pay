package com.pay.database.dao.entity;

import java.util.List;

public class PageInfo<T> {
    private int count;
    private List<T> resultList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
