using System;
using System.Text.Json.Serialization;

namespace order.Models
{

  /**
   *  订单项
   *  @Author Cloudust
   * 
   * */
  public class OrderItem
  {

    /** 编号 */
    [JsonPropertyName("id")]
    public long? Id { get; set; }

    /** 订单ID */
    [JsonPropertyName("order_id")]
    public long? OrderId { get; set; }

    /** 商品ID */
    [JsonPropertyName("goods_id")]
    public long? GoodsId { get; set; }

    /** 标题 */
    [JsonPropertyName("title")]
    public string Title { get; set; }

    /** 商品类型 */
    [JsonPropertyName("goods_status")]
    public int? GoodsStatus { get; set; }

    /** 售价 */
    [JsonPropertyName("price")]
    public decimal? Price { get; set; }

    /** 数量 */
    [JsonPropertyName("quantity")]
    public int? Quantity { get; set; }

    /** 用户ID */
    [JsonPropertyName("user_id")]
    public long? UserId { get; set; }

    /** 用户open_id */
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

  }

}



