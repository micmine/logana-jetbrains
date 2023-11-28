package com.github.micmine.loganaintelij.model;

import java.nio.file.Path;

public record LoganaMessage(
		String text,
		Path path,
		Integer row,
		Integer col
) {
}
