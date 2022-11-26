using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;

namespace order.Utils
{
  public class Util
  {
    public static IConfiguration AppConfig { get; set; }

    public static void SetField<T>(T t, string fieldName, object value)
    {

      var tType = t.GetType();
      var info = tType.GetProperty(fieldName);
      if (value is DBNull)
      {
        info.SetValue(t, null);
      }
      else
      {
        info.SetValue(t, value);
      }
    }


    public static Task<HttpResponseMessage> Fetch(
      HttpClient client, HttpContext ctx, HttpMethod method, string serivce, string url, IDictionary<string, object> args)
    {
      string host = AppConfig.GetSection("Providers").GetValue<string>(serivce);
      if (host == null)
      {
        throw new Exception("错误的服务名 service.");
      }
      string rUrl = host + url;
      // Console.WriteLine("-----:>>>> " + rUrl);

      var request = new HttpRequestMessage(method, rUrl);

      //Console.WriteLine("-----:>>>> Http Version:" + request.Version);
      var excludeHeaders = new string[]{
        "Content-Length","Host",
        "Cache-Control", "User-Agent",
        "Upgrade-Insecure-Requests"
      };

      foreach (var header in ctx.Request.Headers)
      {
        var key = header.Key;
        //Console.Write("-----:>>>> " + header.Key + ":");
        //Console.WriteLine("-----:>>>>添加Header: " + header.Key);
        try
        {
          if (!Array.Exists(excludeHeaders, e => e == key))
          {
            request.Headers.Add(key, header.Value.ToArray());
          }
        }
        catch
        {
          //Console.WriteLine(ex.Message );
          //Console.WriteLine("-----:>>>>Header 错误: " + header.Key);
        }
      }
      return client.SendAsync(request);

    }

  }
}

