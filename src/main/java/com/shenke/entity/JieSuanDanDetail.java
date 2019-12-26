package com.shenke.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_jiesuandandetail")
public class JieSuanDanDetail {
    @Id
    @GeneratedValue
    private Integer Id;//id

    private String name;//产品名称

    private Integer count;//发货数

    private Double guige;//规格

    private String danwei;//单位

    private Double money;//单价

    private Double summoney;//总价格

    @ManyToOne
    @JoinColumn(name = "jiesuandan_id")
    private JieSuanDan jieSuanDan;//结算单

    @Override
    public String toString() {
        return "JieSuanDanDetail{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", guige=" + guige +
                ", danwei='" + danwei + '\'' +
                ", money=" + money +
                ", summoney=" + summoney +
                ", jieSuanDan=" + jieSuanDan +
                '}';
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getGuige() {
        return guige;
    }

    public void setGuige(Double guige) {
        this.guige = guige;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getSummoney() {
        return summoney;
    }

    public void setSummoney(Double summoney) {
        this.summoney = summoney;
    }

    public JieSuanDan getJieSuanDan() {
        return jieSuanDan;
    }

    public void setJieSuanDan(JieSuanDan jieSuanDan) {
        this.jieSuanDan = jieSuanDan;
    }
}
