package com.ruoyi.library.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 读者类别对象 tb_readertype
 *
 * @author xlz
 * @date 2025-12-17
 */
public class TbReadertype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 读者类别【主键】 */
    private Integer rdType; // 课设小数值用Integer，避免Long转Integer异常

    /** 读者类别名称【唯一非空】 */
    @Excel(name = "读者类别名称") // 简化Excel注解，保留核心含义
    private String rdTypeName;

    /** 可借书数量 */
    @Excel(name = "可借书数量")
    private Integer canLendQty; // 小驼峰 + Integer（适配课设）

    /** 可借书天数 */
    @Excel(name = "可借书天数")
    private Integer canLendDay; // 小驼峰 + Integer

    /** 可续借次数 */
    @Excel(name = "可续借次数")
    private Integer canContinueTimes; // 小驼峰 + Integer

    /** 罚款率(元/天) */
    @Excel(name = "罚款率(元/天)")
    private BigDecimal punishRate; // 小驼峰

    /** 证书有效期(年，0=永久有效) */
    @Excel(name = "证书有效期(年，0=永久有效)")
    private Integer dateValid; // 小驼峰 + Integer

    // ========== 以下是规范的getter/setter（小驼峰） ==========
    public void setRdType(Integer rdType)
    {
        this.rdType = rdType;
    }

    public Integer getRdType()
    {
        return rdType;
    }

    public void setRdTypeName(String rdTypeName)
    {
        this.rdTypeName = rdTypeName;
    }

    public String getRdTypeName()
    {
        return rdTypeName;
    }

    public void setCanLendQty(Integer canLendQty)
    {
        this.canLendQty = canLendQty;
    }

    public Integer getCanLendQty()
    {
        return canLendQty;
    }

    public void setCanLendDay(Integer canLendDay)
    {
        this.canLendDay = canLendDay;
    }

    public Integer getCanLendDay()
    {
        return canLendDay;
    }

    public void setCanContinueTimes(Integer canContinueTimes)
    {
        this.canContinueTimes = canContinueTimes;
    }

    public Integer getCanContinueTimes()
    {
        return canContinueTimes;
    }

    public void setPunishRate(BigDecimal punishRate)
    {
        this.punishRate = punishRate;
    }

    public BigDecimal getPunishRate()
    {
        return punishRate;
    }

    public void setDateValid(Integer dateValid)
    {
        this.dateValid = dateValid;
    }

    public Integer getDateValid()
    {
        return dateValid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("rdType", getRdType())
                .append("rdTypeName", getRdTypeName())
                .append("canLendQty", getCanLendQty()) // 小驼峰
                .append("canLendDay", getCanLendDay()) // 小驼峰
                .append("canContinueTimes", getCanContinueTimes()) // 小驼峰
                .append("punishRate", getPunishRate()) // 小驼峰
                .append("dateValid", getDateValid()) // 小驼峰
                .toString();
    }
}