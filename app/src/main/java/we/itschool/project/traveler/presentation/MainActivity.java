package we.itschool.project.traveler.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import we.itschool.project.traveler.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView reV;
    RecyclerView.LayoutManager lmg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reV =  findViewById(R.id.recycleView);
        reV.setHasFixedSize(false);

        lmg = new RecyclerView.LayoutManager() {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return null;
            }
        };

    }
}