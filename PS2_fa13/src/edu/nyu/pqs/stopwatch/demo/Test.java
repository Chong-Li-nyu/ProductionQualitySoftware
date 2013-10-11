package edu.nyu.pqs.stopwatch.demo;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * test 5 thread use the stop watch to record 11 lap times
 * @author Wenjie Chen
 * 
 */
class Test {
  private static void test() {
    final IStopwatch s = StopwatchFactory.getStopwatch("1");
    ExecutorService exec = Executors.newCachedThreadPool();
    s.start();
    for (int i = 0; i < 5; i++) {
      exec.execute(new Runnable() {
        public void run() {
          try {
            Random rand = new Random();
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(3000));
            s.lap();
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000));
            s.lap();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }
    try {
      TimeUnit.MILLISECONDS.sleep(20000);
      s.stop();
      System.out.println(StopwatchFactory.getStopwatches());
    } catch (Exception e) {
      e.printStackTrace();
    }
    exec.shutdown();
  }

  public static void main(String[] args) {
    test();
  }
}
