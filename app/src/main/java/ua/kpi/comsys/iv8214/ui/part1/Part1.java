package ua.kpi.comsys.iv8214.ui.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Part1 {
    private String students = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; " +
            "Лесик Сергій - ІО-82; Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; " +
            "Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81; Кочерук Давид - ІВ-83; " +
            "Лихацька Юлія- ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; " +
            "Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; " +
            "Снігурець Олег - ІВ-81; Роман Олександр - ІО-82; Дудка Максим - ІО-81; " +
            "Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83; Грабко Михайло - ІВ-81; " +
            "Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83; " +
            "Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; " +
            "Тарасенко Юлія - ІО-82; Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; " +
            "Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82";

    private int[] points = {12, 12, 12, 12, 12, 12, 12, 16};

    public HashMap<String, ArrayList<String>> studentsGroups() {
        HashMap<String, ArrayList<String>> studentsGroups = new HashMap<String, ArrayList<String>>();
        String[] studentGroup = students.split(";");
        for (int i = 0; i < studentGroup.length; i++) {
            String[] singleStudent = studentGroup[i].split("-");
            String student = singleStudent[0];
            String group = singleStudent[1] + "-" + singleStudent[2];
            //studentsGroups.put(group, student);
            ArrayList<String> groupStudents = studentsGroups.get(group);

            // if list does not exist create it
            if (groupStudents == null) {
                groupStudents = new ArrayList<String>();
                groupStudents.add(student);
                studentsGroups.put(group, groupStudents);
            } else {
                // add if item is not already in list
                if (!groupStudents.contains(student)) groupStudents.add(student);
            }
        }
        studentsGroups.forEach((k, v) -> {
            Collections.sort(v);
        });
        return studentsGroups;
    }

    private int randomValue(int maxValue) {
        Random random = new Random();
        switch (random.nextInt(6)) {
            case 1:
                return (int) (maxValue * 0.7);
            case 2:
                return (int) (maxValue * 0.9);
            case 3:
            case 4:
            case 5:
                return maxValue;
            default:
                return 0;
        }
    }

    public HashMap<String, HashMap<String, ArrayList<Integer>>> studentsPoint() {
        HashMap<String, HashMap<String, ArrayList<Integer>>> studentsPoint = new HashMap<>();
        studentsGroups().forEach((k, v) -> {
            HashMap<String, ArrayList<Integer>> values = new HashMap<>();
            v.forEach((s) -> {
                ArrayList<Integer> studentPoints = new ArrayList<>();
                for (int i = 0; i < points.length; i++) {
                    studentPoints.add(randomValue(points[i]));
                }
                values.put(s, studentPoints);
            });
            studentsPoint.put(k, values);
        });
        return studentsPoint;
    }

    public HashMap<String, HashMap<String, Integer>> sumPoints() {
        HashMap<String, HashMap<String, Integer>> sumPoints = new HashMap<>();
        studentsPoint().forEach((k, v) -> {
            HashMap<String, Integer> values = new HashMap<>();
            v.forEach((student, point) -> {
                int sum = 0;
                for (int i = 0; i < point.size(); i++) {
                    sum += point.get(i);
                }
                values.put(student, sum);
            });
            sumPoints.put(k, values);
        });
        return sumPoints;
    }

    public HashMap<String, Float> groupAvg() {
        HashMap<String, Float> groupAvg = new HashMap<>();
        sumPoints().forEach((group, students) -> {
            final int[] sum = {0};
            final float[] n = {0};
            students.forEach((s, integer) -> {
                sum[0] +=integer;
                n[0]++;
            });
            groupAvg.put(group, sum[0] / n[0]);
        });
        return  groupAvg;
    }

    public HashMap<String, ArrayList<String>> passedPerGroup() {
        HashMap<String, ArrayList<String>> passedPerGroup = new HashMap<>();
        sumPoints().forEach((group, students) -> {
            ArrayList<String> passedStudents = new ArrayList<>();
            students.forEach((student, point) -> {
                if (point > 60) {
                    passedStudents.add(student);
                }
            });
            passedPerGroup.put(group, passedStudents);
        });
        return passedPerGroup;
    }

    public HashMap<String, HashMap<String, String>> print() {
        HashMap<String, HashMap<String, String>> print = new HashMap<>();
        return print;
    }
}
