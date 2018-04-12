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
    /// Interaction logic for NewTaskWindow.xaml
    /// </summary>
    public partial class NewTaskWindow : Window
    {
        public NewTaskWindow()
        {
            InitializeComponent();
            
        }

        

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnSave_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                CheckInputs();
                AddNewTask();
                this.Close();
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void CheckInputs()
        {
            if (tbxJobName.Text.Trim() == "")
                throw new Exception("Please fill in a Name for this Task");
            if (tbxDescription.Text.Trim() == "")
                throw new Exception("Please fill in a Description for this Task");
        }

        private async void AddNewTask()
        {
            await Service.AddNewTask(tbxJobName.Text.Trim(), tbxDescription.Text.Trim());
        }
    }
}
