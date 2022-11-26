using System;
using System.Collections.Generic;
using System.Data.Common;
using Microsoft.Extensions.DependencyInjection;
using System.Linq;
using System.Reflection;

namespace order.Utils
{
  public class Reflector
  {
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

    /**
     * 给 实体设置值
     * */
    public static T CreateObject<T>(DbDataReader reader)
    {
      var tT = typeof(T);
      T t = (T)Activator.CreateInstance(tT);

      var fields = tT.GetProperties();
      for (int i = 0; i < reader.FieldCount; i++)
      {
        string colName = reader.GetName(i);
        foreach (var field in fields)
        {
          string fieldName = field.Name;
          if (colName.Equals(CaseExtensions.StringExtensions.ToSnakeCase(fieldName)))
          {
            object value = reader.GetValue(i);
            if (value is DBNull)
            {
              field.SetValue(t, null);
            }
            else
            {
              field.SetValue(t, value);
            }
            break;
          }
        }
      }
      return t;

    }

    /**
     * 给 实体设置值
     * */
    public static List<T> CreateObjects<T>(DbDataReader reader)
    {
      List<T> list = new List<T>();
      while (reader.Read())
      {
        T t = CreateObject<T>(reader);
        list.Add(t);
      }
      return list;
    }

    public static void InjectAllServices(IServiceCollection services, string ns)
    {
      var tps = from t in Assembly.GetExecutingAssembly().GetTypes()
                where t.IsClass && t.Namespace == ns
                select t;

      foreach (Type tp in tps)
      {
        services.AddSingleton(tp);
        //services.AddScoped<tp>();
      }
    }



  }
}
