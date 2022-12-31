package com.github.micmine.loganaintelij;

import com.github.micmine.loganaintelij.model.LoganaMessage;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoganaReportParserTest {

    private LoganaReportParser parser = new LoganaReportParser();

    @Test
    public void testParser() {
        List<String> log = List.of(
                "/home/michael/Documents/rust/logana/src/main.rs:16:5|type annotations needed",
                "C:\\home\\michael\\Documents\\rust\\logana\\src\\main.rs:16:5|type annotations needed"
        );

        List<LoganaMessage> result = parser.parse(log);

        assertEquals(result, List.of(
                LoganaMessage.builder()
                        .path(Path.of("/home/michael/Documents/rust/logana/src/main.rs"))
                        .text("type annotations needed")
                        .row(16)
                        .col(5)
                        .build(),
                LoganaMessage.builder()
                        .path(Path.of("C:\\home\\michael\\Documents\\rust\\logana\\src\\main.rs"))
                        .text("type annotations needed")
                        .row(16)
                        .col(5)
                        .build()
        ));
    }

}