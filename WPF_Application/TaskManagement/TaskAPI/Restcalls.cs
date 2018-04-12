using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;


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
