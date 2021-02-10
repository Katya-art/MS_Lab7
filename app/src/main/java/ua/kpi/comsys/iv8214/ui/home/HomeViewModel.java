package ua.kpi.comsys.iv8214.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Кравченко Катерина\n" +
                       "Група ІВ-82\n" +
                       "ЗК ІВ-8214\n");
    }

    public LiveData<String> getText() {
        return mText;
    }
}