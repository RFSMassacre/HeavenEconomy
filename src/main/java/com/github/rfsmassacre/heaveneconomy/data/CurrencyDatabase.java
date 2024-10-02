package com.github.rfsmassacre.heaveneconomy;

import com.github.rfsmassacre.heavenlibrary.databases.MySQLDatabase;
import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyDatabase extends MySQLDatabase<Currency>
{
    public CurrencyDatabase(String hostName, String database, String username, String password, int port, boolean ssl)
    {
        super(hostName, database, username, password, port, ssl);

        try
        {
            String create = "CREATE TABLE IF NOT EXISTS Currencies (ID TEXT NOT NULL, Currency JSON);";
            executeUpdate(create);
        }
        catch (SQLException exception)
        {
            //Do nothing.
        }
    }

    @Override
    public void add(Currency currency) throws SQLException
    {
        String sql = "INSERT INTO Currencies (ID, Currency) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, currency.getCurrencyId().toString());
        statement.setString(2, new Gson().toJson(currency));
        statement.executeUpdate();
    }

    @Override
    public void update(Currency currency) throws SQLException
    {
        add(currency);
    }

    @Override
    public Currency query(String sql, Class<Currency> clazz) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        if (result.next())
        {
            String json = result.getString("Currency");
            if (json == null)
            {
                return null;
            }

            return new Gson().fromJson(json, Currency.class);
        }

        return null;
    }

    @Override
    public void delete(Currency currency) throws SQLException
    {
        String sql = "DELETE FROM Currencies WHERE ID=" + currency.getCurrencyId().toString() + "'";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }
}
