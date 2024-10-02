package com.github.rfsmassacre.heaveneconomy;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Account
{
    @Getter
    private final UUID playerId;
    private final Map<UUID, Double> balances;

    public Account(UUID playerId)
    {
        this.playerId = playerId;
        this.balances = new HashMap<>();
    }

    public double getBalance(UUID currencyId)
    {
        return balances.get(currencyId);
    }

    public void setBalance(UUID currencyId, double balance)
    {
        balances.put(currencyId, balance);
    }

    public void addBalance(UUID currencyId, double balance)
    {
        setBalance(currencyId, getBalance(currencyId) + balance);
    }

    public void removeBalance(UUID currencyId, double balance)
    {
        setBalance(currencyId, getBalance(currencyId) - balance);
    }
}
