package xyz.devcomp.touch_grass_reminder.client;

import java.util.UUID;

import xyz.devcomp.touch_grass_reminder.config.ConfigModel;
import xyz.devcomp.touch_grass_reminder.utils.PlayDurationHandler;

import net.minecraft.client.multiplayer.ServerData;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TouchGrassReminderClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Touch Grass Reminder");
	private ConfigModel config = new ConfigModel();
	private Thread thread;

	@Override
	public void onInitializeClient(ModContainer mod) {
		LOGGER.info("Touch Grass reminder init; isEnabled={}, reminderFrequency={}h", config.isEnabled,
				config.reminderFrequency);
		LOGGER.info("Registering JOIN & DISCONNECT events...");

		if (config.isEnabled) {
			ClientPlayConnectionEvents.JOIN.register((net, packet, client) -> {
				UUID sessionId = net.getId();
				ServerData serverInfo = net.getServerData() == null ? new ServerData("Unknown", "Unknown", false)
						: net.getServerData();

				LOGGER.info(
						"Player initiated connection; sessionId={}, name={}, version={}, protocolVersion={}, isLocal={}",
						sessionId, serverInfo.name, serverInfo.version, serverInfo.protocol, serverInfo.isLan());

				PlayDurationHandler worker = new PlayDurationHandler(client, System.currentTimeMillis(),
						config.reminderFrequency * 60 * 60 * 1000);

				thread = new Thread(worker);
				thread.start();

				LOGGER.info("Successfully started worker thread!");
			});

			ClientPlayConnectionEvents.DISCONNECT.register((net, client) -> {
				LOGGER.info("Player initiated disconnection. Resetting grass touching timer.");

				if (!thread.isInterrupted()) {
					thread.interrupt();
				} else {
					LOGGER.warn("Thread is already interrupted!");
				}
			});
		}
	}
}
