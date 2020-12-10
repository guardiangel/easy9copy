package org.felix.utils;

import com.github.pagehelper.Page;
import org.felix.model.vo.resp.PageVO;

import java.util.List;

/**
 * @author Felix
 */
public final class PageUtils {
    private PageUtils() {

    }

    public static <T> PageVO<T> getPageVO(List<T> list) {
        PageVO<T> result = new PageVO<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setTotalPages(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setCurPageSize(page.size());
            result.setList(page.getResult());
        }
        return result;
    }
}
