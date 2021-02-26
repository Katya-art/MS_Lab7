package ua.kpi.comsys.iv8214.ui.diagram;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ua.kpi.comsys.iv8214.R;
import ua.kpi.comsys.iv8214.ui.home.HomeViewModel;

public class DiagramFragment extends Fragment {

    private DiagramViewModel diagramViewModel;
    PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diagramViewModel =
                new ViewModelProvider(this).get(DiagramViewModel.class);
        View root = inflater.inflate(R.layout.fragment_diagram, container, false);

        pieChart = root.findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(40));
        yValues.add(new PieEntry(30));
        yValues.add(new PieEntry(30));
        PieDataSet dataSet = new PieDataSet(yValues, "Colors");
        //dataSet.setSliceSpace(3f);
        //dataSet.setSelectionShift(5f);
        //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setColors(Color.BLACK, 0xFFFF9800, Color.GREEN);
        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.TRANSPARENT);
        pieChart.setData(data);

        return root;
    }
}
