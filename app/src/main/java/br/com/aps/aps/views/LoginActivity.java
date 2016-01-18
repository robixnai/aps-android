package br.com.aps.aps.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.aps.aps.R;
import br.com.aps.aps.helpers.LoginHelper;
import br.com.aps.aps.models.User;
import br.com.aps.aps.schedules.LocationsAlarmReceiver;
import br.com.aps.aps.storages.internal.async.UserLoginTask;
import br.com.aps.aps.storages.internal.listener.UserLoginListener;
import br.com.aps.aps.storages.sqlite.user.UsersRepository;
import br.com.aps.aps.utils.AppUtil;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "ApS8s";

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPhoneView;
    private View mProgressView, mLoginFormView;
    private Button mEmailSignInButton;

    private LocationsAlarmReceiver mLocationsAlarmReceiver = new LocationsAlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindElements();
        mLocationsAlarmReceiver.setAlarm(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (UsersRepository.getInstance().getAll().size() > 0) {
            Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            startActivity(intent);
        }
    }

    private void bindElements() {
        mEmailView = AppUtil.get(findViewById(R.id.email));
        populateAutoComplete();

        mPhoneView = AppUtil.get(findViewById(R.id.phone));

        mEmailSignInButton = AppUtil.get(findViewById(R.id.email_sign_in_button));
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean attemptLogin = LoginHelper.attemptLogin(LoginActivity.this, mEmailView, mPhoneView);
                if (attemptLogin) {
                    loginExecute();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void loginExecute() {
        if (mAuthTask != null) {
            return;
        }

        showProgress(true);

        final User user = new User();
        user.setEmail(mEmailView.getText().toString());
        user.setPhone(mPhoneView.getText().toString());
        mAuthTask = new UserLoginTask(new UserLoginListener() {
            @Override
            public void onPostExecuteUserLogin(String codeValidation) {
                mAuthTask = null;
                showProgress(false);

                if(TAG.equals(codeValidation)) {
                    // Enviar notification com o codeValidation e habilitar o compo para o codigo
                    // e redirecionar para a intent
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_server) + codeValidation, Toast.LENGTH_LONG).show();
                }
            }
        });
        mAuthTask.execute(user);
    }

    private void populateAutoComplete() {
        final boolean mayRequestContacts = LoginHelper.mayRequestContacts(LoginActivity.this, mEmailView);
        if (!mayRequestContacts) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        final int requestReadContacts = LoginHelper.REQUEST_READ_CONTACTS;
        if (requestCode == requestReadContacts) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), LoginHelper.ProfileQuery.PROJECTION,

                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(LoginHelper.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        LoginHelper.addEmailsToAutoComplete(LoginActivity.this, emails, mEmailView);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

}

