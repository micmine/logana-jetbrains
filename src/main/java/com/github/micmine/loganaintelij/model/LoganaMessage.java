package com.github.micmine.loganaintelij.model;

import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

@Data
@Builder
public class LoganaMessage {
    private String text;
    private Path path;
    private Integer row;
    private Integer col;
}
