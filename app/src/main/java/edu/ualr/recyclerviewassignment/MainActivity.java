package edu.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import edu.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import edu.ualr.recyclerviewassignment.data.DataGenerator;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.SectionHeader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        // TODO. Create and initialize the RecyclerView instance here
        List<Item> items = DataGenerator.getDevicesDataset(10);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        AdapterListBasic mAdapter = new AdapterListBasic(this, items);
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Item item, int position) {
                if (item instanceof Device)
                {
                    Device device = (Device) item;
                    Log.d("TAG", String.format("The user has tapped on %s at position %s", device.getName(), position));
                }
                else
                {
                    SectionHeader header = (SectionHeader) item;
                    Log.d("TAG", String.format("The user has tapped on %s at position %s", header.getLabel(), position));
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
