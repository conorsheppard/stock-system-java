package com.assignment.parser;

import com.assignment.entity.LedgerEntry;

public class InputParser {
    public LedgerEntry parseInput(String input) {
        String[] inputs = input.trim().split(" ");
        return new LedgerEntry(inputs[0], inputs[1], Integer.parseInt(inputs[2]),
                inputs[0].equals("buy") ? 0 : Integer.parseInt(inputs[2]));
    }
}
