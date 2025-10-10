package com.nokia.edp.serialno.scheduler;

import com.nokia.edp.serialno.service.EdpSerialNoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Scheduler for periodic execution of EDP Serial Number synchronization
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EdpSerialNoScheduler {

    private final EdpSerialNoService edpSerialNoService;
    private final Lock jobLock = new ReentrantLock();

    @Value("${job.interval.seconds:3600}")
    private long jobIntervalSeconds;

    @Value("${job.run-on-start:true}")
    private boolean runOnStart;

    /**
     * Execute job on application startup if configured
     */
    @PostConstruct
    public void executeOnStartup() {
        if (runOnStart) {
            log.info("Executing job on startup as configured");
            executeJob();
        }
    }

    /**
     * Scheduled job that runs at fixed intervals
     * Uses fixedDelayString to read from application.properties
     */
    @Scheduled(fixedDelayString = "${job.interval.seconds}000", initialDelayString = "${job.interval.seconds}000")
    public void scheduledExecution() {
        executeJob();
    }

    /**
     * Execute the job with lock to prevent concurrent execution
     */
    private void executeJob() {
        if (jobLock.tryLock()) {
            try {
                log.info("========================================");
                log.info("Starting scheduled job execution...");
                log.info("========================================");
                
                long startTime = System.currentTimeMillis();
                edpSerialNoService.execute();
                long endTime = System.currentTimeMillis();
                
                log.info("========================================");
                log.info("Job completed successfully in {} ms", (endTime - startTime));
                log.info("========================================");
                
            } catch (Exception e) {
                log.error("Job execution error", e);
            } finally {
                jobLock.unlock();
            }
        } else {
            log.warn("Previous job is still running. Skipping this execution.");
        }
    }
}
