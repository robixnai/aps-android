package br.com.aps.aps.storages.sqlite.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.aps.aps.models.User;
import br.com.aps.aps.storages.sqlite.DatabaseHelper;
import br.com.aps.aps.utils.AppUtil;

public class UsersRepository {

    private static class Singleton {
        public static final UsersRepository INSTANCE = new UsersRepository();
    }

    private UsersRepository() {
        super();
    }

    public static UsersRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(UserContract.TABLE, null, UserContract.getContentValues(user));
        db.close();
        helper.close();
    }

    public List<User> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUNS, null, null, null, null, UserContract.EMAIL);
        List<User> users = UserContract.bindList(cursor);
        db.close();
        helper.close();
        return users;
    }

    public User getUser(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = UserContract.EMAIL + " = ? AND " + UserContract.PHONE + " = ?";
        String[] selectionArgs = {user.getEmail(), user.getPhone()};
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUNS, selection, selectionArgs, null, null, UserContract.EMAIL);
        User hasUser = UserContract.bind(cursor);
        db.close();
        helper.close();
        return hasUser;
    }

}
