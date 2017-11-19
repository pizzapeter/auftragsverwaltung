using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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

namespace TaskManagement
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

            this.GenerateTestData();
            this.listView.Items.Clear();
           listView.ItemsSource = JobManager.GetAll();
            listView.Items.Refresh();
        }

        private void MItemNewTask_Click(object sender, RoutedEventArgs e)
        {
            NewJobWindow newJob = new NewJobWindow();
            newJob.ShowDialog();
        }

        private void MItemExit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void listView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (listView.SelectedItem != null)
            {
                Job selected = (Job)listView.SelectedItem;
                this.lblJobName.Content = selected.JobName;
                this.lblTaskDescription.Content = selected.Description;
                this.lblEmployeeName.Content = "Achmed Lee"; //TODO replace with properly code
                this.lblTaskStatus.Content = selected.Status;
                this.btnDeleteJob.IsEnabled = true;
            }
        }

        private void btnDeleteJob_Click(object sender, RoutedEventArgs e)
        {
            Job selected = (Job)listView.SelectedItem;
            JobManager.Delete(selected);
            listView.Items.Refresh();
            ResetInfos();
            this.btnDeleteJob.IsEnabled = false;
        }

        private void GenerateTestData()
        {   
            Job sample = new Job(123, "Fix broken fence", "This is a really nice job which will give u a lot of money", 69);
            JobManager.Create(sample);
            Job sample1 = new Job(234, "Fix broken glass", "dfasdf adsf a dsfad sfasdf as df", 69);
            JobManager.Create(sample1);
            Job sample2 = new Job(345, "Repair lawn mower", "This is a really nice job which will give u a lot of money", 69);
            JobManager.Create(sample2);
            Job sample3 = new Job(456, "Paint the wall green", "This is a really nice job which will give u a lot of money", 69);
            JobManager.Create(sample3);
            Job sample4 = new Job(567, "Find out who's Batman", "This is a really nice job which will give u a lot of money", 69);
            JobManager.Create(sample4);
            Job sample5 = new Job(678, "Get the Joker", "This is a really nice job which will give u a lot of money", 69);
            JobManager.Create(sample5);
        }
        private void ResetInfos()
        {
            this.lblJobName.Content = string.Empty;
            this.lblTaskDescription.Content = string.Empty;
            this.lblEmployeeName.Content = string.Empty;
            this.lblTaskStatus.Content = string.Empty;
        }
    }
}
