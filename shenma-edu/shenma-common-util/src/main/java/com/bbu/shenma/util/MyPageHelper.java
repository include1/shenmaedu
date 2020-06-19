package com.bbu.shenma.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyPageHelper<T>{

    private List<T> list;//要分页的数组
    private Integer total; //总个数
    private Integer currentPage; //当前页
    private Integer totalPages; //总页数
    private Integer pageSize; // 每页个数大小
    private boolean hasPrevious; //前一页
    private boolean hasNext;//后一页

    public void setTotal(Integer total) {

    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasPrevious() {
        return this.currentPage <= 1 ? false : true;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return this.currentPage >= this.totalPages? false : true;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = list.size();
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages() {
        this.totalPages = (int) Math.ceil(1.0 * total / pageSize);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @param list
     * @param currentPage
     * @param pageSize
     */
    public MyPageHelper(List<T> list, Integer currentPage, Integer pageSize) {
        this.list = list;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        setTotal();//计算数组的长度
        setTotalPages();//通过数组长度/每页显示多少条数据
        isHasNext();//判断是否有下一页
        isHasPrevious();//判断是否有上一页
        getResult();
    }

    /**
     * 获取结果
     */
    public void getResult() {
        if (pageSize < total && pageSize > 0) {// 每页小于总数目
            int start = 0, end = 0;
            //计算取出元素在数组中的位置
            if (currentPage <= totalPages - 1 && currentPage >= 0) {
                start = (currentPage-1)* pageSize;
                end = currentPage* pageSize-1;
                setData(start, end);
            }else if(currentPage ==totalPages){
                start = (currentPage-1)* pageSize;
                end = total - 1;
                setData(start, end);
            }
        }
    }

    /**
     * 获取数组指定位置的元素
     * @param start 起始位置
     * @param end   截止位置
     */
    void setData(int start, int end) {
        if (start >= 0 && end < total) {
            List<T> list1 = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                list1.add(list.get(i));
            }
            this.list.clear();
            setList(list1);
        }
    }
}
