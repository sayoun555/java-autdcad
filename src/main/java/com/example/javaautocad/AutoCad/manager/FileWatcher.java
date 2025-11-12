package com.example.javaautocad.AutoCad.manager;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.message.ErrorMessage;

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

    private void runPythonScript(String filePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python3",
                    "/Users/sanghyunyoun/Desktop/dxf_to_json.py",
                    filePath
            );
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            System.err.println(ErrorMessage.ERROR_PYTHON.getMessage());
        }
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
                        runPythonScript(path.resolve(loopPath).toString());
                        Path jsonFile = Paths.get(path.resolve(loopPath).toString().replace(".dxf", ".json"));
//                        System.out.println("감지: " + path.resolve(jsonFile));
                        ai.analyze(jsonFile);
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
