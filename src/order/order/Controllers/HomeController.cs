using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using order.Models;
using order.Services;
using System;
using order.Utils;

namespace order.Controllers
{
  [ApiController]
  public class HomeController : ControllerBase
  {
    private readonly IHttpClientFactory _factory;
    private readonly GoodsService _goodsService;

    public HomeController(IHttpClientFactory factory, GoodsService goodsService)
    {
      _factory = factory;
      _goodsService = goodsService;
    }

    [Route("/healthz/ready")]
    [HttpGet]
    public ActionResult Health()
    {
      return Ok("ok");
      //string strIds = "1,2,3";
      //Utils.Util.Fetch(
      //  _factory.CreateClient(), HttpContext, HttpMethod.Get,
      //  "product", "/apis/product/list/" + strIds + "/", null);

      // try
      // {
      //   return await _goodsService.GetGoods(new long[] { 1L, 2L, 3L });
      // }
      // catch (Exception ex)
      // {
      //   return ResponseBuilder.Build(ex);
      // }

      //return  ResponseBuilder.Build(new Order
      //{
      //  CreateDate = System.DateTime.Now,
      //  Memo = Util.AppConfig.GetConnectionString("OrderMySql")
      //});
      //return ResponseResult.Build("Welcome to order service.");
    }
  }
}
