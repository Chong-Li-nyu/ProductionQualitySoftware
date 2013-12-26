package edu.nyu.pqs.stopwatch.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * A thread-safe implementation of IStopwatch.
 */
public class Stopwatch implements IStopwatch {

  private final String Id;
  private Long startTime;
  // records the last time lap() is called
  private Long lastTime;
  private boolean isRunning;
  private CopyOnWriteArrayList<Long> laps;
  private final Object lock = new Object();

  /**
   * 
   * @param Id
   *          the Id of this stopwatch.
   */
  public Stopwatch(String Id) {
    this.Id = Id;
    isRunning = false;
    laps = new CopyOnWriteArrayList<Long>();
  }

  @Override
  public String getId() {
    return Id;
  }

  @Override
  public void start() {
    synchronized (lock) {
      if (isRunning) {
        throw new IllegalStateException();
      }

      startTime = System.currentTimeMillis();
      isRunning = true;
    }
  }

  @Override
  public void lap() {
    synchronized (lock) {
      if (!isRunning) {
        throw new IllegalStateException();
      }

      Long now = System.currentTimeMillis();
      Long elapsedTime;

      if (laps.size() == 0) {
        elapsedTime = now - startTime;
      } else {
        elapsedTime = now - lastTime;
      }
      lastTime = now;
      laps.add(elapsedTime);
    }
  }

  @Override
  public void stop() {
    synchronized (lock) {
      if (!isRunning) {
        throw new IllegalStateException();
      }

      lap();
      isRunning = false;
    }
  }

  @Override
  public void reset() {
    synchronized (lock) {
      isRunning = false;
      laps.clear();
    }
  }

  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return Collections.unmodifiableList(laps);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((Id == null) ? 0 : Id.hashCode());
    result = prime * result + (isRunning ? 1231 : 1237);
    result = prime * result + ((laps == null) ? 0 : laps.hashCode());
    result = prime * result + ((lastTime == null) ? 0 : lastTime.hashCode());
    result = prime * result + ((lock == null) ? 0 : lock.hashCode());
    result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Stopwatch other = (Stopwatch) obj;
    if (Id == null) {
      if (other.Id != null)
        return false;
    } else if (!Id.equals(other.Id))
      return false;
    if (isRunning != other.isRunning)
      return false;
    if (laps == null) {
      if (other.laps != null)
        return false;
    } else if (!laps.equals(other.laps))
      return false;
    if (lastTime == null) {
      if (other.lastTime != null)
        return false;
    } else if (!lastTime.equals(other.lastTime))
      return false;
    if (lock == null) {
      if (other.lock != null)
        return false;
    } else if (!lock.equals(other.lock))
      return false;
    if (startTime == null) {
      if (other.startTime != null)
        return false;
    } else if (!startTime.equals(other.startTime))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Stopwatch [Id=" + Id + ", startTime=" + startTime + ", lastTime="
        + lastTime + ", isRunning=" + isRunning + ", laps=" + laps + ", lock="
        + lock + "]";
  }

}
