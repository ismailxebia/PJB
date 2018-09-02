package com.example.helmi.pjbdata;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.helmi.pjbdata.db.DBAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView img;
    public EditText txtName, txtGraphA, txtGraphB, txtGraphC;
    public LinearLayout btnDisable, setUpdate, setDelete;

    protected float valueA;
    protected float valueB;
    protected float valueC;

    protected BarChart chart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> barLabels;
    BarDataSet barDataSet;
    BarData barData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        chart = (BarChart) findViewById(R.id.chart);

        //RECEIVE DATA FROM MAIN ACTIVITY
        Intent i = getIntent();

        final String name = i.getExtras().getString("NAME");
        final String apos = i.getExtras().getString("AVALUE");
        final String bpos = i.getExtras().getString("BVALUE");
        final String cpos = i.getExtras().getString("CVALUE");
        final int id = i.getExtras().getInt("ID");

        setUpdate = (LinearLayout) findViewById(R.id.setUpdate);
        btnDisable = findViewById(R.id.setDisable);
        setDelete = findViewById(R.id.setDelete);

        txtName = findViewById(R.id.txtName);
        txtGraphA = findViewById(R.id.txtGraphA);
        txtGraphB = findViewById(R.id.txtGraphB);
        txtGraphC = findViewById(R.id.txtGraphC);

        //ASSIGN DATA TO THOSE VIEWS
        txtName.setText(name);
        txtGraphA.setText(apos);
        txtGraphB.setText(bpos);
        txtGraphC.setText(cpos);

        //update
        setUpdate.setOnClickListener(v -> {
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
                update(id, txtName.getText().toString(), txtGraphA.getText().toString(), txtGraphB.getText().toString(), txtGraphC.getText().toString());
            }
        });

        //DELETE
        //update
        setDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id);
            }
        });


    }

    private void update(int id, String name, String apos, String bpos, String cpos) {
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        long result = db.UPDATE(id, name, apos, bpos, cpos);

        if (result > 0) {
            txtName.setText(name);
            txtGraphA.setText(apos);
            txtGraphB.setText(bpos);
            txtGraphC.setText(cpos);

            Snackbar.make(txtName, "Updated Sucesfully", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(txtName, "Unable to Update", Snackbar.LENGTH_SHORT).show();
        }

        db.close();
    }

    //DELETE
    private void delete(int id) {
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        long result = db.Delete(id);

        if (result > 0) {
            this.finish();
        } else {
            Snackbar.make(txtName, "Unable to Update", Snackbar.LENGTH_SHORT).show();
        }

        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setChart();
    }

    private void setChart() {
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
    }

}
