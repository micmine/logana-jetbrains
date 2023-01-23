package com.github.micmine.loganaintelij;


import com.github.micmine.loganaintelij.model.LoganaMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoganaReportParser {


    private List<String> getReportFile(Path project) {
        Path path = Path.of(project.toString() + "/.logana-report");

        if (!Files.exists(path)) {
            return List.of();
        }

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Unable to read the file: " + path + " error: " + e);
            return List.of();
        }
    }

    protected List<LoganaMessage> parse(List<String> lines) {
        List<LoganaMessage> output = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }

            String dive = "";
            if (line.charAt(1) == ':') {
                dive = line.substring(0,2);
                line = line.substring(2);
            }

            String[] message = line.split("\\|");

            String location = message[0];
            String text = message[1];
            String[] split_location = location.split(":");
            String path = split_location[0];
            String row = split_location[1];
            String col = split_location[2];

            output.add(LoganaMessage.builder()
                    .text(text)
                    .path(Path.of(dive + path))
                    .row(Integer.parseInt(row))
                    .col(Integer.parseInt(col))
                    .build());
        }

        return output;
    }

    public List<LoganaMessage> parseReportFile(Path project) {
        List<String> file = getReportFile(project);

        return parse(file);
    }

}
