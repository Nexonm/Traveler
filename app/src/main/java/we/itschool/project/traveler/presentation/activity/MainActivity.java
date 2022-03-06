package we.itschool.project.traveler.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.presentation.fragment.card_list.CardList;

public class MainActivity extends AppCompatActivity {

    FragmentContainerView fragmentCardList = null;
    FragmentContainerView fragmentBigCard = null;
    private boolean isOnePane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        startFragmentCardList();
    }


    private void initFragments() {
        fragmentBigCard = findViewById(R.id.container_card_big_view);
        fragmentCardList = findViewById(R.id.container_card_list);
        isOnePane = isOnePaneMode();
    }

    private boolean isOnePaneMode() {
        return fragmentBigCard == null;
    }

    private void startFragmentCardList() {
        Fragment fragment = CardList.newInstance(isOnePane);
        getSupportFragmentManager()
                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .add(
                        R.id.container_card_list,
                        fragment
                )
                .commit();
    }

}