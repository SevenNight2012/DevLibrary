package com.xxc.dev.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 全局线程池
 */
public class ThreadPool {

    /**
     * 当前手机系统CPU核数
     */
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final ThreadPool INSTANCE = new ThreadPool();

    public static ThreadPool getInstance() {
        return INSTANCE;
    }

    private ThreadPool() {
        int coreThread = Math.max(3, CPU_COUNT);
        int maxThread = Math.max(CPU_COUNT * 2 + 1, 8);
        long keepAlive = 60;
        Executors.newFixedThreadPool(5);
        mThreadPool = new ThreadPoolExecutor(coreThread, maxThread, keepAlive, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    private ExecutorService mThreadPool;

    public void execute(Runnable runnable) {
        mThreadPool.execute(runnable);
    }
}
