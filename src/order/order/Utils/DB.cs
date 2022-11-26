using MySql.Data.MySqlClient;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using Microsoft.Extensions.Configuration;
using System.Collections;

namespace order.Utils
{
  public class DB
  {
    private static MySqlCommand GetCommand()
    {
      var strConnection = Util.AppConfig.GetConnectionString("OrderMySql");

      MySqlConnection conn;
      MySqlCommand cmd;

      conn = new MySqlConnection();
      cmd = new MySqlCommand();

      conn.ConnectionString = strConnection;

      conn.Open();

      cmd.Connection = conn;
      return cmd;

    }
    /**
    支持 预加载 SQL，按照顺序增加参数
    */
    public static void ExecuteNonQuery(string sqlPrepare, IDictionary<string, object> args)
    {
      MySqlCommand cmd = DB.GetCommand();

      cmd.CommandText = sqlPrepare;
      cmd.Prepare();

      if (args != null)
      {
        foreach (var pair in args)
        {
          string key = pair.Key;
          object value = pair.Value;
          cmd.Parameters.AddWithValue("@" + key, value);
        }
      }
      cmd.ExecuteNonQuery();


    }


    public static DbDataReader ExecuteReader(string sqlPrepare, IDictionary<string, object> args)
    {
      MySqlCommand cmd = GetCommand();

      cmd.CommandText = sqlPrepare;
      cmd.Prepare();

      if (args != null)
      {
        foreach (var pair in args)
        {
          string key = pair.Key;
          object value = pair.Value;
          cmd.Parameters.AddWithValue("@" + key, value);
        }
      }

      return cmd.ExecuteReader();
    }

    public static T FindOne<T>(string table, string sqlWhere, IDictionary<string, object> args)
    {
      var reader = ExecuteReader($"select * from `{table}` where {sqlWhere}", args);
      if (reader.Read())
      {
        return Reflector.CreateObject<T>(reader);
      }
      return default;
    }

    public static long Count(string table, string sqlWhere, IDictionary<string, object> args)
    {
      var reader = ExecuteReader($"select count(*) from `{table}` where {sqlWhere}", args);
      if (reader.Read())
      {
        return reader.GetInt64(0);
      }
      return 0;
    }



    public static T FindOne<T>(string table, long id)
    {
      return FindOne<T>(table, "id=@id", new Dictionary<string, object>() { { "id", id } });
    }


    public static List<T> List<T>(string table, string sqlWhere, IDictionary<string, object> args)
    {
      var reader = ExecuteReader($"select * from `{table}` where {sqlWhere}", args);
      return Reflector.CreateObjects<T>(reader);
    }


    /**
     * 插入 object，返回主键的值
     * */
    public static long Insert<T>(T t)
    {
      var tT = typeof(T);
      var fields = tT.GetProperties();
      List<string> keys = new List<string>();
      List<string> values = new List<string>();
      var pps = new Dictionary<string, object>();

      foreach (var field in fields)
      {
        object v = field.GetValue(t);
        if (v != null && !(v.GetType().IsGenericType))
        {
          string kSnake = CaseExtensions.StringExtensions.ToSnakeCase(field.Name);
          string k = $"`{kSnake}`";
          keys.Add(k);
          values.Add($"@{kSnake}");
          pps.Add(kSnake, v);

        }
      }

      var table = CaseExtensions.StringExtensions.ToSnakeCase(tT.Name);
      string keysArr = string.Join(",", keys.Select(x => x.ToString()).ToArray());
      string valueArr = string.Join(",", values.Select(x => x.ToString()).ToArray());
      string sql = $@"INSERT INTO `{table}` ({keysArr}) VALUES ({valueArr});SELECT LAST_INSERT_ID()";
      var reader = ExecuteReader(sql, pps);
      long lastId = 0;
      if (reader.Read())
      {
        lastId = reader.GetInt64(0);
      }
      return lastId;
    }


  }
}