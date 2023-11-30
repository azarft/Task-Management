package com.example.taskmanage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
    private RadioButton Low;
    @FXML
    private RadioButton Medium;
    @FXML
    private RadioButton High;
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
    private TextField inputID;

    private TaskDAO taskDAO;

    @FXML
    public void initialize() {
        // Initialize the ListView
        listView.setItems(tasks);

        // Initialize TaskDAO
        taskDAO = new TaskDAO();

        // Fetch tasks from the database and populate the ListView
        fetchTasksFromDatabase();
    }

    private void fetchTasksFromDatabase() {
        try {
            // Fetch all tasks
            List<Task> tasksFromDB = taskDAO.getAllTasks();

            // Add fetched tasks to the ObservableList
            tasks.setAll(tasksFromDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onListViewSelected() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        label.setText(String.valueOf(selectedIndex));

        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);

            // Display Task ID
            inputID.setText(String.valueOf(selectedTask.getTaskID()));

            inputName.setText(selectedTask.getTaskName());
            inputDesc.setText(selectedTask.getTaskDescription());
            Deadline.setValue(selectedTask.getDeadline());

            // Set the appropriate radio button based on the task type
            String taskType = selectedTask.getTaskType();
            switch (taskType) {
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

            // Add a null check for getCompleted()
            Boolean completed = selectedTask.getCompleted();
            Completed.setSelected(Boolean.TRUE.equals(completed));
            Completed.setVisible(true);

            // Display the priority
            Priority priority = selectedTask.getPriority();
            if (priority != null) {
                switch (priority) {
                    case LOW:
                        Low.setSelected(true);
                        break;
                    case MEDIUM:
                        Medium.setSelected(true);
                        break;
                    case HIGH:
                        High.setSelected(true);
                        break;
                    default:
                        Low.setSelected(false);
                        Medium.setSelected(false);
                        High.setSelected(false);
                }
            }

        } else {
            // Clear all fields if no task is selected
            inputID.clear();
            inputName.clear();
            inputDesc.clear();
            Deadline.setValue(null);
            Homework.setSelected(false);
            Meeting.setSelected(false);
            Shopping.setSelected(false);
            Completed.setVisible(false);
            // Clear priority selection
            Low.setSelected(false);
            Medium.setSelected(false);
            High.setSelected(false);
        }
    }


    @FXML
    protected void onSaveButtonClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            updateTask(selectedIndex);
        } else {
            createNewTask();
        }
    }

    private void createNewTask() {
        Task newTask = new Task();
        newTask.setTaskName(inputName.getText());
        newTask.setTaskDescription(inputDesc.getText());
        newTask.setDeadline(Deadline.getValue());

        newTask.setTaskType(
                Homework.isSelected() ? "Homework" :
                        Meeting.isSelected() ? "Meeting" :
                                Shopping.isSelected() ? "Shopping" :
                                        " "
        );

        newTask.setCompleted(Completed.isSelected());

        // Set Priority
        if (Low.isSelected()) {
            newTask.setPriority(Priority.LOW);
        } else if (Medium.isSelected()) {
            newTask.setPriority(Priority.MEDIUM);
        } else if (High.isSelected()) {
            newTask.setPriority(Priority.HIGH);
        }

        try {
            // Save the new task to the database
            int generatedId = taskDAO.createTask(newTask);

            // Set the generated ID to the task
            newTask.setTaskID(generatedId);

            // Add the new task to the ObservableList
            tasks.add(newTask);

            // Clear input fields and reset UI elements
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message
        }
    }


    private void updateTask(int selectedIndex) {
        Task selectedTask = tasks.get(selectedIndex);

        // Update Task ID and Priority
        try {
            int updatedTaskID = Integer.parseInt(inputID.getText());
            selectedTask.setTaskID(updatedTaskID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message
        }

        // Update other task details
        selectedTask.setTaskName(inputName.getText());
        selectedTask.setTaskDescription(inputDesc.getText());
        selectedTask.setDeadline(Deadline.getValue());

        selectedTask.setTaskType(
                Homework.isSelected() ? "Homework" :
                        Meeting.isSelected() ? "Meeting" :
                                Shopping.isSelected() ? "Shopping" :
                                        " "
        );

        selectedTask.setCompleted(Completed.isSelected());

        // Update Priority
        if (Low.isSelected()) {
            selectedTask.setPriority(Priority.LOW);
        } else if (Medium.isSelected()) {
            selectedTask.setPriority(Priority.MEDIUM);
        } else if (High.isSelected()) {
            selectedTask.setPriority(Priority.HIGH);
        }

        try {
            // Update the task in the database
            taskDAO.updateTask(selectedTask);

            // Refresh the ListView to reflect the changes
            listView.refresh();

            // Deselect the item in the ListView
            listView.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message
        }
    }


    @FXML
    protected void onDeleteButtonClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);

            try {
                // Delete the task from the database
                taskDAO.deleteTask(selectedTask.getTaskID());

                // Remove the task from the ObservableList
                tasks.remove(selectedTask);

                // Optionally, refresh the ListView to reflect the changes
                listView.refresh();
                Completed.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearInputFields() {
        inputDesc.clear();
        inputName.clear();
        Deadline.setValue(null);
        Completed.setVisible(false);
    }
}
