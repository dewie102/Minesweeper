package net.retrogame;

class Timer {
    private long startTime = 0L;
    private long endTime = 0L;
    
    public Timer() {
    
    }
    
    public void startStopWatch() {
        startTime = System.currentTimeMillis();
    }
    
    public void stopStopWatch() {
        endTime = System.currentTimeMillis();
    }
    
    public int getElapsedTimeSinceStartInSeconds() {
        return startTime == 0 ? 0 : (int)((System.currentTimeMillis() - startTime) / 1000);
    }
    
    public int getRecordedTimeInSeconds() {
        return (int)((endTime - startTime) / 1000);
    }
}