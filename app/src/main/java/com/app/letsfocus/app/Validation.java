package com.app.letsfocus.app;

public class Validation {
    public static boolean require(String ...params) {
        for(String param: params)
        {
            if(param == null) return false;
            if(param.isEmpty()) return false;
        }
        return true;
    }
}
