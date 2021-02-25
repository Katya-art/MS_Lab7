package ua.kpi.comsys.iv8214.ui.drawings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DrawingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DrawingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("On this window will be drawings");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
