package cn.yescallop.essentialsnk.command;

import org.powernukkitx.Player;
import org.powernukkitx.command.Command;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.lang.TranslationContainer;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;

public abstract class CommandBase extends Command {

    protected EssentialsAPI api;

    public CommandBase(String name, EssentialsAPI api) {
        super(name);
        this.description = Language.translate("commands." + name + ".description");
        String usageMessage = Language.translate("commands." + name + ".usage");
        this.usageMessage = usageMessage.equals("commands." + name + ".usage") ? "/" + name : usageMessage;
        this.setPermission("essentialsnk." + name);
        this.api = api;
    }

    protected EssentialsAPI getAPI() {
        return api;
    }

    protected void sendUsage(CommandSender sender) {
        sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
    }

    protected boolean testIngame(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.ingame"));
            return false;
        }
        return true;
    }

    protected void sendPermissionMessage(CommandSender sender) {
        sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.permission"));
    }
}