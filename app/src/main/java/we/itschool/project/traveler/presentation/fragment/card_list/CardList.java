package we.itschool.project.traveler.presentation.fragment.card_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import we.itschool.project.traveler.R;

public class CardList extends Fragment {

    private static final String ARGUMENT_IS_ONE_PANE_MODE = "is onePaneMode";
    private boolean isOnePane;

    private CardListViewModel viewModel;

    private RecyclerView recyclerView;

    private Adapter adapter;

    public static CardList newInstance(boolean isOnePane){
        Bundle args = new Bundle();
        args.putBoolean(ARGUMENT_IS_ONE_PANE_MODE, isOnePane);
        CardList fragment = new CardList();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseParams(){
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_IS_ONE_PANE_MODE))
            throw new RuntimeException("Argument 'is onePane' is absent");
        isOnePane = args.getBoolean(ARGUMENT_IS_ONE_PANE_MODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_card_list,
                container,
                false);
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
    }

    private void initAdapter() {
        adapter = new Adapter(new DiffCallback());
        adapter.cardClickListener = new Adapter.OnCardClickListener() {
            @Override
            public void onCardClick(int peopleId) {
                //TODO start a new fragment AboutCard
            }
        };
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CardListViewModel.class);
        viewModel.getCardList().observe(getViewLifecycleOwner(), cards -> {
            adapter.submitList(cards);
        });
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.rv_card_list);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                Adapter.VIEW_TYPE_CARD_VISITOR, Adapter.MAX_POOL_SIZE);
    }
}
