package io.tebex.analytics.command.sub;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.tebex.analytics.AnalyticsPlugin;
import io.tebex.analytics.command.SubCommand;
import io.tebex.analytics.sdk.platform.PlatformConfig;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class DebugCommand extends SubCommand {
    public DebugCommand(AnalyticsPlugin platform) {
        super(platform, "debug", "analyse.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnalyticsPlugin platform = getPlatform();
        PlatformConfig analyseConfig = platform.getPlatformConfig();

        boolean debugEnabled = args.length > 0 ? Boolean.parseBoolean(args[0]) : !analyseConfig.hasDebugEnabled();

        if(debugEnabled) {
            getPlatform().sendMessage(sender, "You have enabled debug mode. This can be disabled by running &f/analytics debug &7again.");
        } else {
            getPlatform().sendMessage(sender, "You have disabled debug mode.");
        }

        YamlDocument configFile = analyseConfig.getYamlDocument();
        configFile.set("debug", debugEnabled);
        analyseConfig.setDebugEnabled(debugEnabled);

        try {
            configFile.save();
        } catch (IOException e) {
            getPlatform().sendMessage(sender, "&cFailed to toggle debug mode. Check console for more information.");
            e.printStackTrace();
        }
    }
}
