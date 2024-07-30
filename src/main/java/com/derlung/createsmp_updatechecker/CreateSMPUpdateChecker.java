package com.derlung.createsmp_updatechecker;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateSMPUpdateChecker.MODID)
public class CreateSMPUpdateChecker {
    public static final String MODID = "createsmp_updatechecker";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateSMPUpdateChecker() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("checking for modpack updates...");
        VersionChecker.checkForUpdates();

        if (VersionChecker.updateAvailable) {
            LOGGER.warn(String.format("""
                    NEW UPDATE AVAILABLE!
                    
                    A NEW CREATE-SMP MODPACK VERSION IS AVAILABLE!
                    CURRENT VERSION:    %s
                    NEW VERSION:        %s
                    DOWNLOAD HERE:      %s
                    UPDATE TYPE:        %s
                    CHANGELOG:
                    %s
                    A NEW CREATE-SMP MODPACK VERSION IS AVAILABLE!
                    """,
                    Config.currentVersion,
                    VersionChecker.latestVersionInfo.get("latestVersion"),
                    VersionChecker.latestVersionInfo.get("downloadUrl"),
                    VersionChecker.latestVersionInfo.get("updateType"),
                    VersionChecker.latestVersionInfo.get("changelog")
            ));
        } else {
            LOGGER.info("modpack version is up to date");
        }
    }
}
