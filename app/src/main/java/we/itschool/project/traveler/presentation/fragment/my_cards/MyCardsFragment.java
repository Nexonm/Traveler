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

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.databinding.FragmentMyCardsBinding;
import we.itschool.project.traveler.presentation.fragment.card_list.Adapter;
import we.itschool.project.traveler.presentation.fragment.card_list.DiffCallback;
import we.itschool.project.traveler.presentation.fragment.create_new_card.CreateNewCardFragment;

public class MyCardsFragment extends Fragment {

    private FragmentMyCardsBinding binding;

    private ImageButton ib_new_card;

    private ViewModelMyCards viewModel;
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
//        for (CardEntity card : AppStart.getUser().getUserInfo().getUserCards())
//            viewModel.addOne(card);

        viewModel.getCardList().observe(
                getViewLifecycleOwner(),
                cards -> adapter.submitList(cards)
        );
    }

    private void initView(View view) {
        ib_new_card = view.findViewById(R.id.ib_my_cards_create_new_card);
        ib_new_card.setOnClickListener(v -> startCreateNewCardFragment());

        recyclerView = view.findViewById(R.id.rv_card_list_my_cards);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE);
    }

    private void startCreateNewCardFragment(){
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

//    private void addData(){
//        AppStart.getUser().getUserInfo().getUserCards();
//    }

    @Override
    public void onResume() {
        super.onResume();
        AsyncTask.execute(() ->{
        try{
            Thread.sleep(3500);
            adapter.submitList(new ArrayList<>(viewModel.getCardList().getValue()));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        });
    }

    private void startCardFragment(CardEntity card) {

//        Log.e("CARD FRAGMENT", "TRYING TO GO TO BIG CARD");
//        Fragment fragment = CardFragment.newInstance(
//                (new Gson()).toJson(card)
//        );
//        FragmentManager fragmentManager = getParentFragmentManager();
//        fragmentManager.popBackStack();
//        fragmentManager
//                .beginTransaction()
//                .addToBackStack("null")
//                .replace(R.id.nav_host_fragment_content_main, fragment, null)
//                .commit();
    }
}
