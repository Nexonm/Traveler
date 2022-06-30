package we.itschool.project.traveler.presentation.fragment.favourites;

import android.os.AsyncTask;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import traveler.module.domain.entity.CardEntity;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.databinding.FragmentFavouritesBinding;
import we.itschool.project.traveler.presentation.fragment.card_big.CardFragment;
import we.itschool.project.traveler.presentation.fragment.card_list.Adapter;
import we.itschool.project.traveler.presentation.fragment.card_list.DiffCallback;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;

    private ViewModelFavorites viewModel;
    private Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
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
        addData();
    }

    private void initAdapter(){
        adapter = new Adapter(new DiffCallback());
        adapter.cardClickListener = this::startCardFragment;
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(ViewModelFavorites.class);

        viewModel.getCardList().observe(
                getViewLifecycleOwner(),
                cards -> adapter.submitList(cards)
        );
    }

    private void initView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.rv_favs_cards);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE
        );
    }

    private void addData(){
        AsyncTask.execute(()-> adapter.submitList(new ArrayList<>(Objects.requireNonNull(viewModel.getCardList().getValue()))));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
