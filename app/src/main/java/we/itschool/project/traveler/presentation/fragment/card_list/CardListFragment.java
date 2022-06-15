package we.itschool.project.traveler.presentation.fragment.card_list;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import we.itschool.project.traveler.databinding.FragmentMainBinding;
import we.itschool.project.traveler.presentation.fragment.card_big.CardFragment;

public class CardListFragment extends Fragment {

    private static final String ARGUMENT_IS_ONE_PANE_MODE = "is onePaneMode";

    private CardListViewModel viewModel;

    private EditText et_search_input;

    private Adapter adapter;

    private boolean goingUsual;
    private boolean goingSearch;
    private boolean reset;

    View root;

    public static CardListFragment newInstance() {
        return new CardListFragment();
    }

    private void parseParams() {
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_IS_ONE_PANE_MODE))
            throw new RuntimeException("Argument 'is onePane' is absent");
        boolean isOnePane = args.getBoolean(ARGUMENT_IS_ONE_PANE_MODE);
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
        we.itschool.project.traveler.databinding.FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);
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
        goingUsual = true;
        reset = false;
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
        RecyclerView recyclerView = view.findViewById(R.id.rv_card_list);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE);

        et_search_input = view.findViewById(R.id.et_main_search);
        Button bt_search = view.findViewById(R.id.bt_main_search);
        bt_search.setOnClickListener(v -> {
            goingUsual = false;
            searchData();
        });
    }

    private void searchData() {
        if (et_search_input.getText().toString().length() > 1) {

            AsyncTask.execute(() -> {
                goingSearch = true;
                String str = et_search_input.getText().toString();
                if (str.charAt(str.length() - 1) == ' ')
                    viewModel.searchCards(str.substring(0, str.length() - 2));
                else
                    viewModel.searchCards(str);
                try {
                    int count;
                    while (goingSearch) {
                        Thread.sleep(3200);
                        adapter.submitList(new ArrayList<>(Objects.requireNonNull(viewModel.getCardList().getValue())));
                        count = adapter.getItemCount();
                        //just to stop loop and async
                        if (viewModel.getCardList().getValue().size()>5 ||
                        viewModel.getCardList().getValue().size()==0) break;
                        //in case after new update there is same num of items
                        if (count!= 0 && adapter.getItemCount()==count) break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                goingSearch = false;
            });
        } else {
            goingUsual = true;
            reset = true;
            addData();
        }

    }

    //TODO сделать загрузку данных другим способом, в зависимости от прокрутки пользователем странницы
    private void addData() {
        AsyncTask.execute(() -> {

            int num = 0;
            while (num < 20 && goingUsual)
                try {
//                        Log.v("OkHttpClient nik", "запрос отправляю " + num+", going="+going);
                    viewModel.addNewCard();
                    reset = false;
                    num++;
//                        Log.v("OkHttpClient nik", "ухожу спать" + num+", going="+going);
                    Thread.sleep(1500);
//                        Log.v("OkHttpClient nik", "проснулся" + num+", going="+going);
                    //write submit list to add new list made from main list with cards
                    adapter.submitList(new ArrayList<>(Objects.requireNonNull(viewModel.getCardList().getValue())));
//                        adapter.onCurrentListChanged(adapter.getCurrentList(), viewModel.getCardList().getValue());
                    try {
                        num = viewModel.getCardList().getValue().size();
//                            Log.v("OkHttpClient nik", "получил карту num=" + num +", going="+going);
                    } catch (NullPointerException ignored) {
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.v("OkHttpClient nik", "onPAUSE in ListFragment");
        goingUsual = false;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.v("OkHttpClient nik", "onRESUME in ListFragment");
        goingUsual = true;
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
