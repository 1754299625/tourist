package com.ssm.maven.core.entity;

public class HLCTable {

    private int mq_h;// 景区最大承载量

    private int hf;// 当前客流量/车流量

    private float hlc;// 客流承载量/车流承载量   当前客流量/总客流量

    private int time_hour;// 游客时间段 无用

    private String day;

    public HLCTable() {
    }

    public HLCTable(int hf, float hlc, int time_hour) {

        this.hf = hf;
        this.hlc = hlc;
        this.time_hour = time_hour;
    }

    @Override
    public String toString() {
        return "HLCTable{" +
                "mq_h=" + mq_h +
                ", hf=" + hf +
                ", hlc=" + hlc +
                ", time_hour=" + time_hour +
                '}';
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getMq_h() {
        return mq_h;
    }

    public void setMq_h(int mq_h) {
        this.mq_h = mq_h;
    }

    public int getHf() {
        return hf;
    }

    public void setHf(int hf) {
        this.hf = hf;
    }

    public float getHlc() {
        return hlc;
    }

    public void setHlc(float hlc) {
        this.hlc = hlc;
    }

    public int getTime_hour() {
        return time_hour;
    }

    public void setTime_hour(int time_hour) {
        this.time_hour = time_hour;
    }
}
