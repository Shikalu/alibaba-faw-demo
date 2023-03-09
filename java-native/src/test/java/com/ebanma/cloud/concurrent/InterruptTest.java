package com.ebanma.cloud.concurrent;

import com.sun.jmx.snmp.SnmpPduTrap;
import org.junit.Test;

import java.sql.SQLOutput;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class InterruptTest {

    @Test
    public void interrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                System.out.println("当前线程的中断状态" + interrupted);
            }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        Thread.sleep(100);

        System.exit(0);
    }

    @Test
    public void interruptAndStop() throws InterruptedException {
        Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    boolean interrupted = Thread.currentThread().isInterrupted();
                    System.out.println("当前线程的中断状态" + interrupted);
                }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        Thread.sleep(1000);
    }

    @Test
    public void interrupted() throws InterruptedException {
        Thread thread =new Thread(()->{
            int i = 0;
            while(true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                System.out.println("中断标记:" + interrupted);
                ++i;
                if(i>100) {
                    //静态方法，调用native方法isInterrupted(boolean ClearInterrupted);入参true，重置
                    boolean interrupted1 = Thread.interrupted();
                    System.out.println("重置中断状态1:" + interrupted1);
                    interrupted1 = Thread.interrupted();
                    System.out.println("重置中断状态2:" +interrupted1);
                    //对象方法，调用native方法isInterrupted(boolean ClearInterrupted);入参false，不重置
                    interrupted = Thread.currentThread().isInterrupted();
                    System.out.println("中断标记:" +interrupted);
                    break;
                }
            }
        });
        thread.start();
        Thread.sleep(1);
        thread.interrupt();
        Thread.sleep(1000);
        System.out.println("main中断状态检查-1："+thread.isInterrupted());
        System.out.println("main中断状态检查-2："+thread.isInterrupted());
        System.exit(0);
    }


}
