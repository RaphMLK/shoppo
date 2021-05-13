package fr.shoppo.ms_stat.presentation;


public interface RealTimeCalculator {
    void run();
    <T> RealTimeCalculator load(T toLoad);
}
