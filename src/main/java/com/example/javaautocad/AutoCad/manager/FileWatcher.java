package com.example.javaautocad.AutoCad.manager;

import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;
import com.example.javaautocad.AutoCad.view.OutputView;

import java.nio.file.StandardWatchEventKinds;

import java.io.IOException;
import java.nio.file.*;

public class FileWatcher {
    private WatchService watchService;
    private Path path;
    private boolean surveillance;
    private final OutputView outputView;
    private final AutoMeasureService autoMeasureService;

    public FileWatcher(AutoMeasureService service, OutputView outputView) {
        this.outputView = outputView;
        this.autoMeasureService = service;
    }

    public void watchLoop() {
        try {
            while (surveillance) {
                WatchKey key = watchService.take();
                events(key);
                key.reset();
            }
        } catch (InterruptedException | ClosedWatchServiceException e) {
        }
    }

    private void events(WatchKey key) {
        for (WatchEvent<?> event : key.pollEvents()) {
            processEvent(event);
        }
    }

    private void processEvent(WatchEvent<?> event) {
        if (!isModify(event)) {
            return;
        }
        Path changedFile = filePath(event);
        if (!isDxfFile(changedFile)) {
            return;
        }
        analyzeDisplay(changedFile);
    }

    private boolean isModify(WatchEvent<?> event) {
        return event.kind() == StandardWatchEventKinds.ENTRY_MODIFY;
    }

    private Path filePath(WatchEvent<?> event) {
        return path.resolve((Path) event.context());
    }

    private boolean isDxfFile(Path filePath) {
        return filePath.toString().endsWith(".dxf");
    }

    public void tireType(String type) {
        autoMeasureService.tireType(type);
    }

    private void analyzeDisplay(Path dxf) {
        String result = autoMeasureService.analyzeDxf(dxf);
        outputView.result(result);
    }

    public void stop() {
        try {
            surveillance = false;
            watchService.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.CLOSE_ERROR.getMessage());
        }
    }

    public void start(String file) {
        surveillance = true;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path = Paths.get(file);
            Files.list(path)
                    .filter(p -> p.toString().endsWith(".dxf"))
                    .findFirst()
                    .ifPresent(this::analyzeDisplay);
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            new Thread(this::watchLoop).start();
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.START_ERROR.getMessage());
        }
    }
}
