package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * 
 * A thread-safe object that can be used for timing laps. The stopwatch objects
 * are created in the StopwatchFactory. Different threads can share a single
 * stopwatch object and safely call any of the stopwatch methods.
 * 
 */
public class StopWatchImpl implements IStopwatch {
  private final String id;
  private final List<Long> lapTimes;
  private volatile boolean running;
  private long lapStartTime;

  private StopWatchImpl(String id) {
    this.id = id;
    this.lapTimes = Collections.synchronizedList(new ArrayList<Long>());
    running = false;
    lapStartTime = 0;
  }

  /**
   * 
   * @param id
   *          create a instance of IStopwatch via id, which can't be null or
   *          empty.
   * @return IStopwatch
   */
  public static IStopwatch newStopWatch(String id)
      throws IllegalArgumentException {
    if (id.isEmpty() || id == null) {
      throw new IllegalArgumentException("Error: id is empty or null!");
    }
    return new StopWatchImpl(id);
  }

  /**
   * Returns the Id of this stopwatch
   * 
   * @return the Id of this stopwatch. Will never be empty or null.
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Starts the stopwatch.
   * 
   * @throws IllegalStateException
   *           if called when the stopwatch is already running
   */
  @Override
  public void start() throws IllegalStateException {
    synchronized (this) {
      if (running == true) {
        throw new IllegalStateException("Error: the stopwatch " + id
            + "is running. Can't be started again!");
      }
      lapStartTime = System.currentTimeMillis();
      running = true;
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called or since
   * start() was called if this is the first lap.
   * 
   * @throws IllegalStateException
   *           if called when the stopwatch isn't running
   */
  @Override
  public void lap() throws IllegalStateException {
    long laptime;
    synchronized (this) {
      if (running == false) {
        throw new IllegalStateException("Error: the stopwatch " + id
            + "isn't running. Can't record lap time!");
      }
      long endTime = System.currentTimeMillis();
      laptime = endTime - lapStartTime;
      lapStartTime = endTime;
    }
    // a single operation on synchronized list don't need to be protected in
    // critical section
    lapTimes.add(laptime);
  }

  /**
   * Stops the stopwatch (and records one final lap).
   * 
   * @throws IllegalStateException
   *           if called when the stopwatch isn't running
   */
  @Override
  public void stop() throws IllegalStateException {
    long laptime;
    synchronized (this) {
      if (running == false) {
        throw new IllegalStateException("Error: the stopwatch " + id
            + "isn't running. Can't be started again!");
      }
      long end = System.currentTimeMillis();
      laptime = end - lapStartTime;
      running = false;
    }
    lapTimes.add(laptime);
  }

  /**
   * Resets the stopwatch. If the stopwatch is running, this method stops the
   * watch and resets it. This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (this) {
      running = false;
    }
    lapTimes.clear();
  }

  /**
   * Returns a list of lap times (in milliseconds). This method can be called at
   * any time and will not throw an exception.
   * 
   * @return a list of recorded lap times or an empty list if no times are
   *         recorded.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (this) {
      return new ArrayList<Long>(lapTimes);
    }
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    res.append("stopwatch id = " + id + " < ");
    for (Long c : lapTimes) {
      res.append(c + ",");
    }
    return res.append(">").toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StopWatchImpl)) {
      return false;
    }
    StopWatchImpl oth = (StopWatchImpl) o;
    return oth.id.equals(id);
  }

  @Override
  public int hashCode() {
    int res = 17;
    res = res * 31 + id.hashCode();
    return res;
  }
}
