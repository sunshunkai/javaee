package com.ssk.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 待转换对象
 */
@Data
public class ConversionClass {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("itemId")
    private Long itemId;

    @ApiModelProperty("价格方案id")
    private Long programId;

    @ApiModelProperty("方案标题")
    private String name;

    @ApiModelProperty("开始时间（现货必填）格式为:YYYY-MM-DD HH:MM:SS")
    private String startAt;

    @ApiModelProperty("结束时间（现货必填）格式为:YYYY-MM-DD HH:MM:SS")
    private String endAt;

    @ApiModelProperty("价格，下单价时含义为挂牌价格")
    private Long price;

    @ApiModelProperty("运费单价")
    private Long freightUnitPrice;

    @ApiModelProperty("不含税价格，下单价时含义为挂牌不含税价格")
    private Long excludePrice;

    @ApiModelProperty("价格小数位")
    private Integer priceDecimalNumber;

    @ApiModelProperty("可售数量")
    private BigDecimal capacity;

    @ApiModelProperty("已售数量")
    private BigDecimal saled;

//    @ApiModelProperty("区域定价")
//    private List<SkuAreaInfo> areaInfos;
//
//    @ApiModelProperty("时间维度定价")
//    private List<ExpectPickUpDateInfo> expectPickUpDateInfo;
//
//    @ApiModelProperty("数量折扣")
//    private List<SkuNumberDiscountInfo> numberDiscountInfos;
//
//    @ApiModelProperty("站点类型加价")
//    private StationPriceInfo stationPriceInfo;

    @ApiModelProperty("价格方法：1、固定价。2、阶梯价。")
    private Integer priceMethod;

    @ApiModelProperty("状态：1、待生效。2、生效中。3、使用中。4、失效。5，待提交")
    private Byte status;

    @ApiModelProperty("多维度开关")
    private Integer multiDimensionSwitch;

//    @ApiModelProperty("多维度价格类型")
//    private SettlementPriceType priceType;
//
//    @ApiModelProperty("取价时间条件")
//    private SettlementPriceTimeType priceTimeType;
//
//    @ApiModelProperty("多维度")
//    private MultiDimension multiDimension;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("商品单位")
    private String itemUnit;

    @ApiModelProperty("是否含税")
    private Boolean taxIncluded;

    @ApiModelProperty("综合服务费费率(不含税)")
    private BigDecimal serviceFeeRate;

    @ApiModelProperty("城建税及教育费附加费率(不含税)")
    private BigDecimal surchargeRate;

    @ApiModelProperty("税率，万分位即4位小数，400表示4%")
    private Integer taxRate;

    @ApiModelProperty("运单使用 调增调减的基准价格。")
    private Long basePrice;

    @ApiModelProperty("价格方案类型 1 买断价 0 预收价")
    private Integer type;

//    @ApiModelProperty("密度信息")
//    List <DensityInfo> densityInfos;

}
