package dev.qelli.minecraft.xserversync.messenger;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitTask;

import com.saicone.delivery4j.AbstractMessenger;
import com.saicone.delivery4j.Broker;
import com.saicone.delivery4j.broker.RedisBroker;
import com.saicone.delivery4j.util.DelayedExecutor;

import dev.qelli.minecraft.xserversync.XServerSync;

public class Messenger extends AbstractMessenger implements Broker.Logger, DelayedExecutor<BukkitTask> {

    XServerSync plugin;

    public Messenger(XServerSync plugin) {
        this.plugin = plugin;
        setExecutor(command -> {
            if (!Bukkit.isPrimaryThread()) {
                command.run();
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, command);
            }
        });
    }

    @Override
    protected Broker loadBroker() {
        final String type = plugin.getConfig().getString("connection.type", "");
        if (type.equalsIgnoreCase("redis")) {
            final ConfigurationSection config = plugin.getConfig().getConfigurationSection("connection." + type.toLowerCase());
            final String url = config.getString("url");
            if (url != null) {
                return RedisBroker.of(url);
            } else {
                final String host = config.getString("host", "localhost");
                final int port = config.getInt("port", 6379);
                final String password = config.getString("password", "password");
                final int database = config.getInt("database", 0);
                final boolean ssl = config.getBoolean("ssl", false);
                return RedisBroker.of(host, port, password, database, ssl);
            }
        }
        throw new IllegalArgumentException("The messenger type '" + type + "' is not a valid type");
    }

    @Override
    public void log(int level, String msg) {
        if (level > 3) {
            return;
        }
        switch (level) {
            case 1:
                plugin.getLogger().severe(msg);
                break;
            case 2:
                plugin.getLogger().warning(msg);
                break;
            case 3:
            default:
                plugin.getLogger().info(msg);
                break;
        }
    }

    @Override
    public void log(int level, String msg, Throwable throwable) {
        if (level > 3) {
            return;
        }
        switch (level) {
            case 1:
                plugin.getLogger().log(Level.SEVERE, msg, throwable);
                break;
            case 2:
                plugin.getLogger().log(Level.WARNING, msg, throwable);
                break;
            case 3:
            default:
                plugin.getLogger().log(Level.INFO, msg, throwable);
                break;
        }
    }

    @Override
    public BukkitTask execute(Runnable command) {
        return Bukkit.getScheduler().runTaskAsynchronously(this.plugin, command);
    }

    @Override
    public BukkitTask execute(Runnable command, long delay, TimeUnit unit) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, command, (long) (unit.toMillis(delay) * 0.02));
    }

    @Override
    public BukkitTask execute(Runnable command, long delay, long period, TimeUnit unit) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, command, (long) (unit.toMillis(delay) * 0.02), (long) (unit.toMillis(period) * 0.02));
    }

    @Override
    public void cancel(BukkitTask task) {
        task.cancel();
    }
}
