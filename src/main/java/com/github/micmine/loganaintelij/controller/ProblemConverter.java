package com.github.micmine.loganaintelij.controller;

import com.github.micmine.loganaintelij.model.LoganaMessage;
import com.intellij.analysis.problemsView.FileProblem;
import com.intellij.analysis.problemsView.Problem;
import com.intellij.analysis.problemsView.ProblemsProvider;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.content.AlertIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;

public class ProblemConverter {

    private HashMap<Project, ProblemsProvider> problemsProviderHashMap = new HashMap<>();

    private ProblemConverter(){}

    private static ProblemConverter instance;
    public static ProblemConverter getInstance() {
        if (instance == null) {
            instance = new ProblemConverter();
        }

        return instance;
    }

    private ProblemsProvider getProblemProvider(Project project) {
        ProblemsProvider provider = problemsProviderHashMap.get(project);

        if (provider == null) {
            ProblemsProvider problemsProvider = new ProblemsProvider() {
                @Override
                public void dispose() {
                    ErrorController.getInstance().dispose(project);
                }

                @NotNull
                @Override
                public Project getProject() {
                    return project;
                }
            };

            return problemsProvider;
        }
        return provider;
    }

    public FileProblem toProblem(Project project,LoganaMessage message) {
        FileProblem problem = new FileProblem() {
            @Override
            public int getLine() {
                return message.getRow();
            }

            @NotNull
            @Override
            public VirtualFile getFile() {
                return LocalFileSystem.getInstance().findFileByNioFile(message.getPath());
            }

            @Override
            public int getColumn() {
                return message.getCol();
            }

            @NotNull
            @Override
            public ProblemsProvider getProvider() {
                return getProblemProvider(project);
            }

            @NotNull
            @Override
            public String getText() {
                return message.getText();
            }

            @Nullable
            @Override
            public String getGroup() {
                return null;
            }

            @Nullable
            @Override
            public String getDescription() {
                return null;
            }

            @NotNull
            public Icon getIcon() {
                return AllIcons.General.Error;
            }
        };

        return problem;
    }
}
