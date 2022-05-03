package we.itschool.project.traveler.presentation.fragment.my_cards;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.databinding.FragmentMyCardsBinding;
import we.itschool.project.traveler.presentation.fragment.create_new_card.CreateNewCardFragment;

public class MyCardsFragment extends Fragment {

    private FragmentMyCardsBinding binding;

    private FloatingActionButton fb_new_card;

    public static MyCardsFragment newInstance(){
        return new MyCardsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyCardsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        fb_new_card = view.findViewById(R.id.fb_my_cards_create_new_card);
        fb_new_card.setOnClickListener(v -> {
            startCreateNewCardFragment();
        });
    }

    private void startCreateNewCardFragment(){
        Fragment fragment = CreateNewCardFragment.newInstance();

        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()
                .addToBackStack("null")
                .replace(R.id.nav_host_fragment_content_main, fragment, null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
