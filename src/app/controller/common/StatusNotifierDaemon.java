package app.controller.common;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.ArrayBlockingQueue;

public class StatusNotifierDaemon extends Thread {

    private boolean running;
    private final Label status;
    private int duration;
    private final ArrayBlockingQueue<String> queue =
            new ArrayBlockingQueue<>(10);

    public StatusNotifierDaemon(Label status, int duration) {
        this.status = status;
        this.duration = duration;
        this.running = true;
        this.setDaemon(true);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void requestStop() {
        this.running = false;
    }

    public void addStatus(String status) {
        this.queue.add(status);
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (!queue.isEmpty() && this.status != null) {
                    Platform.runLater(() -> this.status.setText(queue.poll()));
                    System.out.println("Atualizando status");
                    Thread.sleep(duration);
                } else if (this.status != null) {
                    Platform.runLater(() -> this.status.setText(""));
                    synchronized (this) {
                        this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
