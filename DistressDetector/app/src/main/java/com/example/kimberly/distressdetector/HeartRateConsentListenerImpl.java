
public class HeartRateConsentListenerImpl {

    public static final int SAMPLES = 1000;

    private double avg;
    private double std;
    private int[] sampleHeartRates;
    // public int currentHeartRate;

    public static final double Z_SCORE = 1.96;

    public HeartRateConsentListenerImpl() {
        samples = new int[SAMPLES];
        getSamples();
        calcAvg();
        calcSTD();
    }

    private void getSamples() {
        for (int i = 0; i < SAMPLES; i++) {
            // TODO: fix API call
            sampleHeartRates[i] = getHeartRate();
        }
    }

    private void calcAvg() {
        double sum = 0;
        for (int heartRate : sampleHeartRates) {
            sum += heartRate;
        }
        avg = sum / SAMPLES;
    }

    private void calcSTD() {
        double sumDiffs = 0;
        for (int heartRate : sampleHeartRates) {
            sumDiffs += Math.pow(heartRate - sumDiffs, 2);
        }
        double var = sumDiffs / (SAMPLES - 1);
        std = Math.sqrt(var);
    }

    public boolean inNormalRange(int currentHeartRate) {
        double min = avg - Z_SCORE * std;
        double max = avg + Z_SCORE * std;
        return min < currentHeartRate && currentHeartRate < max;
    }
}