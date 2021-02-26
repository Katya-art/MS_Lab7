package ua.kpi.comsys.iv8214.ui.graphic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphicViewModel extends ViewModel {

    private LineGraphSeries<DataPoint> serires;

    public GraphicViewModel() {
        double x, y;
        x = -6;
        serires = new LineGraphSeries<>();
        int numPoints = 100;
        for (int i = 0; i < numPoints; i++) {
            y = Math.exp(x);
            serires.appendData(new DataPoint(x, y), true, 100);
            x = x + 0.1;
        }
    }

    public LineGraphSeries<DataPoint> getSerires() {
        return serires;
    }
}
