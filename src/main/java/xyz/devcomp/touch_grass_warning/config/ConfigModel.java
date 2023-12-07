package xyz.devcomp.touch_grass_warning.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;

public class ConfigModel {
    @SerialEntry(comment = "Whether the mod's functionality is enabled")
    @AutoGen(category = "touch_grass_warning")
    @TickBox
    public boolean isEnabled = true;

    @SerialEntry(comment = "Number of hours the warning should be displayed after")
    @AutoGen(category = "touch_grass_warning")
    @IntField(min = 1)
    public int reminderFrequency = 24; 
}
