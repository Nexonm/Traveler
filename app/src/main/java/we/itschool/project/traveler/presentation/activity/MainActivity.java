package we.itschool.project.traveler.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.presentation.fragment.card_list.CardList;

public class MainActivity extends AppCompatActivity {

    FragmentContainerView fragmentCardList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        startFragmentCardList();
    }


    private void initFragments() {
        fragmentCardList = findViewById(R.id.container_card_list);
    }

    private void startFragmentCardList() {
        Fragment fragment = new CardList();
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