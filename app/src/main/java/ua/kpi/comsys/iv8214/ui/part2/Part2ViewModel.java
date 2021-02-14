package ua.kpi.comsys.iv8214.ui.part2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.ParseException;

public class Part2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Part2ViewModel() throws ParseException {
        TimeKK defaultTime = new TimeKK();
        TimeKK createdTime = new TimeKK(18, 30, 21);
        TimeKK currentTime = new TimeKK(java.time.LocalTime.now());
        mText = new MutableLiveData<>();
        mText.setValue("Default time:\n" + defaultTime.date() +
                       "\nCreated time:\n" + createdTime.date() +
                       "\nCurrent time:\n" + currentTime.date() +
                       "\nAdd created time to current:\n" + currentTime.addToDefault(createdTime).date() +
                       "\nSubtract created time from current:\n" + currentTime.subtractToDefault(createdTime).date() +
                       "\nAdd 2 times(created, current):\n" + TimeKK.add(createdTime, currentTime).date() +
                       "\nSubtract 2 times(current, created):\n" + TimeKK.subtract(currentTime, createdTime).date());
    }

    public LiveData<String> getText() {
        return mText;
    }
}
