package com.shenke.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_jiesuandan")
public class JieSuanDan {

    @Id
    @GeneratedValue
    private Integer id;//id

    private String closeOnAccountNumber;//结算单号

    private Date data;//结算单日期

    private String clientName;//购货单位名称

    private String chehao;//车牌号

    private String songhuoren;//送货人

    private String shouhuoren;//送货人

    private String lankman;//联系方式

    private String fuzeren;//负责人

    private String tianzhiren;//填写人

    private String baoguanyuan;//保管

    private String xiaoshouyuan;//销售员

    private Double yukuan;//前项余款

    private Double qiankuan;//前项欠款

    private Double xianjin;//本次现金

    private Double piaoju;//本次票据

    private Double jyukuan;//本次结算后余款

    private Double jqiankuan;//本次结算后欠款


    public JieSuanDan(Integer id) {
        this.id = id;
    }

    public JieSuanDan() {

    }

    @Override
    public String toString() {
        return "JieSuanDan{" +
                "id=" + id +
                ", closeOnAccountNumber='" + closeOnAccountNumber + '\'' +
                ", data=" + data +
                ", clientName='" + clientName + '\'' +
                ", chehao='" + chehao + '\'' +
                ", songhuoren='" + songhuoren + '\'' +
                ", shouhuoren='" + shouhuoren + '\'' +
                ", lankman='" + lankman + '\'' +
                ", fuzeren='" + fuzeren + '\'' +
                ", tianzhiren='" + tianzhiren + '\'' +
                ", baoguanyuan='" + baoguanyuan + '\'' +
                ", xiaoshouyuan='" + xiaoshouyuan + '\'' +
                ", yukuan=" + yukuan +
                ", qiankuan=" + qiankuan +
                ", xianjin=" + xianjin +
                ", piaoju=" + piaoju +
                ", jyukuan=" + jyukuan +
                ", jqiankuan=" + jqiankuan +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCloseOnAccountNumber() {
        return closeOnAccountNumber;
    }

    public void setCloseOnAccountNumber(String closeOnAccountNumber) {
        this.closeOnAccountNumber = closeOnAccountNumber;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getChehao() {
        return chehao;
    }

    public void setChehao(String chehao) {
        this.chehao = chehao;
    }

    public String getSonghuoren() {
        return songhuoren;
    }

    public void setSonghuoren(String songhuoren) {
        this.songhuoren = songhuoren;
    }

    public String getShouhuoren() {
        return shouhuoren;
    }

    public void setShouhuoren(String shouhuoren) {
        this.shouhuoren = shouhuoren;
    }

    public String getLankman() {
        return lankman;
    }

    public void setLankman(String lankman) {
        this.lankman = lankman;
    }

    public String getFuzeren() {
        return fuzeren;
    }

    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    public String getTianzhiren() {
        return tianzhiren;
    }

    public void setTianzhiren(String tianzhiren) {
        this.tianzhiren = tianzhiren;
    }

    public String getBaoguanyuan() {
        return baoguanyuan;
    }

    public void setBaoguanyuan(String baoguanyuan) {
        this.baoguanyuan = baoguanyuan;
    }

    public String getXiaoshouyuan() {
        return xiaoshouyuan;
    }

    public void setXiaoshouyuan(String xiaoshouyuan) {
        this.xiaoshouyuan = xiaoshouyuan;
    }

    public Double getYukuan() {
        return yukuan;
    }

    public void setYukuan(Double yukuan) {
        this.yukuan = yukuan;
    }

    public Double getQiankuan() {
        return qiankuan;
    }

    public void setQiankuan(Double qiankuan) {
        this.qiankuan = qiankuan;
    }

    public Double getXianjin() {
        return xianjin;
    }

    public void setXianjin(Double xianjin) {
        this.xianjin = xianjin;
    }

    public Double getPiaoju() {
        return piaoju;
    }

    public void setPiaoju(Double piaoju) {
        this.piaoju = piaoju;
    }

    public Double getJyukuan() {
        return jyukuan;
    }

    public void setJyukuan(Double jyukuan) {
        this.jyukuan = jyukuan;
    }

    public Double getJqiankuan() {
        return jqiankuan;
    }

    public void setJqiankuan(Double jqiankuan) {
        this.jqiankuan = jqiankuan;
    }
}
