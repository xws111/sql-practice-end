package com.xws111.sqlpractice.common;  
  
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;  
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;  
import lombok.Data;
  
import java.util.ArrayList;
import java.util.List;  

  
/**  
 * @author Fancier  
 * @version 1.0  
 * @description: 分页相应类  
 * @date 2024/4/29 22:13  
 */  
@Data  
public class PageResponse<T> extends BaseResponse<List<T>>{  
    /**  
     * 总记录数  
     */  
    private Long total = 0L;  
  
    /**  
     * 每页显示得记录数, 默认每页显示10条  
     */  
    private Long size = 10L;  
  
    /**  
     * 当前页码  
     */  
    private Long current = 1L;  
  
    /**  
     * 总页数  
     */  
    private Long pages = 0L;  
  
    /**  
     * 查询成功返回  
     * @param page  
     * @param voClass  
     * @return  
     * @param <T>  
     * @param <P>  
     */  
    public static <T, P> PageResponse<T> success(Page<P> page, Class<T> voClass) {
        List<P> records = page.getRecords();  
  
        PageResponse<T> pageResponse = new PageResponse<>();  
  
        // do 转 vo
        List<T> vos = null;
        if (CollectionUtils.isNotEmpty(records)) {
             vos = BeanUtil.copyToList(records, voClass);
        }  
  
        // 为返回结果赋值  
        pageResponse.setPages(page.getPages());  
        pageResponse.setCurrent(page.getCurrent());  
        pageResponse.setTotal(page.getTotal());  
        pageResponse.setSize(page.getSize());  
        pageResponse.setData(vos);  
        return pageResponse;  
    }  
  
}