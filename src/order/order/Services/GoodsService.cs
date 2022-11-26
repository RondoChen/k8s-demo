using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using order.Models;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace order.Services
{
  public class GoodsService
  {
    private readonly IHttpClientFactory _factory;
    private readonly IHttpContextAccessor _httpContextAccessor;

    public GoodsService(IHttpClientFactory factory, IHttpContextAccessor httpContextAccessor)
    {
      _factory = factory;
      _httpContextAccessor = httpContextAccessor;
    }

    public async Task<IEnumerable<Goods>> GetGoods(long[] ids)
    {
      var client = _factory.CreateClient();
      var context = _httpContextAccessor.HttpContext;


      var resMsg = await Utils.Util.Fetch(
           client, context, HttpMethod.Get,
           "product", "/apis/product/list/" + string.Join(",", ids) + "/", null);
      var resStr = await resMsg.Content.ReadAsStringAsync();
      //Console.WriteLine(resStr);

      JsonDocument doc = JsonDocument.Parse(resStr);

      var strData = doc.RootElement.GetProperty("data").GetProperty("items").GetRawText();

      // Console.WriteLine("-------------");
      // Console.WriteLine(strData);


      List<Goods> objRes = JsonSerializer.Deserialize<List<Goods>>(strData);

      // Console.WriteLine("-------------");
      // Console.WriteLine(objRes.Count);



      return objRes;

    }
  }
}
