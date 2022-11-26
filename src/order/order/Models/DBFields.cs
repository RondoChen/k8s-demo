using System.Collections.Generic;

namespace order.Models
{
  public static class DBFieldMap
  {


    public static readonly IDictionary<string, string> Order =
      new Dictionary<string, string>(){

        {"id", "Id"},
        {"order_status", "OrderStatus"},
        {"pay_type", "PayType"},
        {"memo", "Memo"},
        {"district_code", "DistrictCode"},
        {"district", "District"},
        {"zipcode", "Zipcode"},
        {"address", "Address"},
        {"consignee_tel", "ConsigneeTel"},
        {"consignee_name", "ConsigneeName"},
        {"order_no", "OrderNo"},
        {"fee_discount", "FeeDiscount"},
        {"fee_delivery", "FeeDelivery"},
        {"total", "Total"},
        {"fee_settled", "FeeSettled"},
        {"user_id", "UserId"},
        {"user_open_id", "UserOpenId"},
        {"tenant_id", "TenantId"},
        {"create_date", "CreateDate"},
        {"creator", "Creator"},
        {"modify_date", "ModifyDate"},
        {"modifier", "Modifier"},
      };




    public static readonly IDictionary<string, string> RevOrder =
      new Dictionary<string, string>(){

        {"Id", "id"},
        {"OrderStatus", "order_status"},
        {"PayType", "pay_type"},
        {"Memo", "memo"},
        {"DistrictCode", "district_code"},
        {"District", "district"},
        {"Zipcode", "zipcode"},
        {"Address", "address"},
        {"ConsigneeTel", "consignee_tel"},
        {"ConsigneeName", "consignee_name"},
        {"OrderNo", "order_no"},
        {"FeeDiscount", "fee_discount"},
        {"FeeDelivery", "fee_delivery"},
        {"Total", "total"},
        {"FeeSettled", "fee_settled"},
        {"UserId", "user_id"},
        {"UserOpenId", "user_open_id"},
        {"TenantId", "tenant_id"},
        {"CreateDate", "create_date"},
        {"Creator", "creator"},
        {"ModifyDate", "modify_date"},
        {"Modifier", "modifier"},
      };




    public static readonly IDictionary<string, string> OrderItem =
      new Dictionary<string, string>(){

        {"id", "Id"},
        {"order_id", "OrderId"},
        {"goods_id", "GoodsId"},
        {"title", "Title"},
        {"goods_status", "GoodsStatus"},
        {"price", "Price"},
        {"quantity", "Quantity"},
        {"user_id", "UserId"},
        {"user_open_id", "UserOpenId"},
        {"tenant_id", "TenantId"},
        {"create_date", "CreateDate"},
        {"creator", "Creator"},
        {"modify_date", "ModifyDate"},
        {"modifier", "Modifier"},
      };




    public static readonly IDictionary<string, string> RevOrderItem =
      new Dictionary<string, string>(){

        {"Id", "id"},
        {"OrderId", "order_id"},
        {"GoodsId", "goods_id"},
        {"Title", "title"},
        {"GoodsStatus", "goods_status"},
        {"Price", "price"},
        {"Quantity", "quantity"},
        {"UserId", "user_id"},
        {"UserOpenId", "user_open_id"},
        {"TenantId", "tenant_id"},
        {"CreateDate", "create_date"},
        {"Creator", "creator"},
        {"ModifyDate", "modify_date"},
        {"Modifier", "modifier"},
      };





  }
}

