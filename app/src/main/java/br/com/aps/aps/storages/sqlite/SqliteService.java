package br.com.aps.aps.storages.sqlite;

public class SqliteService {

    private SqliteService() {
        super();
    }

    public static SqliteService getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SqliteService INSTANCE = new SqliteService();
    }

}
