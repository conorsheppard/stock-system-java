package com.assignment.system;

import com.assignment.entity.ProductLedger;
import com.assignment.parser.InputParser;

import java.util.ArrayDeque;

import static java.io.IO.readln;

public class ProductSystem {
    public void run() {
        var inputParser = new InputParser();
        var ledger = new ProductLedger(new ArrayDeque<>());

        while (true) {
            ledger.add(inputParser.parseInput(readln("client:> ")));
            ledger.printLedger();
        }
    }
}
