using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using order.Models;
using order.Services;
using order.Utils;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace order.Controllers
{

  [ApiController]
  [Route("/user/")]
  public class OrderController : ControllerBase
  {
    private readonly ILogger<OrderController> _logger;
    private readonly OrderService _orderService;
    private readonly AccountService _accountService;


    public OrderController(ILogger<OrderController> logger,
      OrderService orderService,
      AccountService accountService)
    {
      _logger = logger;
      _orderService = orderService;
      _accountService = accountService;
    }

    [HttpGet]
    [Route("orders")]
    public async Task<dynamic> List(
      [FromQuery]int? pageSize,
      [FromQuery]int? page,
      [FromQuery]string ticket)
    {
      if (ticket == null)
      {
        return ResponseBuilder.Build(new Exception("必须登录后访问"));
      }
      try
      {
        Account account = await _accountService.GetAccount(ticket);
        int limit = pageSize ?? 20;
        int offset = ((page ?? 1) - 1) * 20;
        string sqlWhere = "user_open_id=@user_open_id";
        var pas = new Dictionary<string, object> { { "user_open_id", account.OpenId } };

        var items = _orderService.List(sqlWhere + " order by id desc", limit, offset, pas);
        var total = _orderService.Count(sqlWhere, pas);
        return ResponseBuilder.Build(new ListPager<Order>()
        {
          Items = items,
          Total = total,
          Offset = offset,
          Limit = limit
        });
      }
      catch (Exception ex)
      {
        _logger.LogError(ex, ex.Message);
        return ResponseBuilder.Build(ex);
      }
    }

    [HttpGet]
    [Route("order/id/{id?}")]
    public async Task<dynamic> GetById(long? id,
      [FromQuery]string ticket)
    {
      if (!id.HasValue || id <= 0)
      {
        return ResponseBuilder.Build(new Exception("错误的ID"));
      }
      if (ticket == null)
      {
        return ResponseBuilder.Build(new Exception("必须登录后访问"));
      }
      try
      {
        Account account = await _accountService.GetAccount(ticket);
        var order = _orderService.GetById(id ?? 0);
        if (order == null)
        {
          return ResponseBuilder.Build(new Exception("没有找到订单"));
        }
        if (order.UserOpenId != account.OpenId)
        {
          return ResponseBuilder.Build(new Exception("非法订单"));
        }
        return ResponseBuilder.Build(order);
      }
      catch (Exception ex)
      {
        _logger.LogError(ex, ex.Message);
        return ResponseBuilder.Build(ex);
      }
    }

    [HttpGet]
    [Route("order/no/{no}")]
    public dynamic GetByNo(string no)
    {
      return ResponseBuilder.Build("没有实现：" + no);
    }

    [HttpPost]
    [Route("order")]
    public async Task<dynamic> CreateOrder([FromQuery] string ticket, Order order)
    {
      try
      {
        Account account = await _accountService.GetAccount(ticket);
        order.UserOpenId = account.OpenId;
        var res = await _orderService.Insert(order);
        return ResponseBuilder.Build(res);
      }
      catch (Exception ex)
      {
        _logger.LogError(ex, ex.Message);
        return ResponseBuilder.Build(ex);
      }


    }
  }
}
