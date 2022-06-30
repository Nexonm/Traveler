package we.itschool.project.traveler.presentation.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import we.itschool.project.traveler.presentation.fragment.login.LoginFragment;
import we.itschool.project.traveler.presentation.fragment.registration.RegistrationFragment;

public class LoginAdapter extends FragmentPagerAdapter {

    int totalTabs;

    public LoginAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LoginFragment();
        } else {
            return new RegistrationFragment();
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}