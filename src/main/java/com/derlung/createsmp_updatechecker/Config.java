package com.derlung.createsmp_updatechecker;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CreateSMPUpdateChecker.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();


    public static final ForgeConfigSpec.ConfigValue<String> VERSION_API_ENDPOINT = BUILDER
            .comment("""
                    WARNING: DO NOT TOUCH THESE VALUES IF YOU DON'T KNOW WHAT YOU ARE DOING!
                    CHANGING THESE VALUES MAY BREAK THE UPDATE NOTIFICATION SYSTEM!
                    
                    the endpoint supplying the newest version number
                    (Read the documentation at https://github.com/DevLung/CreateSMPUpdateChecker for more information.)
                    Format: https://example.com/""")
            .define("versionApiEndpoint", "");

    public static final ForgeConfigSpec.ConfigValue<String> CURRENT_VERSION = BUILDER
            .comment("""
                    the current version number of the modpack
                    (this number is compared to the number of the newest version provided by the API Endpoint
                    to determine if an update is available)""")
            .define("currentVersion", "1.0.0");


    static final ForgeConfigSpec SPEC = BUILDER.build();


    public static String versionApiEndpoint;
    public static String currentVersion;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        versionApiEndpoint = VERSION_API_ENDPOINT.get();
        currentVersion = CURRENT_VERSION.get();
    }
}
