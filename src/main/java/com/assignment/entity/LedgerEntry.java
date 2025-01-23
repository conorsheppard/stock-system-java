package com.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LedgerEntry {
    private String action;
    private String product;
    private int quantity;
    private int remainingQuantity;

    public enum Action {
        SELL,
        BUY;
    }

    public LedgerEntry(LedgerEntry ledgerEntry) {
        this.setAction(ledgerEntry.getAction());
        this.setProduct(ledgerEntry.getProduct());
        this.setQuantity(ledgerEntry.getQuantity());
        this.setRemainingQuantity(ledgerEntry.getRemainingQuantity());
    }

    public boolean isSale() {
        return getAction().equals(Action.SELL.toString().toLowerCase());
    }

    public boolean isPurchase() {
        return getAction().equals(Action.BUY.toString().toLowerCase());
    }

    public LedgerEntry setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String toString() {
        return "system:< " +
                this.action + " " +
                this.product + " " +
                this.quantity + " " +
                (this.remainingQuantity == 0 ? "closed" : "remaining:" + this.remainingQuantity);
    }
}
