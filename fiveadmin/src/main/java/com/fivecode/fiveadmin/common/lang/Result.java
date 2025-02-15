package com.fivecode.fiveadmin.common.lang;

import java.io.Serializable;

import lombok.Data;

@Data
public class Result implements Serializable{


    private int code;
    private String msg;
    private Object data;


    public static Result succ(Object data){

            return succ(200, "操作成功", data);
        }
    
    public static Result succ(int code, String msg, Object data){
        Result R = new Result();
        R.setCode(code);
        R.setMsg(msg);
        R.setData(data);
        return R;
    }


    public static Result fail(String msg){

        return fail(400, msg, null);
    }

    public static Result fail(int code, String msg, Object data){
        Result R = new Result();
        R.setCode(code);
        R.setMsg(msg);
        R.setData(data);
        return R;
    }
}
