package xyz.devcomp.touch_grass_reminder.integrations;

import xyz.devcomp.touch_grass_reminder.config.ConfigHandler;

import org.quiltmc.loader.api.QuiltLoader;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            if (!QuiltLoader.isModLoaded("yet_another_config_lib_v3"))
                return parent;
            return new ConfigHandler().showGui(parent);
        };
    }
}