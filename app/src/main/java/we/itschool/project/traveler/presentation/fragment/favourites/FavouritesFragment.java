package we.itschool.project.traveler.presentation.fragment.favourites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentFavouritesBinding;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.presentation.fragment.card_big.CardFragment;
import we.itschool.project.traveler.presentation.fragment.card_list.Adapter;
import we.itschool.project.traveler.presentation.fragment.card_list.DiffCallback;

public class FavouritesFragment extends Fragment {

    private FragmentFavouritesBinding binding;

    private ViewModelFavorites viewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;

    private boolean going = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
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
        recyclerView = view.findViewById(R.id.rv_favs_cards);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE
        );
    }

    private void addData(){
        AsyncTask.execute(()->{

            try {
                ArrayList<Long> list = new ArrayList<>(AppStart.getUser().getUserInfo().getUserFavoritesCards());
                int size = list.size();
                int start = 0;
                while (start < size && going) {
                    viewModel.addCard(list.get(start));
                    if (start % 2 == 0)
                        adapter.submitList(new ArrayList<>(Objects.requireNonNull(viewModel.getCardList().getValue())));
                    Thread.sleep(100);
                    start++;
                }
                adapter.submitList(new ArrayList<>(Objects.requireNonNull(viewModel.getCardList().getValue())));
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.v("OkHttpClient nik", "onPAUSE in ListFragment");
        going = false;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.v("OkHttpClient nik", "onRESUME in ListFragment");
        going = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startCardFragment(CardEntity card) {

        Log.e("CARD FRAGMENT", "TRYING TO GO TO BIG CARD");
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
