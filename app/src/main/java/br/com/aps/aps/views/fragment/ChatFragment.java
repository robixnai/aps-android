package br.com.aps.aps.views.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.aps.aps.R;
import br.com.aps.aps.views.adapter.PagerFragmentAdapter;
import br.com.aps.aps.utils.AppUtil;

public class ChatFragment extends Fragment {

    private View mView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.nav_chat);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat, container, false);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        bindViewPager();
    }

    private void bindViewPager() {
        mViewPager = AppUtil.get(mView.findViewById(R.id.viewpager));
        setupViewPager(mViewPager);

        mTabLayout = AppUtil.get(mView.findViewById(R.id.tabs));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager upViewPager) {
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new ConversationsFragment(), getString(R.string.str_conversations));
        adapter.addFragment(new ContactsFragment(), getString(R.string.str_contacts));
        mViewPager.setAdapter(adapter);
    }
}
