/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package websec.models;

import burp.api.montoya.http.handler.HttpResponseReceived;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LoggerModel extends AbstractTableModel
{
    public class Row {
        public String dateTime;
        public String source;
        public String message;
    }

    private final List<Row> log;

    public LoggerModel()
    {
        this.log = new ArrayList<>();
    }

    @Override
    public synchronized int getRowCount()
    {
        return log.size();
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public String getColumnName(int column)
    {
        return switch (column)
        {
            case 0 -> "Date";
            case 1 -> "Source";
            case 2 -> "Messege";
            default -> "";
        };
    }

    @Override
    public synchronized Object getValueAt(int rowIndex, int columnIndex)
    {
        Row row = log.get(rowIndex);

        return switch (columnIndex)
        {
            case 0 -> row.dateTime;
            case 1 -> row.source;
            case 2 -> row.message;
            default -> "";
        };
    }

    public synchronized void add(String dateTime, String source, String message)
    {
        int index = log.size();
        Row row = new Row();
        row.dateTime = dateTime;
        row.source = source;
        row.message = message;
        log.add(row);
        fireTableRowsInserted(index, index);
    }

    public synchronized Row get(int rowIndex)
    {
        return log.get(rowIndex);
    }
}
