package org.dengyu.daydayshopping.util;

public class AjaxResult {
    private boolean success=true;
    private String msg="登录成功";
    private Object resultObject;

    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public AjaxResult setResultObject(Object resultObject) {
        this.resultObject = resultObject;
        return this;
    }

    public static AjaxResult getAjaxResult(){
        return new AjaxResult();
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", resultObject=" + resultObject +
                '}';
    }
}
