package xyz.devcomp.touch_grass_warning.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import org.quiltmc.loader.api.QuiltLoader;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

public class ConfigHandler {
    public static final ConfigClassHandler<ConfigModel> HANDLER = ConfigClassHandler.createBuilder(ConfigModel.class)
            .id(new ResourceLocation("touchgrassreminder", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(QuiltLoader.getConfigDir().resolve("touch-grass-reminder.json"))
                    .setJson5(true)
                    .build())
            .build();

    public Screen showGui(Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }

}
