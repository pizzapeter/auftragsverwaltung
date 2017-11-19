using System;
using System.Collections.Generic;
using System.Text;

namespace TaskAPI
{
    public static class JobManager
    {
        private static List<Job> _allJobs = new List<Job>();

        public static void Create(Job t)
        {
            try
            {
                if (t == null)
                    throw new Exception("Job can not be null");

                _allJobs.Add(new Job(t));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }
        public static void Delete(Job toDelete)
        {
            _allJobs.Remove(toDelete);
        }
        public static void Update()
        {
            throw new NotImplementedException();
        }
        public static List<Job> GetAll()
        {
            return _allJobs;
        }
    }
}
