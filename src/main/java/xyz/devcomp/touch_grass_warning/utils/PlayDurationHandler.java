package xyz.devcomp.touch_grass_warning.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.SystemToast.SystemToastIds;
import net.minecraft.network.chat.Component;
import xyz.devcomp.touch_grass_warning.client.TouchGrassWarningClient;

public class PlayDurationHandler implements Runnable {
    Minecraft client;
    SystemToast toast;
    long startTime;
    int reminderFrequency; // In milliseconds, converted in onInitializeClient

    public PlayDurationHandler(Minecraft client, long startTime, int reminderFrequency) {
        this.client = client;
        this.startTime = startTime;

        this.toast = new SystemToast(SystemToastIds.TUTORIAL_HINT, Component.literal(String.format("You've been playing for greater than %d hours", reminderFrequency)),
                Component.literal("Excessive gaming may interfere with normal daily life"));
    }

    @Override
    public void run() {
        TouchGrassWarningClient.LOGGER.info("Started playing Minecraft at: {}", this.startTime);

        while (true) {
            if (System.currentTimeMillis() - this.startTime > this.reminderFrequency) {
                TouchGrassWarningClient.LOGGER
                        .info("Player has spent more than 24 hours in Minecraft. Displaying warning.");

                this.client.getToasts().addToast(toast);
                return;
            }

            try {
                // Check every 10 minutes
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
