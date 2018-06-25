package com.example.dell.wx;

public class MyEvent {
    public  String Msg;

    public MyEvent(String msg) {
        Msg = msg;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
