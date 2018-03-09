using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using TaskAPI;


namespace TaskAPI
{
    public static class Restcalls
    {
        private static string path = "http://localhost:8080/RESTBSD/rest/TaskService";
        static HttpClient client = new HttpClient();

        public static async Task<List<Job>> FetchTasksAsync()
        {
            string url = string.Format("http://services.groupkt.com/country/get/all");

            HttpClient client = new HttpClient();
            client.DefaultRequestHeaders.Add("Accept", "application/json");

            var jsonString = await client.GetStringAsync(url);
            JArray obj = Newtonsoft.Json.JsonConvert.DeserializeObject<JArray>(jsonString);


            List<Job> jobs = new List<Job>();
            foreach (JToken j in obj)
            {
                Job job = new Job(int.Parse(j["ID"].ToString()), j["Name"].ToString(), j["DESCRIPTION"].ToString(), j["Firstname"].ToString(), j["Lastname"].ToString());
                jobs.Add(job);
            }
            return jobs;
        }
    }
}
