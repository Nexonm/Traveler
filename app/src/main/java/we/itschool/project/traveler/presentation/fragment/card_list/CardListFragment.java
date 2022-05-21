package we.itschool.project.traveler.presentation.fragment.card_list;

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

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.databinding.FragmentMainBinding;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.presentation.fragment.card_big.CardFragment;

public class CardListFragment extends Fragment {

    private static final String ARGUMENT_IS_ONE_PANE_MODE = "is onePaneMode";
    private boolean isOnePane;

    private CardListViewModel viewModel;

    private RecyclerView recyclerView;

    private Adapter adapter;

    private FragmentMainBinding binding;

    private boolean going;

    View root;

    public static CardListFragment newInstance() {
        return new CardListFragment();
    }

    private void parseParams() {
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_IS_ONE_PANE_MODE))
            throw new RuntimeException("Argument 'is onePane' is absent");
        isOnePane = args.getBoolean(ARGUMENT_IS_ONE_PANE_MODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //parseParams();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        return root;
//        return inflater.inflate(
//                R.layout.fragment_main,
//                container,
//                false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initViewModel();
        initView(view);
        going = true;
        addData();
    }

    private void initAdapter() {
        adapter = new Adapter(new DiffCallback());
        adapter.cardClickListener = this::startCardFragment;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CardListViewModel.class);
        viewModel.getCardList().observe(
                getViewLifecycleOwner(),
                cards -> adapter.submitList(cards)
        );
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_card_list);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE);
    }

    //TODO сделать загрузку данных другим способом, в зависимости от прокрутки пользователем странницы
    private void addData() {
        AsyncTask.execute(()->{

                int num = 0;
                while (num < 5 && going)
                    try {
                        Log.v("OkHttpClient nik", "запрос отправляю " + num);
                        viewModel.addNewCard();
                        num++;
                        Thread.sleep(2500);
                        //write submit list to add new list made from main list with cards
                        adapter.submitList(new ArrayList<>(viewModel.getCardList().getValue()));
//                        adapter.onCurrentListChanged(adapter.getCurrentList(), viewModel.getCardList().getValue());
                        try{
                            num = viewModel.getCardList().getValue().size();
                        }catch (NullPointerException ignored){}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        going = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        going = false;
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
