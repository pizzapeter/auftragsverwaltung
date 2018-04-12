using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using TaskAPI;

namespace RestAPI
{
    public static class Service
    {
        private const string baseURL = "http://localhost:8080/RESTBSD/rest/";
        private const string taskURL = "getalltasks";

        public static async Task<List<UTask>> FetchAllTasks()
        {
            string url = baseURL + taskURL;
            List<UTask> all = null;

            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
                request.ContentType = "application/json";
                request.Method = "GET";

                string responseString;
                using (WebResponse response = await request.GetResponseAsync())
                {
                    using (Stream stream = response.GetResponseStream())
                    {
                        StreamReader reader = new StreamReader(stream);
                        responseString = reader.ReadToEnd();
                        reader.Close();
                        response.Close();
                    }
                }

                all = JsonConvert.DeserializeObject<List<UTask>>(responseString);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return all;
        }
    }
}
