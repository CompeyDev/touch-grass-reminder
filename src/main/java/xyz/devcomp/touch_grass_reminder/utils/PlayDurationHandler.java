package xyz.devcomp.touch_grass_reminder.utils;

import xyz.devcomp.touch_grass_reminder.client.TouchGrassReminderClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.SystemToast.SystemToastIds;
import net.minecraft.network.chat.Component;

public class PlayDurationHandler implements Runnable {
    Minecraft client;
    SystemToast toast;
    long startTime;
    int reminderFrequency; // In milliseconds, converted in onInitializeClient

    public PlayDurationHandler(Minecraft client, long startTime, int reminderFrequency) {
        this.client = client;
        this.startTime = startTime;
        this.reminderFrequency = reminderFrequency;

        this.toast = new SystemToast(SystemToastIds.TUTORIAL_HINT,
                Component.translatable("touch_grass_reminder.toast.name", reminderFrequency),
                Component.translatable("touch_grass_reminder.toast.description"));
    }

    @Override
    public void run() {
        TouchGrassReminderClient.LOGGER.info("Started playing Minecraft at: {}", this.startTime);

        while (true) {
            if (System.currentTimeMillis() - this.startTime > this.reminderFrequency) {
                TouchGrassReminderClient.LOGGER
                        .info("Player has spent more than 24 hours in Minecraft. Displaying reminder.");

                this.client.getToasts().addToast(toast);
                return;
            }

            try {
                // Recheck every 10 minutes
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
