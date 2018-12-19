package nox.scripts.smith.core.enums;

import java.util.Random;
import java.util.stream.IntStream;

public enum SleepDuration {
    QUICK(100, 10),
    SHORT(250, 15),
    MEDIUM(500, 50),
    LONG(2000, 300),
    AFK(40_000, 10_000);

    private static Random r = new Random();

    private final int mean;
    private final int deviation;

    SleepDuration(int mean, int deviation) {
        this.mean = mean;
        this.deviation = deviation;
    }

    public int getTime() {
        return Math.max(0, (int)Math.round(r.nextGaussian() * deviation + mean));
    }
}
