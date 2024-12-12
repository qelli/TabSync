package dev.qelli.minecraft.xserversync.utils;

public class Constants {

    public static class Placeholders {
        public static final String InstanceName = "instancename";
        public static final String GlobalPlayers = "globalplayers";
        public static final String InstancePlayers = "instanceplayers";
        public static final String FullPlayerName = "fullplayername";
    }

    public static class Permissions {
        public static final String Admin = "xserversync.admin";
    }

    public static class Channels {
        public static final String Main = "xserversync:main";
    }

    public static class Actions {
        public static class Players {
            public static final String Join = "actions.player.join";
            public static final String Quit = "actions.player.quit";
            public static final String Update = "actions.player.update";
            public static final String Message = "actions.player.message";
        }

        public static class Instances {
            public static final String Sync = "instance.sync";
            public static final String Sync_OK = "instance.sync_ok";
            public static final String Sync_EXIT = "instance.sync_exit";
        }

        public static class Commands {
            public static final String Reload = "reload";
            public static final String Sync = "sync";
            public static final String Log = "log";
        }
    }

}
