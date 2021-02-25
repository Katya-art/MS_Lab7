package ua.kpi.comsys.iv8214.ui.graphic;

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

import ua.kpi.comsys.iv8214.R;

public class GraphicFragment extends Fragment {

    private GraphicViewModel graphicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphicViewModel =
                new ViewModelProvider(this).get(GraphicViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graphic, container, false);
        final TextView textView = root.findViewById(R.id.text_graphic);
        graphicViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
