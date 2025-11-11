package com.example.javaautocad.AutoCad.manager;

import com.example.javaautocad.AutoCad.ai.AutoAi;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;

public class FileWatcher {
    private final AutoAi ai;
    private WatchService watchService;
    private Path path;
    private boolean surveillance;
    private ExecutorService executorService;
    private String dir = "/Users/sanghyunyoun";


    public FileWatcher(AutoAi ai, WatchService watchService, Path path) {
        this.ai = ai;
        this.watchService = watchService;
        this.path = path;
    }
    public void watchLoop() {
        try {
            WatchKey key = null;
            while (surveillance) {
                key = watchService.take();
                for (WatchEvent<?> enent : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = enent.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path loopPath = (Path) enent.context();
                        ai.analyze(loopPath);
//                        System.out.println("감지: " + path.resolve(loopPath));
                    }
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            throw new IllegalArgumentException();
        }
    }

    public void stop() {
        try {
            surveillance = false;
            watchService.close();
        }catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    public void start() {
        surveillance = true;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path = Paths.get(dir);
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            new Thread(this::watchLoop).start();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
