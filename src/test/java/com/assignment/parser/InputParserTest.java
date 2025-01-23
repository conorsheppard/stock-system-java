package com.assignment.parser;

import com.assignment.entity.LedgerEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {
    private final InputParser inputParser = new InputParser();

    @Test
    public void testParseSell() {
        var entry = inputParser.parseInput("sell wine 1000");
        assertEquals(new LedgerEntry("sell", "wine", 1000, 1000), entry);
    }

    @Test
    public void testParseBuy() {
        var entry = inputParser.parseInput("buy wine 1000");
        assertEquals(new LedgerEntry("buy", "wine", 1000, 0), entry);
    }
}
