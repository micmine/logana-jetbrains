package com.github.micmine.loganaintelij.controller;

import com.github.micmine.loganaintelij.model.LoganaMessage;
import com.intellij.analysis.problemsView.Problem;
import com.intellij.analysis.problemsView.ProblemsCollector;
import com.intellij.openapi.project.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorController {

    List<Problem> oldMessages = List.of();
    List<Problem> messages = List.of();


    private static ErrorController instance;
    public static ErrorController getInstance() {
        if (instance == null) {
            instance = new ErrorController();
        }

        return instance;
    }

    public void dispose(Project project) {
        oldMessages.clear();
        messages.clear();
    }

    public void update(Project project, List<LoganaMessage> newMessages) {
        ProblemsCollector problemsCollector = ProblemsCollector.getInstance(project);

        this.oldMessages = this.messages;
        this.messages = newMessages.stream().map(message -> ProblemConverter.getInstance().toProblem(project, message)).collect(Collectors.toList());

        for (Problem problem: this.oldMessages) {
            problemsCollector.problemDisappeared(problem);
        }
        for (Problem problem: this.messages) {
            problemsCollector.problemAppeared(problem);
        }


    }



}
