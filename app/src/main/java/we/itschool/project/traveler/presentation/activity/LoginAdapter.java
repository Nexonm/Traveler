package we.itschool.project.traveler.presentation.activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import we.itschool.project.traveler.presentation.fragment.login.LoginFragment;
import we.itschool.project.traveler.presentation.fragment.registration.RegistrationFragment;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs = 2;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    public LoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegistrationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}