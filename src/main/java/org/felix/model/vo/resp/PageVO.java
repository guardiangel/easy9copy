package org.felix.model.vo.resp;

import java.util.List;

/**
 * 分页
 * @author Felix
 * @param <T>
 */
public class PageVO<T> {

    /**
     *总记录数
     */
    private Long totalRows;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     *当前第几页
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 当前页记录数
     */
    private Integer curPageSize;

    /**
     *数据列表
     */
    private List<T> list;

    @Override
    public String toString() {
        return "PageVo{" +
                "totalRows=" + totalRows +
                ", totalPages=" + totalPages +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", curPageSize=" + curPageSize +
                ", list=" + list +
                '}';
    }

    public Long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Long totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurPageSize() {
        return curPageSize;
    }

    public void setCurPageSize(Integer curPageSize) {
        this.curPageSize = curPageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
