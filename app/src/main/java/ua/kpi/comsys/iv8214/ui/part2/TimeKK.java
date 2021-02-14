package ua.kpi.comsys.iv8214.ui.part2;

import java.time.LocalTime;

public class TimeKK {
    private int hours;
    private int minutes;
    private int seconds;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void create(int hours, int minutes, int seconds) {
        if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59 && seconds >= 0 &&
                seconds <= 59) {
            setHours(hours);
            setMinutes(minutes);
            setSeconds(seconds);
        }
    }

    public TimeKK() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public TimeKK(int hours, int minutes, int seconds) {
        if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59 && seconds >= 0 &&
                seconds <= 59) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    public TimeKK(LocalTime localTime) {
        this.hours = localTime.getHour();
        this.minutes = localTime.getMinute();
        this.seconds = localTime.getSecond();
    }

    //TODO more simple
    public String date() {
        int hours = getHours();
        int minutes = getMinutes();
        int seconds = getSeconds();
        String zone = "AM";
        String time = "";
        if (hours > 12) {
            zone = "PM";
            hours -= 12;
        }
        if (hours < 10) {
            time += "0" + hours + ":";
        } else {
            time += hours + ":";
        }
        if (minutes < 10) {
            time += "0" + minutes + ":";
        } else {
            time += minutes + ":";
        }
        if (seconds < 10) {
            time += "0" + seconds + zone;
        } else {
            time += seconds + zone;
        }
        return time;
    }

    public TimeKK addToDefault(TimeKK currentTime) {
        TimeKK resultTime = new TimeKK();
        resultTime.setHours(currentTime.getHours() + this.getHours());
        resultTime.setMinutes(currentTime.getMinutes() + this.getMinutes());
        resultTime.setSeconds(currentTime.getSeconds() + this.getSeconds());
        if (resultTime.getSeconds() > 60) {
            int addToMinutes = (int) Math.floor(resultTime.getSeconds()/60);
            resultTime.setSeconds((int) (resultTime.getSeconds() - addToMinutes * 60));
            resultTime.setMinutes(resultTime.getMinutes() + addToMinutes);
        }
        if (resultTime.getMinutes() > 60) {
            int addToHours = (int) Math.floor(resultTime.getMinutes()/60);
            resultTime.setMinutes((int) (resultTime.getMinutes() - addToHours * 60));
            resultTime.setHours(resultTime.getHours() + addToHours);
        }
        if (resultTime.getHours() > 24) {
            resultTime.setHours(resultTime.getHours() - 24);
        }
        return resultTime;
    }

    public TimeKK subtractToDefault(TimeKK currentTime) {
        TimeKK resultTime = new TimeKK();
        resultTime.setHours(this.getHours() - currentTime.getHours());
        resultTime.setMinutes(this.getMinutes() - currentTime.getMinutes());
        resultTime.setSeconds(this.getSeconds() - currentTime.getSeconds());
        if (resultTime.getSeconds() < 0) {
            resultTime.setSeconds((int) (resultTime.getSeconds() + 60));
            resultTime.setMinutes(resultTime.getMinutes() - 1);
        }
        if (resultTime.getMinutes() < 0) {
            resultTime.setMinutes((int) (resultTime.getMinutes() + 60));
            resultTime.setHours(resultTime.getHours() - 1);
        }
        if (resultTime.getHours() < 0) {
            resultTime.setHours(resultTime.getHours() + 24);
        }
        return resultTime;
    }

    public static TimeKK add(TimeKK time1, TimeKK time2) {
        TimeKK resultTime = new TimeKK();
        resultTime.setHours(time1.getHours() + time2.getHours());
        resultTime.setMinutes(time1.getMinutes() + time2.getMinutes());
        resultTime.setSeconds(time1.getSeconds() + time2.getSeconds());
        if (resultTime.getSeconds() > 60) {
            int addToMinutes = (int) Math.floor(resultTime.getSeconds()/60);
            resultTime.setSeconds((int) (resultTime.getSeconds() - addToMinutes * 60));
            resultTime.setMinutes(resultTime.getMinutes() + addToMinutes);
        }
        if (resultTime.getMinutes() > 60) {
            int addToHours = (int) Math.floor(resultTime.getMinutes()/60);
            resultTime.setMinutes((int) (resultTime.getMinutes() - addToHours * 60));
            resultTime.setHours(resultTime.getHours() + addToHours);
        }
        if (resultTime.getHours() > 24) {
            resultTime.setHours(resultTime.getHours() - 24);
        }
        return resultTime;
    }

    public static TimeKK subtract(TimeKK time1, TimeKK time2) {
        TimeKK resultTime = new TimeKK();
        resultTime.setHours(time1.getHours() - time2.getHours());
        resultTime.setMinutes(time1.getMinutes() - time2.getMinutes());
        resultTime.setSeconds(time1.getSeconds() - time2.getSeconds());
        if (resultTime.getSeconds() < 0) {
            resultTime.setSeconds((int) (resultTime.getSeconds() + 60));
            resultTime.setMinutes(resultTime.getMinutes() - 1);
        }
        if (resultTime.getMinutes() < 0) {
            resultTime.setMinutes((int) (resultTime.getMinutes() + 60));
            resultTime.setHours(resultTime.getHours() - 1);
        }
        if (resultTime.getHours() < 0) {
            resultTime.setHours(resultTime.getHours() + 24);
        }
        return resultTime;
    }
}
