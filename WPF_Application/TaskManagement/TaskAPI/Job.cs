using System;
using System.Collections.Generic;
using System.Text;

namespace TaskAPI
{
    public class Job
    {
        public int ID { get; private set; }
        public string JobName { get; private set; }
        public string Description { get; private set; }
        public JobStatus Status { get; private set; }
        public int EmployeeID { get; private set; }
        public Place Location { get; private set; }
        public Job(int iD, string jobName, string description, int employeeID)
        {
            ID = iD;
            JobName = jobName;
            Description = description;
            Status = JobStatus.InProgress;
            EmployeeID = employeeID;
           // Location = location;
        }
        public Job(Job toCopy)
        {
            ID = toCopy.ID;
            JobName = toCopy.JobName;
            Description = toCopy.Description;
            Status = toCopy.Status;
            EmployeeID = toCopy.EmployeeID;
            // Location = toCopy.Location;
        }

        public override string ToString()
        {
            return this.JobName;
        }
    }
}