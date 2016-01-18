package br.com.aps.aps.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.aps.aps.R;
import br.com.aps.aps.utils.AppUtil;

public class SettingsFragment extends Fragment {

    private static View mView;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.nav_settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = AppUtil.get(mView.getParent());
            if (parent != null)
                parent.removeView(mView);
        }

        try {
            mView = inflater.inflate(R.layout.fragment_settings, container, false);
        } catch (InflateException e) {}

        return mView;
    }

}
