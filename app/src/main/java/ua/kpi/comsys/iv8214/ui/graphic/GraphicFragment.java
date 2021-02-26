package ua.kpi.comsys.iv8214.ui.graphic;

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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import ua.kpi.comsys.iv8214.R;

public class GraphicFragment extends Fragment {

    private GraphicViewModel graphicViewModel;
    GraphView graph;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphicViewModel =
                new ViewModelProvider(this).get(GraphicViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graphic, container, false);

        graph = root.findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);
        graph.addSeries(graphicViewModel.getSerires());
        graph.getViewport().setMinX(-6);
        graph.getViewport().setMaxX(6);
        graph.getViewport().setMinY(-6);
        graph.getViewport().setMaxY(6);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        return root;
    }
}
