package ua.kpi.comsys.iv8214.ui.part2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Part2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Part2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This will be the second part of Lab1.2");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
