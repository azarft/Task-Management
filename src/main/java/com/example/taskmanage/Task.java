package com.example.taskmanage;

import java.time.LocalDate;

public class Task{

    private int taskID;
    private String taskType;
    private String taskName;

    private String TaskDescription;
    private boolean completed;
    private Priority priority;
    private LocalDate deadline;

    public String getTaskDescription() {
        return TaskDescription;
    }
    public Task(){
    }
    public Task(int taskID) {
        this.taskID = taskID;
    }
    public Task(String taskName) {
        this.taskName = taskName;
    }
    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void createTask(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.TaskDescription = taskDescription;
        this.completed = false;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public void setTaskDescription(String taskDescription) {
        this.TaskDescription = taskDescription;
    }


    public void markAsComplete() {
        this.completed = true;
    }


    public void setPriority(Priority priority) {
        this.priority = priority;
    }


    public void setDeadline(LocalDate date) {
        this.deadline = date;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return TaskDescription;
    }

    public void setDescription(String description) {
        this.TaskDescription = description;
    }

    public String toString(){
        String azar;
        if (this.completed){
            azar = "completed";
        }
        else
        {
            azar = "not completed";
        }

        return this.taskType + ": " + this.taskName + " " + this.deadline + " " + azar;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void markAsIncomplete() {
        this.completed = false;
    }

    public boolean getCompleted()
    {
        return completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
