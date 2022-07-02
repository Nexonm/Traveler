package we.itschool.project.traveler.presentation.fragment.my_cards;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import traveler.module.domain.entity.CardEntity;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.databinding.FragmentMyCardsBinding;
import we.itschool.project.traveler.presentation.fragment.card_list.Adapter;
import we.itschool.project.traveler.presentation.fragment.card_list.DiffCallback;
import we.itschool.project.traveler.presentation.fragment.create_new_card.CreateNewCardFragment;
import we.itschool.project.traveler.presentation.fragment.my_card_big.MyCardBigFragment;

public class MyCardsFragment extends Fragment {

    private FragmentMyCardsBinding binding;

    private ViewModelMyCards viewModel;
    private Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyCardsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initViewModel();
        initView(view);
//        addData();
    }

    private void initAdapter() {
        adapter = new Adapter(new DiffCallback());
        adapter.cardClickListener = this::startCardFragment;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ViewModelMyCards.class);

        //add cards to mutable data for better representation

        viewModel.getCardList().observe(
                getViewLifecycleOwner(),
                cards -> adapter.submitList(cards)
        );
    }

    private void initView(View view) {
        ImageButton ib_new_card = view.findViewById(R.id.ib_my_cards_create_new_card);
        ib_new_card.setOnClickListener(v -> startCreateNewCardFragment());

        RecyclerView recyclerView = view.findViewById(R.id.rv_card_list_my_cards);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE);
    }

    private void startCreateNewCardFragment() {
        Fragment fragment = CreateNewCardFragment.newInstance();

        FragmentManager fragmentManager = getParentFragmentManager();
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

    @Override
    public void onResume() {
        super.onResume();
        AsyncTask.execute(() -> {
            try {
                Thread.sleep(3500);
                adapter.submitList(new ArrayList<>(Objects.requireNonNull(viewModel.getCardList().getValue())));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void startCardFragment(CardEntity card) {
        Fragment fragment = MyCardBigFragment.newInstance(
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
