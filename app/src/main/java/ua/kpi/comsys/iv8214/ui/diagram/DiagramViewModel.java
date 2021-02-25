package ua.kpi.comsys.iv8214.ui.diagram;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiagramViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiagramViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("On this window will be diagram");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
