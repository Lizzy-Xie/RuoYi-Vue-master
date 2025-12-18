package com.ruoyi.library.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 读者信息对象 tb_reader
 * 
 * @author xlz
 * @date 2025-12-15
 */
public class TbReader extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 读者编号/借书证号【主键】 */
    private Long rdID;

    /** 读者姓名 */
    @Excel(name = "读者姓名")
    private String rdName;

    /** 性别(男/女) */
    @Excel(name = "性别(男/女)")
    private String rdSex;

    /** 读者类别【外键关联TB_ReaderType】 */
    @Excel(name = "读者类别【外键关联TB_ReaderType】")
    private Long rdType;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String rdDept;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String rdPhone;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String rdEmail;

    /** 办证日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "办证日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rdDateReg;

    /** 读者照片 */
    @Excel(name = "读者照片")
    private String rdPhoto;

    /** 证件状态(有效/挂失/注销) */
    @Excel(name = "证件状态(有效/挂失/注销)")
    private String rdStatus;

    /** 已借书数量 */
    @Excel(name = "已借书数量")
    private Long rdBorrowQty;

    /** 读者密码(初始123) */
    @Excel(name = "读者密码(初始123)")
    private String rdPwd;

    /** 管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理) */
    @Excel(name = "管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理)")
    private Long rdAdminRoles;

    public void setRdID(Long rdID) 
    {
        this.rdID = rdID;
    }

    public Long getRdID() 
    {
        return rdID;
    }

    public void setRdName(String rdName) 
    {
        this.rdName = rdName;
    }

    public String getRdName() 
    {
        return rdName;
    }

    public void setRdSex(String rdSex) 
    {
        this.rdSex = rdSex;
    }

    public String getRdSex() 
    {
        return rdSex;
    }

    public void setRdType(Long rdType) 
    {
        this.rdType = rdType;
    }

    public Long getRdType() 
    {
        return rdType;
    }

    public void setRdDept(String rdDept) 
    {
        this.rdDept = rdDept;
    }

    public String getRdDept() 
    {
        return rdDept;
    }

    public void setRdPhone(String rdPhone) 
    {
        this.rdPhone = rdPhone;
    }

    public String getRdPhone() 
    {
        return rdPhone;
    }

    public void setRdEmail(String rdEmail) 
    {
        this.rdEmail = rdEmail;
    }

    public String getRdEmail() 
    {
        return rdEmail;
    }

    public void setRdDateReg(Date rdDateReg) 
    {
        this.rdDateReg = rdDateReg;
    }

    public Date getRdDateReg() 
    {
        return rdDateReg;
    }

    public void setRdPhoto(String rdPhoto) 
    {
        this.rdPhoto = rdPhoto;
    }

    public String getRdPhoto() 
    {
        return rdPhoto;
    }

    public void setRdStatus(String rdStatus) 
    {
        this.rdStatus = rdStatus;
    }

    public String getRdStatus() 
    {
        return rdStatus;
    }

    public void setRdBorrowQty(Long rdBorrowQty) 
    {
        this.rdBorrowQty = rdBorrowQty;
    }

    public Long getRdBorrowQty() 
    {
        return rdBorrowQty;
    }

    public void setRdPwd(String rdPwd) 
    {
        this.rdPwd = rdPwd;
    }

    public String getRdPwd() 
    {
        return rdPwd;
    }

    public void setRdAdminRoles(Long rdAdminRoles) 
    {
        this.rdAdminRoles = rdAdminRoles;
    }

    public Long getRdAdminRoles() 
    {
        return rdAdminRoles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("rdID", getRdID())
            .append("rdName", getRdName())
            .append("rdSex", getRdSex())
            .append("rdType", getRdType())
            .append("rdDept", getRdDept())
            .append("rdPhone", getRdPhone())
            .append("rdEmail", getRdEmail())
            .append("rdDateReg", getRdDateReg())
            .append("rdPhoto", getRdPhoto())
            .append("rdStatus", getRdStatus())
            .append("rdBorrowQty", getRdBorrowQty())
            .append("rdPwd", getRdPwd())
            .append("rdAdminRoles", getRdAdminRoles())
            .toString();
    }
}
