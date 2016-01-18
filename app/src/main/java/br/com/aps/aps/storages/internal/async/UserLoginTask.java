package br.com.aps.aps.storages.internal.async;

import android.os.AsyncTask;

import java.io.IOException;

import br.com.aps.aps.models.User;
import br.com.aps.aps.storages.external.ExternalService;
import br.com.aps.aps.storages.internal.listener.UserLoginListener;
import br.com.aps.aps.storages.sqlite.user.UsersRepository;

public class UserLoginTask extends AsyncTask<User, Void, String> {

    private static final String TAG = "ApS8s";

    private UserLoginListener mUserLoginListener;

    public UserLoginTask(UserLoginListener userLoginListener) {
        mUserLoginListener = userLoginListener;
    }

    @Override
    protected String doInBackground(User... params) {
        String codeValidation;
        try {
            final User user = ExternalService.getInstance().postUser(params[0]);
            UsersRepository.getInstance().save(user);
            codeValidation = TAG;
        } catch (IOException e) {
            codeValidation = e.getMessage();
        }

        return codeValidation;
    }

    @Override
    protected void onPostExecute(String codeValidation) {
        if (mUserLoginListener != null) {
            mUserLoginListener.onPostExecuteUserLogin(codeValidation);
        }
    }

}