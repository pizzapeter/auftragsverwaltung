using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using TaskAPI;

namespace TaskAPI
{
    public static class Service
    {
        private const string baseURL = "http://localhost:8080/RESTBSD/rest/";
        private const string baseTaskURL = "TaskService";

        private const string getTaskURL = baseTaskURL + "/getalltasks";
        private const string getUserURL = "UserService/users";
        private const string insertTaskURL = baseTaskURL + "/insert";
        private const string insertEmployeeToTaskURL = baseTaskURL + "/insertEmployee";
        private const string finishTaskURL = "/finishtask/";
        public static async Task<List<UTask>> FetchAllTasks()
        {
            string url = baseURL + getTaskURL;
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

        public static async Task FinishTask(int taskID)
        {
            string url = baseURL + baseTaskURL + finishTaskURL + taskID;

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
                string s = responseString;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public static async Task AddEmployeeToTask(int employeeID, int taskID)
        {
            string s = employeeID.ToString();
            HttpClient client = new HttpClient();
            var values = new Dictionary<string, string>
            {
               { "EmployeeID", employeeID.ToString() },
               { "taskID", taskID.ToString()}
            };

            var content = new FormUrlEncodedContent(values);

            var response = await client.PostAsync(baseURL + insertTaskURL, content);

            var responseString = await response.Content.ReadAsStringAsync();
        }

        public static async Task AddNewTask(string name, string description)
        {
            HttpClient client = new HttpClient();
            var values = new Dictionary<string, string>
            {
               { "name", name },
               { "description", description },
               {"PlaceID", "2" }
            };

            var content = new FormUrlEncodedContent(values);

            var response = await client.PostAsync(baseURL + insertTaskURL, content);

            var responseString = await response.Content.ReadAsStringAsync();
        }

        public static async Task<List<User>> FetchAllUser()
        {
            string url = baseURL + getUserURL;
            List<User> all = null;

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

                all = JsonConvert.DeserializeObject<List<User>>(responseString);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return all;
        }

    }
}