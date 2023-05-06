package com.github.micmine.loganaintelij;

import com.github.micmine.loganaintelij.controller.ErrorController;
import com.github.micmine.loganaintelij.model.LoganaMessage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.vfs.VirtualFile;

import java.nio.file.Path;
import java.util.List;

public class LoadLoganaReportAction extends AnAction {

    LoganaReportParser parser = new LoganaReportParser();

    @Override
    public void actionPerformed(@org.jetbrains.annotations.NotNull AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile selected = FileEditorManager.getInstance(project).getSelectedEditor().getFile();
        Module module = FileIndexFacade.getInstance(project).getModuleForFile(selected);
        Path projectDirectory = Path.of(ProjectUtil.guessModuleDir(module).getPath());

        List<LoganaMessage> messages = parser.parseReportFile(projectDirectory);

        ErrorController.getInstance().update(project, messages);
    }
}