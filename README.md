# Developer notes
The `PlayerMixin` is used for serverside messages using the player name (join, death, achievement gain, etc.). `ClientboundPlayerInfoUpdatePacketEntryMixin` will send the player the _display name_ we want, but we can't send a modifed `GameProfile` because it exceeds the packet limit. This display name is used for the tablist, but not nametags.

We cannot change nametags as the client uses the `GameProfile` name which we cannot safely lie about without the possibility of breaking core features, and is also limited to 16 characters by the client.