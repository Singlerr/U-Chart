using System;
using System.Collections.Generic;
using Uchart;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            List<KeyValuePair<string, string>> list = new List<KeyValuePair<string, string>>();
            list.Add(new KeyValuePair<string, string>("score", "50"));
            SendInfo.Send(list);
        }
    }
}
