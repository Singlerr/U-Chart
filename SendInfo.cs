using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;

namespace Uchart
{
    class SendInfo
    {
        private static String Url = "http://localhost:4500/addInfo";
        private static String ProgramName = "asdf@gmail.com/테스트론";

        public static void Send(List<KeyValuePair<string,string>> keyValues)
        {
            StringBuilder postParams = new StringBuilder();
            postParams.Append("program_name=");
            postParams.Append(ProgramName);

            postParams.Append("&os=");
            postParams.Append(Environment.OSVersion.ToString());
            postParams.Append("&uuid=");
            postParams.Append(UUID.Get());

            postParams.Append("&key_and_value=");
            for(int i = 0; i < keyValues.Count; ++i)
            {
                postParams.Append(keyValues[i].Key);
                postParams.Append(";");
                postParams.Append(keyValues[i].Value);
                postParams.Append(",");
            }
            --postParams.Length;

            Console.WriteLine(postParams.ToString());

            Encoding encoding = Encoding.UTF8;
            byte[] result = encoding.GetBytes(postParams.ToString());

            HttpWebRequest wReqFirst = (HttpWebRequest)WebRequest.Create(Url);

            wReqFirst.Method = "POST";
            wReqFirst.ContentType = "application/x-www-form-urlencoded";
            wReqFirst.ContentLength = result.Length;

            Stream postDataStream = wReqFirst.GetRequestStream();
            postDataStream.Write(result, 0, result.Length);
            postDataStream.Close();

            try
            {
                HttpWebResponse wRespFirst = (HttpWebResponse)wReqFirst.GetResponse();
                Stream respPostStream = wRespFirst.GetResponseStream();
                StreamReader readerPost = new StreamReader(respPostStream, Encoding.Default);

            }
            catch (Exception ex)
            {
            }
        }
    }
       
}
