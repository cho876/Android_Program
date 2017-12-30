package com.example.skcho.smartcarrier;

/**
 * Created by skCho on 2017-12-28.
 */


import java.sql.SQLException;

public interface DetailInterface {
    public void backToList();

    public void saveToList(Memo memo) throws SQLException;

    public void deleteFromList(Memo memo) throws SQLException;
}
