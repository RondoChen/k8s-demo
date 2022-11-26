using System.Collections.Generic;

namespace order.Models
{
  public class ListPager<T>
  {
    public long Total { get; set; }
    public int Offset { get; set; }
    public int Limit { get; set; }
    public IEnumerable<T> Items { get; set; }
  }
}