package com.iqilu.message.transfer.controller.result;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 卢斌
 */
@Getter
@Setter
@NoArgsConstructor
public class Result<T> {

    /**
     * 接口请求结果；1成功，其他数字表示失败
     */
    Integer status;

    /**
     * 接口提示信息
     */
    String message;

    /**
     * 数据对象
     */
    T data;

    /**
     * 数据列表
     */
    List<T> list;

    /**
     * 页码号
     */
    Integer pageNum;

    /**
     * 页容量
     */
    Integer pageSize;

    /**
     * 总页数
     */
    Long totalPage;

    /**
     * 是否为第一页
     */
    Boolean firstPage;

    /**
     * 是否为最后一页
     */
    Boolean lastPage;


    public static Result<Object> ok() {
        Result<Object> result = new Result<>();
        result.setStatus(1);
        return result;
    }

    public static <T>Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setStatus(1);
        result.setData(data);
        return result;
    }

    public static <T>Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setStatus(0);
        result.setMessage(message);
        return result;
    }


    public static <T>Result<T> error(Exception e) {
        Result<T> result = new Result<>();
        result.setStatus(-1);
        result.setMessage(e.getMessage());
        return result;
    }

    public static <T>Result<T> error(String e) {
        Result<T> result = new Result<>();
        result.setStatus(-1);
        result.setMessage(e);
        return result;
    }

    public static <T>Result<T> ok(List<T> list) {
        PageInfo<T> storage = new PageInfo<>(list);
        Result<T> result = new Result<>();
        result.setStatus(1);
        result.setPageNum(storage.getPageNum());
        result.setPageSize(storage.getPageSize());
        result.setTotalPage(storage.getTotal());
        result.setList(storage.getList());
        result.setFirstPage(storage.isIsFirstPage());
        result.setLastPage(storage.isIsLastPage());
        return result;
    }

}
