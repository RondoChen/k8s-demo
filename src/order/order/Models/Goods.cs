using System.Text.Json.Serialization;

namespace order.Models
{
  public class Goods
  {
    [JsonPropertyName("productId")]
    public long? ProductId { get; set; }
    [JsonPropertyName("title")]
    public string Title { get; set; }
    [JsonPropertyName("tencentPrice")]
    public decimal? Price { get; set; }
    [JsonPropertyName("subtitle")]
    public string Subtitle { get; set; }
    [JsonPropertyName("coverImgUrl")]
    public string CoverImgUrl { get; set; }
  }
}
