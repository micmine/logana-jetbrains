package com.github.micmine.loganaintelij;

import com.github.micmine.loganaintelij.controller.ErrorController;
import com.github.micmine.loganaintelij.model.LoganaMessage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

import java.nio.file.Path;
import java.util.List;

public class LoadLoganaReportAction extends AnAction {

    LoganaReportParser parser = new LoganaReportParser();

    @Override
    public void actionPerformed(@org.jetbrains.annotations.NotNull AnActionEvent e) {
        Project project = e.getProject();
        String projectDirectory = getProjectDirectory(project.getBasePath());

        List<LoganaMessage> messages = parser.parseReportFile(Path.of(projectDirectory));

        ErrorController.getInstance().update(project, messages);
    }

    private String getProjectDirectory(String basePath) {
        int idea = basePath.indexOf(".idea");

        if (idea == -1) {
            return basePath;
        }

        return basePath.substring(0, idea - 1);

    }
}
