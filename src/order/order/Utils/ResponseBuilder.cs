using System;
using System.Collections.Generic;
using order.Models;

namespace order.Utils
{
  public class ResponseBuilder
  {
    public static ResponseResult Build(Exception ex)
    {
      return new ResponseResult
      {
        Success = false,
        Error = new Error
        {
          Message = ex.Message
        }
      };
    }

    public static ResponseResult<T> Build<T>(T data)
    {
      return new ResponseResult<T>
      {
        Success = true,
        Data = data
      };
    }
  }
}