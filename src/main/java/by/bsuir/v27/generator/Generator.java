package by.bsuir.v27.generator;

public class Generator {
    private double m = Math.pow(2, 16);
    private double a = 1664525.0;
    private double c = 1013904223;
    private double R = 1;

    private static Generator instance;

    private Generator() {
    }

    public static Generator getInstance() {
        if (instance == null) {
            instance = new Generator();
        }
        return instance;
    }

    public double generateNumber() {
        R = (a * R + c) % m;
        return R / m;
    }
}
