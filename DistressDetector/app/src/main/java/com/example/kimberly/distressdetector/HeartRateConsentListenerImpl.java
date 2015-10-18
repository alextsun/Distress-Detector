package com.example.kimberly.distressdetector;

import android.util.Log;

import java.util.*;

public class HeartRateConsentListenerImpl {

    public static final int SAMPLES = 1000;
    public static final int MIN_SAMPLES = 10;

    private double avg;
    private double std;
    private List<Integer> samples;
    // public int currentHeartRate;

    public static final double Z_SCORE = 1.5;

    public HeartRateConsentListenerImpl() {
        samples = new ArrayList<Integer>(SAMPLES);
    }

    public void addSample(int rate) {
        if (samples.size() < SAMPLES) {
            samples.add(rate);
        }
    }

    private void calcAvg() {
        double sum = 0;
        for (int heartRate : samples) {
            sum += heartRate;
        }
        avg = sum / samples.size();
    }

    private void calcSTD() {
        calcAvg();
        double sumDiffs = 0;
        for (int heartRate : samples) {
            sumDiffs += Math.pow(heartRate - avg, 2);
        }
        double var = sumDiffs / (samples.size() - 1);
        std = Math.sqrt(var);
    }

    public boolean inNormalRange(int currentHeartRate) {
        calcSTD();
        Log.e("avg=", avg + "");
        Log.e("std=",std+"");
        double min = avg - Z_SCORE * std;
        double max = avg + Z_SCORE * std;
        return min < currentHeartRate && currentHeartRate < max;
    }
}