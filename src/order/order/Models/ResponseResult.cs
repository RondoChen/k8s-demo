using System;

namespace order.Models
{
  public class ResponseResult
  {
    public ResponseResult()
    {
      Service = "order";
      Timestamp = DateTimeOffset.UtcNow.ToUnixTimeSeconds() * 1000;
    }
    public string Service { get; set; }
    public Error Error { get; set; }
    public bool Success { get; set; }
    public long Timestamp { get; set; }
  }

  public class ResponseResult<T> : ResponseResult
  {
    public T Data { get; set; }
  }

  public class Error
  {
    public string Message { get; set; }
    public string Code { get; set; }
  }

}