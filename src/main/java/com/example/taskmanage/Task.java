package com.example.taskmanage;

import java.time.LocalDate;

public interface Task {
    void createTask(String taskName, String taskDescription);
    void setTaskName(String taskName);
    void setTaskDescription(String taskDescription);
    void markAsComplete();
    void setPriority(Priority priority);
    void setDeadline(LocalDate date);

}
