package ua.kpi.comsys.iv8214.ui.part2;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

public class Part2Fragment extends Fragment {

    private Part2ViewModel part2ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        part2ViewModel =
                new ViewModelProvider(this).get(Part2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_part2, container, false);
        final TextView textView = root.findViewById(R.id.text_part2);
        part2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
        });
        return root;
    }
}
