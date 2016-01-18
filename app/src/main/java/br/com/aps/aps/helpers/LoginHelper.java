package br.com.aps.aps.helpers;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.List;

import br.com.aps.aps.R;
import br.com.aps.aps.views.LoginActivity;

import static android.Manifest.permission.READ_CONTACTS;

public abstract class LoginHelper {

    public static final int REQUEST_READ_CONTACTS = 0;


    private static boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() > 4;
    }

    private static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static void addEmailsToAutoComplete(LoginActivity loginActivity, List<String> emails,
                                               AutoCompleteTextView emailView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                loginActivity, android.R.layout.simple_dropdown_item_1line, emails);

        emailView.setAdapter(adapter);
    }

    public static boolean mayRequestContacts(final LoginActivity loginActivity,
                                             AutoCompleteTextView emailView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (loginActivity.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (loginActivity.shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(emailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            loginActivity.requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            loginActivity.requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    public static boolean attemptLogin(final LoginActivity loginActivity,
                                       final AutoCompleteTextView emailView,
                                       final EditText phoneView) {
        emailView.setError(null);
        phoneView.setError(null);

        String email = emailView.getText().toString();
        String phone = phoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            phoneView.setError(loginActivity.getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            phoneView.setError(loginActivity.getString(R.string.error_invalid_phone));
            focusView = phoneView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailView.setError(loginActivity.getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(loginActivity.getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }

        return true;
    }

    public interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }
}
