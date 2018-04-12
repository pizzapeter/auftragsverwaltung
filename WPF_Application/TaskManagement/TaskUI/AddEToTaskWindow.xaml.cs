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

namespace TaskUI
{
    /// <summary>
    /// Interaction logic for AddEToTaskWindow.xaml
    /// </summary>
    public partial class AddEToTaskWindow : Window
    {
        public static int _employeeID = 0;
        public static int _id = 0;
        public AddEToTaskWindow(int id)
        {
            InitializeComponent();
            SetAllUsers(cbxEmployees);
            _id = id;
        }

        private static async void SetAllUsers(ComboBox cbxEmployees)
        {
            List<User> all = await Service.FetchAllUser();
            User.SetAllUsers(all);
            cbxEmployees.ItemsSource = all;
        }

        private void btnSave_Click(object sender, RoutedEventArgs e)
        {
            User u = (User)cbxEmployees.SelectedItem;
            if (u != null)
            {
                _employeeID = u.id;
                AddEmployeeToTask();
            }
            this.Close();
        }

        private static async void AddEmployeeToTask()
        {
            await Service.AddEmployeeToTask(_employeeID, _id);
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
