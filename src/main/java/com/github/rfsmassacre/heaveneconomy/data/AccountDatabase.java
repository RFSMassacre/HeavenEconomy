package com.github.rfsmassacre.heaveneconomy;

import com.github.rfsmassacre.heavenlibrary.databases.MySQLDatabase;
import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDatabase extends MySQLDatabase<Account>
{
    public AccountDatabase(String hostName, String database, String username, String password, int port, boolean ssl)
    {
        super(hostName, database, username, password, port, ssl);

        try
        {
            String create = "CREATE TABLE IF NOT EXISTS Currencies (ID TEXT NOT NULL, Account JSON);";
            executeUpdate(create);
        }
        catch (SQLException exception)
        {
            //Do nothing.
        }
    }

    @Override
    public void add(Account account) throws SQLException
    {
        String sql = "INSERT INTO Accounts (ID, Account) VALUES (?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, account.getPlayerId().toString());
        statement.setString(2, new Gson().toJson(account));
        statement.executeUpdate();
    }

    @Override
    public void update(Account account) throws SQLException
    {
        add(account);
    }

    @Override
    public Account query(String sql, Class<Account> clazz) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        if (result.next())
        {
            String json = result.getString("Account");
            if (json == null)
            {
                return null;
            }

            return new Gson().fromJson(json, Account.class);
        }

        return null;
    }

    @Override
    public void delete(Account account) throws SQLException
    {
        String sql = "DELETE FROM Accounts WHERE ID='" + account.getPlayerId().toString() + "';";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeQuery();
    }
}
