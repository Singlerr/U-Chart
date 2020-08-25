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
            list.Add(new KeyValuePair<string, string>("<여기에 KEY를 넣으세요>", "<여기에 VALUE를 넣으세요>"));
            SendInfo.Send(list); //정보를 서버에 전송
        }
    }
}
