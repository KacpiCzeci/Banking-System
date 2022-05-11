package com.company;

import com.company.Report.Report;
import org.junit.jupiter.api.*;

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