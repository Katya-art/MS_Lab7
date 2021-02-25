package ua.kpi.comsys.iv8214.ui.graphic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GraphicViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GraphicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("On this window will be graphic");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
