package br.com.aps.aps.storages.sqlite.user;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.aps.aps.models.User;

public class UserContract {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    public static final String[] COLUNS = {ID, EMAIL, PHONE};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(EMAIL + " TEXT, ");
        sql.append(PHONE + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(User user) {
        ContentValues content = new ContentValues();
        content.put(ID, user.getId());
        content.put(EMAIL, user.getEmail());
        content.put(PHONE, user.getPhone());
        return content;
    }

    public static User bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            User user = new User();
            user.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
            return user;
        }
        return null;
    }

    public static List<User> bindList(Cursor cursor) {
        final List<User> users = new ArrayList<User>();
        while (cursor.moveToNext()) {
            users.add(bind(cursor));
        }
        return users;
    }

}
