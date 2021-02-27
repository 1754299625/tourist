package com.ssm.maven.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Scenicspot {
    private Integer id;

    private String scenictype;// 景区类型

    private String scenicname;// 景区名称

    private String address;// 景区地址

    private String telephone;// 联系方式

    private Integer max_people;// 客流最大承载量

    private Integer max_car;// 停车场最大承载量

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;// 创建时间

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;// 更新时间

    private Integer creator;

    private String status;// 景区状态

    private Integer updator;

    private Integer del_flag;// 删除标志

    private String code;// 景区编号

    private double max_di;// 舒适度阈值

    @Override
    public String toString() {
        return "Scenicspot{" +
                "id=" + id +
                ", scenictype=" + scenictype +
                ", scenicname='" + scenicname + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", max_people=" + max_people +
                ", max_car=" + max_car +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", creator=" + creator +
                ", status=" + status +
                ", updator=" + updator +
                ", del_flag=" + del_flag +
                ", code='" + code + '\'' +
                ", max_di=" + max_di +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScenictype() {
        return scenictype;
    }

    public void setScenictype(String scenictype) {
        this.scenictype = scenictype;
    }

    public String getScenicname() {
        return scenicname;
    }

    public void setScenicname(String scenicname) {
        this.scenicname = scenicname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getMax_people() {
        return max_people;
    }

    public void setMax_people(Integer max_people) {
        this.max_people = max_people;
    }

    public Integer getMax_car() {
        return max_car;
    }

    public void setMax_car(Integer max_car) {
        this.max_car = max_car;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMax_di() {
        return max_di;
    }

    public void setMax_di(double max_di) {
        this.max_di = max_di;
    }
}