package nox.scripts.smith.tools;

import nox.scripts.smith.core.Sleep;
import nox.scripts.smith.core.enums.SleepDuration;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SleepGenerator {
    private static int iterations = 100;

    public static void main(String[] args) {
        for (SleepDuration dur: SleepDuration.values()) {
            Statistics stat = new Statistics(dur.name());

            for (int i = 0; i < iterations; i++) {
                stat.add(dur.getTime());
            }

            stat.compute();

            log(stat.toString());
        }
    }


    private static void log(String s, Object... params) {
        System.out.println(String.format(s, params));
    }

    private static class Statistics {
        int min;
        int max;
        int range;
        double mean;
        double dev;

        List<Integer> times;
        private String setName;

        @Override
        public String toString() {
            return "Statistics{" +
                    "min=" + min +
                    ", max=" + max +
                    ", range=" + range +
                    ", mean=" + mean +
                    ", dev=" + dev +
                    ", setName='" + setName + '\'' +
                    '}';
        }

        public Statistics(String setName) {
            this.setName = setName;
            times = new ArrayList<>();
        }

        public void add(int i) {
            times.add(i);
        }

        public void compute() {
            if (times.size() == 0) return;
            min = Collections.min(times);
            max = Collections.max(times);
            range = max - min;
            mean = times.stream().reduce(0, Integer::sum) / times.size();
            dev = Math.sqrt(times.stream().map(m -> (double)m).reduce(0.0, (prev, next) -> prev += Math.pow(next - mean, 2)) / iterations );
        }
    }
}
