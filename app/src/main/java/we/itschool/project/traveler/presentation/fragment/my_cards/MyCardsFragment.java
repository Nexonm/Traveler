package we.itschool.project.traveler.presentation.fragment.my_cards;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.databinding.FragmentMyCardsBinding;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.presentation.fragment.card_big.CardFragment;
import we.itschool.project.traveler.presentation.fragment.card_list.Adapter;
import we.itschool.project.traveler.presentation.fragment.card_list.DiffCallback;
import we.itschool.project.traveler.presentation.fragment.create_new_card.CreateNewCardFragment;

public class MyCardsFragment extends Fragment {

    private FragmentMyCardsBinding binding;

    private FloatingActionButton fb_new_card;

    private CardListViewModelMyCards viewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;

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
        initAdapter();
        initViewModel();
        initView(view);
    }

    private void initAdapter() {
        adapter = new Adapter(new DiffCallback());
        adapter.cardClickListener = this::startCardFragment;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CardListViewModelMyCards.class);
        viewModel.getCardList().observe(
                getViewLifecycleOwner(),
                cards -> adapter.submitList(cards)
        );
    }

    private void initView(View view) {
        fb_new_card = view.findViewById(R.id.fb_my_cards_create_new_card);
        fb_new_card.setOnClickListener(v -> {
            startCreateNewCardFragment();
        });

        recyclerView = view.findViewById(R.id.rv_card_list_my_cards);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE);
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

    private void startCardFragment(CardEntity card) {

        Fragment fragment = CardFragment.newInstance(
                (new Gson()).toJson(card)
        );
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()
                .addToBackStack("null")
                .replace(R.id.nav_host_fragment_content_main, fragment, null)
                .commit();
    }
}
