package ua.kpi.comsys.iv8214.ui.part1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Part1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Part1ViewModel() {
        mText = new MutableLiveData<>();
        Part1 part1 = new Part1();
        mText.setValue("Task1\n" + part1.studentsGroups().toString() +
                       "\nTask2\n" + part1.studentsPoint() +
                       "\nTask3\n" + part1.sumPoints() +
                       "\nTask4\n" + part1.groupAvg() +
                       "\nTask5\n" + part1.passedPerGroup());
    }

    public LiveData<String> getText() {
        return mText;
    }
}