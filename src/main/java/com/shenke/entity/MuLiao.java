package com.shenke.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_muliao")
public class MuLiao {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String remark;

    @Override
    public String toString() {
        return "MuLiao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
