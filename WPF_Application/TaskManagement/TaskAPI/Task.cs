using System;
using System.Collections.Generic;
using System.Text;

namespace TaskAPI
{
    public class Task
    {
        public int ID { get; private set; }
        public string Description { get; set; }
        public bool TaskStatus { get; private set; }
        public int EmployeeID { get; private set; }
        public Place Location { get; private set; }
        public Task(int iD, string description, bool taskStatus, int employeeID, Place location)
        {
            ID = iD;
            Description = description;
            TaskStatus = taskStatus;
            EmployeeID = employeeID;
           // Location = location;
        }
    }
}