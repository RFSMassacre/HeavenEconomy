package com.github.rfsmassacre.heaveneconomy;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Currency
{
    private final UUID currencyId;
    private String name;
    private String symbol;

    public Currency(UUID currencyId, String name, String symbol)
    {
        this.currencyId = currencyId;
        this.name = name;
        this.symbol = symbol;
    }

    public Currency(String name, String symbol)
    {
        this(UUID.randomUUID(), name, symbol);
    }
}
