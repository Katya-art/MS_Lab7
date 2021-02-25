package ua.kpi.comsys.iv8214.ui.drawings;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ua.kpi.comsys.iv8214.R;
import ua.kpi.comsys.iv8214.ui.diagram.DiagramViewModel;

public class DrawingsFragment extends Fragment {

    private DrawingsViewModel drawingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        drawingsViewModel =
                new ViewModelProvider(this).get(DrawingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_drawings, container, false);
        BottomNavigationView bottomNavigationView = root.findViewById(R.id.painting_nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_graphic, R.id.navigation_diagram)
                .build();
        NavController navController = Navigation.findNavController(root.findViewById(R.id.painting_nav_host_fragment));
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        return root;
    }
}
