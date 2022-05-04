package com.company;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    private Report report;

    @Test
    void getType() {
        report = new Report("type", "context");
        Assertions.assertEquals("type", report.getType());
    }

    @Test
    void getContent() {
        report = new Report("type", "context");
        Assertions.assertEquals("context", report.getContent());
    }
}