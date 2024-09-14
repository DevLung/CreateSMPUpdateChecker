package com.derlung.createsmp_updatechecker;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class VersionChecker {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static Map<String, Object> latestVersionInfo;
    public static boolean updateAvailable = false;
    private static final String latestVersionInfoFilePath = "./latest_version_info.json";

    public static void checkForUpdates() {
        try {
            latestVersionInfo = getJsonFromUrl(new URL(Config.versionApiEndpoint));

            if (latestVersionInfo.get("latestVersion") == null) {
                throw new JsonParseException("key \"latestVersion\" does not exist");
            }
            updateAvailable = !Objects.equals(Config.currentVersion, latestVersionInfo.get("latestVersion"));

            // remove schema to be able to be able to edit json without violating the schema
            latestVersionInfo.remove("$schema");
            // add changelog with "%n%" as the line break symbol making it compatible with FancyMenu syntax
            latestVersionInfo.put("changelogFancyMenu", latestVersionInfo.get("changelog").toString().replace("\n", "%n%"));
            latestVersionInfo.put("currentVersion", Config.currentVersion);
            latestVersionInfo.put("updateAvailable", updateAvailable);
            writeJsonToFile(GSON.toJson(latestVersionInfo), latestVersionInfoFilePath);
            return;
        }
        catch (MalformedURLException ex) {
            LOGGER.error("INVALID MODPACK VERSION API ENDPOINT! Please change endpoint URL in config/createsmp_updatechecker-common.toml", ex);
        } catch (IOException ex) {
            LOGGER.error("could not get modpack version info JSON data", ex);
        } catch (JsonParseException ex) {
            LOGGER.error("modpack version info JSON is invalid", ex);
        }

        // fallback so file is always created
        writeJsonToFile(String.format("{\"currentVersion\": \"%s\", \"updateAvailable\": false}", Config.currentVersion), latestVersionInfoFilePath);
    }

    private static Map<String, Object> getJsonFromUrl(URL url) throws IOException, JsonSyntaxException {
        String json = IOUtils.toString(url, StandardCharsets.UTF_8);
        return GSON.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
    }

    private static void writeJsonToFile(String json, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(json);
        } catch (IOException ex) {
            LOGGER.error("could not write modpack version info to file", ex);
        }
    }
}
