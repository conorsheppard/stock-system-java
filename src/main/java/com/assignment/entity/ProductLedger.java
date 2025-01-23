package com.assignment.entity;

import java.util.Collection;

public class ProductLedger implements Ledger {
    private Collection<LedgerEntry> ledger;

    public ProductLedger(Collection<LedgerEntry> c) {
        this.ledger = c;
    }

    public void add(LedgerEntry entry) {
        if (entry.isSale()) ledger.add(entry);
        if (entry.isPurchase()) buy(entry);
    }

    private void buy(LedgerEntry entry) {
        var entryCopy = new LedgerEntry(entry);
        ledger.iterator().forEachRemaining(l -> buyBackStock(l, entryCopy));
        if (entryCopy.getQuantity() < entry.getQuantity())
            ledger.add(entry.setQuantity(entry.getQuantity() - entryCopy.getQuantity()));
    }

    private void buyBackStock(LedgerEntry entry, LedgerEntry ledgerEntryBuy) {
        if (entry.isSale()
                && entry.getProduct().equals(ledgerEntryBuy.getProduct())
                && entry.getRemainingQuantity() > 0
                && ledgerEntryBuy.getQuantity() > 0) {
            if (entry.getRemainingQuantity() < ledgerEntryBuy.getQuantity()) {
                ledgerEntryBuy.setQuantity(ledgerEntryBuy.getQuantity() - entry.getRemainingQuantity());
                ledgerEntryBuy.setQuantity(ledgerEntryBuy.getQuantity());
                entry.setRemainingQuantity(0);
            } else if (entry.getRemainingQuantity() > ledgerEntryBuy.getQuantity()) {
                entry.setRemainingQuantity(entry.getRemainingQuantity() - ledgerEntryBuy.getQuantity());
                ledgerEntryBuy.setQuantity(0);
            } else {
                entry.setRemainingQuantity(0);
                ledgerEntryBuy.setQuantity(0);
            }
        }
    }

    @Override
    public void printLedger() {
        ledger.forEach(System.out::println);
    }
}
