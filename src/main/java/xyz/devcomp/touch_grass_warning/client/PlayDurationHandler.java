package xyz.devcomp.touch_grass_warning.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.SystemToast.Type;
import net.minecraft.text.Text;

public class PlayDurationHandler implements Runnable {
    MinecraftClient client;
    SystemToast toast;
    long startTime;

    public PlayDurationHandler(MinecraftClient client, long startTime) {
        this.client = client;
        this.startTime = startTime;

        this.toast = new SystemToast(Type.TUTORIAL_HINT, Text.literal("You've been playing for greater than 24 hours"),
                Text.literal("Excessive gaming may interfere with normal daily life"));
    }

    @Override
    public void run() {
        TouchGrassWarningClient.LOGGER.info("Started playing Minecraft at: {}", this.startTime);

        while (true) {
            if (System.currentTimeMillis() - this.startTime > 24 * 60 * 60 * 1000) {
                TouchGrassWarningClient.LOGGER
                        .info("Player has spent more than 24 hours in Minecraft. Displaying warning.");

                this.client.getToastManager().add(this.toast);
            }

            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
