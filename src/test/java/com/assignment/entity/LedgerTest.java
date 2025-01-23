package com.assignment.entity;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerTest {
    private ArrayDeque<LedgerEntry> queue = new ArrayDeque<>();
    private Ledger ledger = new ProductLedger(queue);

    @Test
    public void testAddToLedger() {
        var expectedLedgerEntry = new LedgerEntry("sell", "wine", 500, 500);
        ledger.add(expectedLedgerEntry);

        assertEquals(1, queue.size());
        assertEquals(expectedLedgerEntry, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testBuyFromEmptyLedger() {
        var ledgerEntryBuyFromEmptyLedger = new LedgerEntry("buy", "wine", 500, 0);
        ledger.add(ledgerEntryBuyFromEmptyLedger);

        assertTrue(queue.isEmpty());
        assertNull(queue.poll());
    }

    @Test
    public void testSellThenBuyEqual() {
        var entrySell = new LedgerEntry("sell", "wine", 500, 500);
        var entryBuy = new LedgerEntry("buy", "wine", 500, 0);

        ledger.add(entrySell);
        ledger.add(entryBuy);

        assertEquals(entrySell, queue.getFirst());
        queue.pop();
        assertEquals(entryBuy, queue.getFirst());
    }

    @Test
    public void testPrintLedger() {
        var queue = new ArrayDeque<LedgerEntry>();
        var ledger = new ProductLedger(queue);
        var expectedLedgerEntry = new LedgerEntry("sell", "wine", 1000, 1000);
        ledger.add(expectedLedgerEntry);

        var outputStream = new ByteArrayOutputStream();
        var ps = new PrintStream(outputStream);
        System.setOut(ps);

        ledger.printLedger();

        assertEquals("system:< sell wine 1000 remaining:1000", outputStream.toString().trim());
    }

    @SneakyThrows
    @Test
    public void testSellAndBuyEntriesOnLedger() {
        List<LedgerEntry> ledgerEntries = getLedgerEntries();

        ledgerEntries.forEach(ledger::add);

        assertFalse(queue.isEmpty());
        assertEquals(6, queue.size());
        assertEquals(new LedgerEntry("sell", "wine", 1000, 0), queue.poll());
        assertEquals(new LedgerEntry("sell", "whisky", 100, 0), queue.poll());
        assertEquals(new LedgerEntry("buy", "wine", 500, 0), queue.poll());
        assertEquals(new LedgerEntry("buy", "wine", 500, 0), queue.poll());
        assertEquals(new LedgerEntry("sell", "whisky", 100, 80), queue.poll());
        assertEquals(new LedgerEntry("buy", "whisky", 120, 0), queue.poll());
    }

    private static List<LedgerEntry> getLedgerEntries() throws IOException {
        List<LedgerEntry> ledgerEntries;
        try (Reader reader = Files.newBufferedReader(Paths.get("src/test/resources/test-data.csv"))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            ledgerEntries = csvParser
                    .getRecords().stream()
                    .map(r -> new LedgerEntry(r.get(0), r.get(1), Integer.parseInt(r.get(2)), Integer.parseInt(r.get(3))))
                    .toList();
        }
        return ledgerEntries;
    }
}
