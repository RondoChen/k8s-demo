
using order.Models;
using System.Collections.Generic;
using order.Utils;
using System.Linq;
using System;
using System.Threading.Tasks;

namespace order.Services
{
  public class OrderService
  {

    private readonly string table;
    private readonly OrderItemService _orderItemService;
    private readonly GoodsService _goodsService;


    public OrderService(OrderItemService orderItemService, GoodsService goodsService)
    {
      table = "order";
      _orderItemService = orderItemService;
      _goodsService = goodsService;
    }

    public async Task<Order> Insert(Order order)
    {
      if (order.Items == null || order.Items.Count == 0)
      {
        throw new Exception("订单中没有商品");
      }
      if (string.IsNullOrWhiteSpace(order.ConsigneeName))
      {
        throw new Exception("没有收货人");
      }
      if (string.IsNullOrWhiteSpace(order.Address))
      {
        throw new Exception("没有收货地址");
      }
      if (string.IsNullOrWhiteSpace(order.ConsigneeTel))
      {
        throw new Exception("没有收货人电话");
      }

      foreach (var item in order.Items)
      {
        if (!item.GoodsId.HasValue || item.GoodsId <= 0)
        {
          throw new Exception("必须指定商品");
        }
        if (!item.Quantity.HasValue || item.Quantity <= 0)
        {
          throw new Exception("商品至少要购买一个");
        }

      }



      //远程调用 商品库
      var ids = from item in order.Items
                select item.GoodsId ?? 0;

      IEnumerable<Goods> goods = await _goodsService.GetGoods(ids.ToArray<long>());

      decimal xtotal = 0;
      foreach (var item in order.Items)
      {
        item.TenantId = order.TenantId;
        item.UserId = order.UserId;
        item.UserOpenId = order.UserOpenId;
        var tarGoods = (from go in goods where go.ProductId == item.GoodsId select go).First();
        if (tarGoods != null)
        {
          item.Title = tarGoods.Title;
          item.Price = tarGoods.Price;
        }
        xtotal += (item.Price ?? 0) * (item.Quantity ?? 0);
      }

      order.Total = xtotal;
      long orderId = DB.Insert(order);
      order.Id = orderId;
      foreach (var item in order.Items)
      {
        item.OrderId = orderId;
        DB.Insert(item);
      }

      return await Task.Run<Order>(() =>
       {
         return order;
       });


    }


    public IEnumerable<Order> List(string sqlWhere, int? limit, int? offset, IDictionary<string, object> sqlParam)
    {
      limit ??= 500;
      offset ??= 0;

      //DbDataReader reader = DB.ExecuteReader("select * from `order` where " + sqlWhere +
      //  " limit " + limit + " offset " + offset, sqlParam);
      //List<Order> items = Reflector.CreateObjects<Order>(reader);

      var items = DB.List<Order>("order", sqlWhere + " limit " + limit + " offset " + offset, sqlParam);
      //List<Order> items = Reflector.CreateObjects<Order>(reader);
      return items;
    }

    public long Count(string sqlWhere, IDictionary<string, object> sqlParam)
    {
      return DB.Count(table, sqlWhere, sqlParam);

    }

    public Order GetById(long id)
    {
      var order = DB.FindOne<Order>(table, id);
      //Order order = Reflector.CreateObject<Order>(reader);
      order.Items = _orderItemService.List(id);

      return order;

    }

  }
}
