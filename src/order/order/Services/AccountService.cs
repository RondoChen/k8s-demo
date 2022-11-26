using System;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using order.Models;
using System.Text.Json;

namespace order.Services
{
  public class AccountService
  {
    private readonly IHttpClientFactory _factory;
    private readonly IHttpContextAccessor _httpContextAccessor;

    public AccountService(IHttpClientFactory factory, IHttpContextAccessor httpContextAccessor)
    {
      _factory = factory;
      _httpContextAccessor = httpContextAccessor;
    }

    public async Task<Account> GetAccount(string ticket)
    {
      var client = _factory.CreateClient();
      var context = _httpContextAccessor.HttpContext;

      var resMsg = await Utils.Util.Fetch(
           client, context, HttpMethod.Get,
           "passport", "/open/account/info?ticket=" + ticket, null);
      var resStr = await resMsg.Content.ReadAsStringAsync();
      // Console.WriteLine(resStr);

      JsonDocument doc = JsonDocument.Parse(resStr);

      var jsonSuccess = doc.RootElement.GetProperty("success").GetBoolean();
      if (!jsonSuccess)
      {
        throw new Exception("错误的登录凭证");
      }
      var jsonAccount = doc.RootElement.GetProperty("data");

      return new Account
      {
        Email = jsonAccount.GetProperty("email").GetString(),
        Nick = jsonAccount.GetProperty("nick").GetString(),
        Mobile = jsonAccount.GetProperty("mobile").GetString(),
        OpenId = jsonAccount.GetProperty("open_id").GetString(),
        Avatar = jsonAccount.GetProperty("avatar").GetString()
      };

    }
  }
}
