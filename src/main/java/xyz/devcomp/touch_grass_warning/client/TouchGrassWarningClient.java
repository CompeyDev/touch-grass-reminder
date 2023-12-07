package xyz.devcomp.touch_grass_warning.client;

import java.util.UUID;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.network.ServerInfo;

public class TouchGrassWarningClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Touch Grass Warning");
	private Thread t;

	@Override
	public void onInitializeClient(ModContainer mod) {
		LOGGER.info("Registering JOIN & DISCONNECT events...");

		ClientPlayConnectionEvents.JOIN.register((net, packet, client) -> {
			UUID sessionId = net.getSessionId();
			ServerInfo serverInfo = net.getServerInfo() == null ? new ServerInfo("Unknown", "Unknown", false)
					: net.getServerInfo();

			LOGGER.info(
					"Player initiated connection; sessionId={}, name={}, version={}, protocolVersion={}, isLocal={}, isOnline={}",
					sessionId, serverInfo.name, serverInfo.version, serverInfo.protocolVersion, serverInfo.isLocal(),
					serverInfo.online);

			PlayDurationHandler worker = new PlayDurationHandler(client, System.currentTimeMillis());

			t = new Thread(worker);
			t.start();

			LOGGER.info("Successfully started worker thread!");
		});

		ClientPlayConnectionEvents.DISCONNECT.register((net, client) -> {
			LOGGER.info("Player initiated disconnection. Resetting grass touching timer.");

			if (!t.isInterrupted()) {
				t.interrupt();
			} else {
				LOGGER.warn("Thread is already interrupted!");
			}
		});
	}
}
