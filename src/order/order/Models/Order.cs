using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace order.Models
{

  /**
   *  订单
   *  @Author Cloudust
   * 
   * */
  public class Order
  {

    /** 编号 */
    [JsonPropertyName("id")]
    public long? Id { get; set; }

    /** 订单状态 */
    [JsonPropertyName("order_status")]
    public long? OrderStatus { get; set; }

    /** 支付类型 */
    [JsonPropertyName("pay_type")]
    public int? PayType { get; set; }

    /** 备注 */
    [JsonPropertyName("memo")]
    public string Memo { get; set; }

    /** 地区代码 */
    [JsonPropertyName("district_code")]
    public int? DistrictCode { get; set; }

    /** 地区 */
    [JsonPropertyName("district")]
    public string District { get; set; }

    /** 邮编 */
    [JsonPropertyName("zipcode")]
    public string Zipcode { get; set; }

    /** 地址 */
    [JsonPropertyName("address")]
    public string Address { get; set; }

    /** 收货人电话 */
    [JsonPropertyName("consignee_tel")]
    public string ConsigneeTel { get; set; }

    /** 收货人名字 */
    [JsonPropertyName("consignee_name")]
    public string ConsigneeName { get; set; }

    /** 订单编号 */
    [JsonPropertyName("order_no")]
    public string OrderNo { get; set; }

    /** 打折金额 */
    [JsonPropertyName("fee_discount")]
    public decimal? FeeDiscount { get; set; }

    /** 运费 */
    [JsonPropertyName("fee_delivery")]
    public decimal? FeeDelivery { get; set; }

    /** 订单总额 */
    [JsonPropertyName("total")]
    public decimal? Total { get; set; }

    /** 支付金额 */
    [JsonPropertyName("fee_settled")]
    public decimal? FeeSettled { get; set; }

    /** 用户id */
    [JsonPropertyName("user_id")]
    public long? UserId { get; set; }

    /** 用户 */
    [JsonPropertyName("user_open_id")]
    public string UserOpenId { get; set; }

    /** 租户 */
    [JsonPropertyName("tenant_id")]
    public string TenantId { get; set; }

    /** 创建时间 */
    [JsonPropertyName("create_date")]
    public System.DateTime? CreateDate { get; set; }

    /** 创建人 */
    [JsonPropertyName("creator")]
    public string Creator { get; set; }

    /** 修改时间 */
    [JsonPropertyName("modify_date")]
    public System.DateTime? ModifyDate { get; set; }

    /** 修改人 */
    [JsonPropertyName("modifier")]
    public string Modifier { get; set; }

    /** 订单项目 */
    [JsonPropertyName("items")]
    public List<OrderItem> Items { get; set; }

  }

}



