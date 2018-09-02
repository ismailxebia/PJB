package com.example.helmi.pjbdata;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.helmi.pjbdata.db.DBAdapter;
import com.example.helmi.pjbdata.db.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected BarChart chart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> barLabels;
    BarDataSet barDataSet;
    BarData barData;

    public EditText txtName,txtGraphA, txtGraphB, txtGraphC;
    public LinearLayout btnDisable,setSave;

    protected float valueA;
    protected float valueB;
    protected float valueC;

    RecyclerView rv;
    MyAdapter adapter;
    ArrayList<DataChart> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (BarChart) findViewById(R.id.chart);

        LinearLayout setChart = (LinearLayout) findViewById(R.id.setChart);
        setSave = (LinearLayout) findViewById(R.id.setSave);
        btnDisable = findViewById(R.id.setDisable);

        txtName = findViewById(R.id.txtName);
        txtGraphA = findViewById(R.id.txtGraphA);
        txtGraphB = findViewById(R.id.txtGraphB);
        txtGraphC = findViewById(R.id.txtGraphC);

        setChart.setOnClickListener(view -> {

            final String graphA = txtGraphA.getText().toString();
            final String graphB = txtGraphB.getText().toString();
            final String graphC = txtGraphC.getText().toString();

            if (graphA.isEmpty()) {
                txtGraphA.setError("Isi A");
                txtGraphA.requestFocus();
            } else if (graphB.isEmpty()) {
                txtGraphB.setError("Isi B");
                txtGraphB.requestFocus();
            } else if (graphC.isEmpty()) {
                txtGraphC.setError("Isi C");
                txtGraphC.requestFocus();
            } else {

                txtGraphA.setText(graphA);
                txtGraphB.setText(graphB);
                txtGraphC.setText(graphC);

                valueA = Float.valueOf(txtGraphA.getText().toString());
                valueB = Float.valueOf(txtGraphB.getText().toString());
                valueC = Float.valueOf(txtGraphC.getText().toString());

                barEntries = new ArrayList<BarEntry>();
                barLabels = new ArrayList<String>();

                barLabels.add("");// index 0 kosongkan saja
                barEntries.add(new BarEntry(1, valueA));
                barLabels.add("Project A");
                barEntries.add(new BarEntry(2, valueB));
                barLabels.add("Project B");
                barEntries.add(new BarEntry(3, valueC));
                barLabels.add("Project C");

                barDataSet = new BarDataSet(barEntries, "Projects");

                barData = new BarData(barDataSet);
                chart.animateY(1000);
                chart.getXAxis().setValueFormatter(
                        new IndexAxisValueFormatter(barLabels));

                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                chart.setData(barData);

                btnDisable.setVisibility(View.GONE);
                setSave.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Berhasil Menampilkan Data", Toast.LENGTH_SHORT).show();
            }

            setSave.setOnClickListener(view1 -> {
                final String saveName = txtName.getText().toString();
                final String saveA = txtGraphA.getText().toString();
                final String saveB = txtGraphB.getText().toString();
                final String saveC = txtGraphC.getText().toString();
                //
                if (saveA.isEmpty()) {
                    txtGraphA.setError("Isi A");
                    txtGraphA.requestFocus();
                } else if (saveB.isEmpty()) {
                    txtGraphB.setError("Isi B");
                    txtGraphB.requestFocus();
                } else if (saveC.isEmpty()) {
                    txtGraphC.setError("Isi C");
                    txtGraphC.requestFocus();
                } else if (saveName.isEmpty()) {
                    txtName.setError("Isi Nama Project");
                    txtName.requestFocus();
                } else {
                    save(txtName.getText().toString(),txtGraphA.getText().toString(), txtGraphB.getText().toString(), txtGraphC.getText().toString());
                }
            });
        });

        //recycler
        rv = (RecyclerView) findViewById(R.id.rv_save);

        //SET ITS PROPS
        //rv.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

// And now set it to the RecyclerView
        rv.setLayoutManager(mLayoutManager);

        //ADAPTER
        adapter = new MyAdapter(this, players);

        retrieve();
    }

    private void save(String name,String apos, String bpos, String cpos) {
        DBAdapter db = new DBAdapter(this);

        //OPEN
        db.openDB();

        //INSERT
        long result = db.add(name,apos, bpos, cpos);

        if (result > 0) {
            txtGraphA.setText("");
            txtGraphB.setText("");
            txtGraphC.setText("");
            txtName.setText("");
            setSave.setVisibility(View.GONE);
            btnDisable.setVisibility(View.VISIBLE);
        } else {
            Snackbar.make(txtGraphA, "Unable To Insert", Snackbar.LENGTH_SHORT).show();
        }

        //CLOSE
        db.close();

        //refresh
        retrieve();
    }

    private void retrieve() {
        DBAdapter db = new DBAdapter(this);

        //OPEN
        db.openDB();

        players.clear();

        //SELECT
        Cursor c = db.getAllPlayers();

        //LOOP THRU THE DATA ADDING TO ARRAYLIST
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String apos = c.getString(2);
            String bpos = c.getString(3);
            String cpos = c.getString(4);

            //CREATE PLAYER
            DataChart p = new DataChart(name,apos, bpos, cpos, id);

            //ADD TO PLAYERS
            players.add(p);
        }

        //SET ADAPTER TO RV
        if (!(players.size() < 1)) {
            rv.setAdapter(adapter);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieve();
        if (adapter.getItemCount() == 0)
        {
            Toast.makeText(getApplicationContext(), "Data Save Kosong",Toast.LENGTH_SHORT).show();
        }
    }
}
