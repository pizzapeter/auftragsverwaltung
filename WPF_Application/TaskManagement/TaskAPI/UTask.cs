using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Text;

namespace TaskAPI
{
    public class UTask
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public string DESCRIPTION { get; set; }
        public int FINISHED { get; set; }
        public int PLACEID { get; set; }
        private static List<UTask> all = new List<UTask>();

        public UTask(int ID, string Name, string DESCRIPTION, int FINISHED, int PLACEID)
        {
            this.ID = ID;
            this.Name = Name;
            this.DESCRIPTION = DESCRIPTION;
            this.FINISHED = FINISHED;
            this.PLACEID = PLACEID;
        }

        public static void SetAllTasks(List<UTask> allTasks)
        {
            all = allTasks;
        }

        public override string ToString()
        {
            return this.Name;
        }
    }
}
