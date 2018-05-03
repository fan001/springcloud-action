package com.base.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;
    public static final int TOKEN_ERROR_CODE = 40101;
    public static final int TOKEN_FORBIDDEN_CODE = 40301;

    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private long totalCount = 0;

    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }


    public ResultBean(T data, long totalCount) {
        super();
        this.data = data;
        this.totalCount = totalCount;
    }
}
