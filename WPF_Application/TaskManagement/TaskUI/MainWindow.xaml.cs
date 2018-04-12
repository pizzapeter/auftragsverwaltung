using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
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
using System.Windows.Navigation;
using System.Windows.Shapes;
using TaskAPI;

namespace TaskUI
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        static List<UTask> allTasks;
        public MainWindow()
        {
            InitializeComponent();
            init();
        }

        private static async void GetAllTasks(ListView listview)
        {
            allTasks = await Service.FetchAllTasks();
            UTask.SetAllTasks(allTasks);
            listview.ItemsSource = allTasks;
        }

        private void init()
        {
            GetAllTasks(this.listView);
            refreshGUI();
        }

        private void btnDeleteTask_Click(object sender, RoutedEventArgs e)
        {

        }

        private void listView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (listView.SelectedItem != null)
            {
                UTask selected = (UTask)listView.SelectedItem;
                this.lblJobName.Content = selected.Name;
                this.lblTaskDescription.Content = selected.DESCRIPTION;
                if (selected.FINISHED == 0)
                    this.lblTaskStatus.Content = "in progress";
                if (selected.FINISHED == 1)
                    this.lblTaskStatus.Content = "finished";
                this.btnAddEToTask.IsEnabled = true;
                this.btnFinishTask.IsEnabled = true;
            }
            refreshGUI();
        }

        private void refreshGUI()
        {
            this.listView.ItemsSource = allTasks;
        }

        private void MItemNewTask_Click(object sender, RoutedEventArgs e)
        {
            NewTaskWindow newTask = new NewTaskWindow();
            newTask.ShowDialog();
            refreshGUI();
        }

        private void MItemExit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnAddEToTask_Click(object sender, RoutedEventArgs e)
        {
            UTask selected = (UTask)listView.SelectedItem;
            AddEToTaskWindow addEToTaskWindow = new AddEToTaskWindow(selected.ID);
            addEToTaskWindow.ShowDialog();
        }

        private void btnFinishTask_Click(object sender, RoutedEventArgs e)
        {
            UTask selected = (UTask)listView.SelectedItem;
            FinishTask(selected.ID);
        }

        private static async void FinishTask(int taskID)
        {
            await Service.FinishTask(taskID);
        }
    }
}
