using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace TaskAPI
{
    public static class UTaskManager
    {
        private static List<UTask> _allTasks = new List<UTask>();

        public static void Create(UTask t)
        {
            try
            {
                if (t == null)
                    throw new Exception("Job can not be null");

               // _allJobs.Add(new UTask(t));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        public static async Task<List<UTask>> GetAll()
        {
            await Service.FetchAllTasks();
            return _allTasks;
        }
    }
}
