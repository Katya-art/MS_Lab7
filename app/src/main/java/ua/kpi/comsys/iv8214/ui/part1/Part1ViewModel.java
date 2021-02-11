package ua.kpi.comsys.iv8214.ui.part1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Part1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Part1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This will be the first part of Lab1.2");
    }

    public LiveData<String> getText() {
        return mText;
    }
}