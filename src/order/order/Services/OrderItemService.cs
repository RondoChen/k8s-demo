using System.Collections.Generic;
using order.Models;
using order.Utils;
using System.Linq;
using System;


namespace order.Services
{
  public class OrderItemService
  {
    private readonly string table;

    public OrderItemService()
    {
      table = "order_item";
    }


    public List<OrderItem> List(long orderId)
    {
      var sqlParam = new Dictionary<string, object>();

      string sqlWhere = "order_id=@order_id limit @limit";
      sqlParam.Add("order_id", orderId);
      sqlParam.Add("limit", 2000);

      var items = DB.List<OrderItem>(table, sqlWhere, sqlParam);

      //var ids = from item in items
      //          select item.GoodsId;
      //Console.WriteLine("----------");
      //Console.WriteLine(ids.ToArray());

      return items;
    }

  }
}