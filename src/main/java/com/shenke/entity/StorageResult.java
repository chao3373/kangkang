package com.shenke.entity;

import javax.persistence.Entity;

/***
 * 当前库存查询自定义查询实体类
 */
public class StorageResult {
    private String clientname;// 客户名称

    private String peasant;// 农户名称

    private String saleNumber;// 订单号

    private String name;// 商品名称

    private Double model;

    private Double length;

    private String price;

    private Double weight;

    private String color;

    private Integer sum;

    private Double sumWeight;

    private String dao;

    private Double unitPrice;

    private Double totalPrice;

    private String demand;

    public StorageResult(String clientname, String peasant, String saleNumber, String name, Double model, Double length, String price, Double weight, String color, Integer sum, Double sumWeight, String dao, Double unitPrice, Double totalPrice, String demand) {
        this.clientname = clientname;
        this.peasant = peasant;
        this.saleNumber = saleNumber;
        this.name = name;
        this.model = model;
        this.length = length;
        this.price = price;
        this.weight = weight;
        this.color = color;
        this.sum = sum;
        this.sumWeight = sumWeight;
        this.dao = dao;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "StorageResult{" +
                "clientname='" + clientname + '\'' +
                ", peasant='" + peasant + '\'' +
                ", saleNumber='" + saleNumber + '\'' +
                ", name='" + name + '\'' +
                ", model=" + model +
                ", length=" + length +
                ", price='" + price + '\'' +
                ", weight=" + weight +
                ", color='" + color + '\'' +
                ", sum=" + sum +
                ", sumWeight=" + sumWeight +
                ", dao='" + dao + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", demand='" + demand + '\'' +
                '}';
    }

    public StorageResult() {

    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getPeasant() {
        return peasant;
    }

    public void setPeasant(String peasant) {
        this.peasant = peasant;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getModel() {
        return model;
    }

    public void setModel(Double model) {
        this.model = model;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Double getSumWeight() {
        return sumWeight;
    }

    public void setSumWeight(Double sumWeight) {
        this.sumWeight = sumWeight;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }
}
