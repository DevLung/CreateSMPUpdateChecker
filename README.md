# Create-SMP Update Checker

A simple update checker for the Create-SMP modpack.

## Usage

- install the mod
- run your Minecraft instance once to generate the config
- open the `config/createsmp_updatechecker-common.toml` in your instance's files
- set the `versionApiEndpoint` variable to a valid version info API endpoint ([API Spec](#api-specification))
- set the `currentVersion` variable to the current version of your modpack

Now you can distribute your modpack.
**(Don't forget to update `currentVersion` each time you update your modpack.)**

I recommend parsing the `latest_version_info.json` File
in the root directory of the instance with [FancyMenu](https://modrinth.com/mod/fancymenu)
to create a GUI update notification in the main menu.

Otherwise, the update notification will just show up in the console and log.
(That's what makes the mod also useful on the server.)

## API Specification

The API endpoint serving the information about the latest modpack version to the mod
should respond with a JSON object containing the following data:

```json
{
  "latestVersion": "<latest modpack version>",
  "updateType": "<needed OR optional>",
  "downloadUrl": "<latest modpack version download link>",
  "changelog": "<update changelog>"
}
```

*(`<placeholder>` = placeholder accepting data explained within;
`one OR two` = either one or two is accepted)*

You should probably also create a JSON schema to validate your JSON against
before your API responds with it to circumvent any errors and bugs.

**Now just update that JSON whenever you release a new version
to notify any outdated instances using the mod (assuming it is [properly set up](#usage))**

