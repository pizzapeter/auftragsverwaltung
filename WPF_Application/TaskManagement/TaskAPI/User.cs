using System;
using System.Collections.Generic;
using System.Text;

namespace TaskAPI
{
    public class User
    {
        public int id { get; set; }
        public string firstname { get; set; }
        public string lastname { get; set; }
        public string date_of_birth { get; set; }
        public string departmentName { get; set; }
        public int permissionLevelID { get; set; }
        private static List<User> allUsers = new List<User>();

        public User(int id, string firstname, string lastname, string date_of_birth, string departmentName, int permissionLevelID)
        {
            this.id = id;
            this.firstname = firstname;
            this.lastname = lastname;
            this.date_of_birth = date_of_birth;
            this.departmentName = departmentName;
            this.permissionLevelID = permissionLevelID;
        }

        public static List<User> GetAllUsers()
        {
            return allUsers;
        }
        public static void SetAllUsers(List<User> all)
        {
            allUsers = all;
        }

        public override string ToString()
        {
            return this.firstname + " " + this.lastname;
        }
    }
}
