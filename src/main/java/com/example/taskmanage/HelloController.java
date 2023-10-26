package com.example.taskmanage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class HelloController {
    @FXML
    private CheckBox Completed;
    @FXML
    private RadioButton Homework;
    @FXML
    private RadioButton Meeting;
    @FXML
    private RadioButton Shopping;
    @FXML
    private DatePicker Deadline;

    @FXML
    private Button Delete;
    @FXML
    private ListView<Task> listView;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputDesc;

    @FXML
    private Label label;

    @FXML
    public void initialize() {
        // Initialize the ListView
        listView.setItems(tasks);
    }

    @FXML
    protected void onListViewSelected() {
        int i = listView.getSelectionModel().getSelectedIndex();
        label.setText("" + i);

        if (i >= 0) {
            HomeworkTask selectedTask = (HomeworkTask) tasks.get(i);

            inputName.setText(selectedTask.getTaskName());
            inputDesc.setText(selectedTask.getDescription());
            Deadline.setValue(selectedTask.getDeadline());

            // Set the appropriate radio button based on the task type
            switch (selectedTask.getTaskType()) {
                case "Homework":
                    Homework.setSelected(true);
                    break;
                case "Meeting":
                    Meeting.setSelected(true);
                    break;
                case "Shopping":
                    Shopping.setSelected(true);
                    break;
                default:
                    Homework.setSelected(false);
                    Meeting.setSelected(false);
                    Shopping.setSelected(false);
            }
            Completed.setVisible(true);
        }
        else
        {
            Completed.setVisible(false);
        }
    }


    @FXML
    protected void onSaveButtonClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            HomeworkTask selectedTask = (HomeworkTask) tasks.get(selectedIndex);

            selectedTask.setTaskName(inputName.getText());
            selectedTask.setDescription(inputDesc.getText());

            LocalDate selectedDate = Deadline.getValue();
            if (selectedDate != null) {
                selectedTask.setDeadline(selectedDate);
            } else {
                // Handle the case when no date is selected
            }

            selectedTask.setTaskType(
                    Homework.isSelected() ? "Homework" :
                            Meeting.isSelected() ? "Meeting" :
                                    Shopping.isSelected() ? "Shopping" :
                                            " "
            );

            if (Completed.isSelected()) {
                selectedTask.markAsComplete();
            } else {
                // Optionally, you can mark it as incomplete if the checkbox is not selected
                selectedTask.markAsIncomplete();
            }

            // Update the task in the list to reflect changes
            listView.refresh();

            // Clear input fields and deselect the item in the ListView
            inputDesc.clear();
            inputName.clear();
            Deadline.setValue(null);
            Completed.setVisible(false);
            listView.getSelectionModel().clearSelection();
        } else {
            // No item selected, create a new task
            HomeworkTask ht = new HomeworkTask();
            ht.createTask(inputName.getText(), inputDesc.getText());

            LocalDate selectedDate = Deadline.getValue();
            if (selectedDate != null) {
                ht.setDeadline(selectedDate);
            } else {
                // Handle the case when no date is selected
            }

            ht.setTaskType(
                    Homework.isSelected() ? "Homework" :
                            Meeting.isSelected() ? "Meeting" :
                                    Shopping.isSelected() ? "Shopping" :
                                            " "
            );

            if (Completed.isSelected()) {
                ht.markAsComplete();
            }

            tasks.add(ht);

            // Clear input fields and reset UI elements
            inputDesc.clear();
            inputName.clear();
            Deadline.setValue(null);
            Completed.setVisible(false);
        }
    }
    @FXML
    protected void onDeleteButtonClick() {
        // Create a list to store the completed tasks that need to be removed
        ObservableList<Task> completedTasks = FXCollections.observableArrayList();

        // Iterate through the tasks and identify completed tasks
        for (Task task : tasks) {
            if (task instanceof HomeworkTask) {
                HomeworkTask homeworkTask = (HomeworkTask) task;
                if (homeworkTask.getCompleted()) {
                    completedTasks.add(task);
                }
            }
        }

        // Remove the completed tasks from the main list
        tasks.removeAll(completedTasks);

        // Optionally, refresh the ListView to reflect the changes
        listView.refresh();
        Completed.setVisible(false);
    }
}


