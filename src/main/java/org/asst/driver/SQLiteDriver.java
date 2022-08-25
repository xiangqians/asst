package org.asst.driver;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.sqlite.JDBC;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Sqlite
 * {@link DataSourceAutoConfiguration}
 * {@link JDBC#createConnection(java.lang.String, java.util.Properties)}
 *
 * @author xiangqian
 * @date 22:49 2022/08/18
 */
public class SQLiteDriver extends JDBC {

    /**
     * {@link SQLiteConnection#open(java.lang.String, java.lang.String, java.util.Properties)}
     *
     * @param url
     * @param info
     * @return
     * @throws SQLException
     */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return super.connect(url, info);
    }

}
