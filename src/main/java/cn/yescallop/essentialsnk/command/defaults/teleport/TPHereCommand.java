package cn.yescallop.essentialsnk.command.defaults.teleport;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class TPHereCommand extends CommandBase {

    public TPHereCommand(EssentialsAPI api) {
        super("tphere", api);
        this.setAliases(new String[]{"s"});

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("player",false,CommandParamType.WILDCARD_SELECTION)
        });
        //KailynDev2024®
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (!this.testIngame(sender)) {
            return false;
        }
        if (args.length != 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player = api.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[0]));
            return false;
        }
        player.teleport((Player) sender);
        player.sendMessage(Language.translate("commands.tphere.other", ((Player) sender).getDisplayName()));
        sender.sendMessage(Language.translate("commands.tphere.success", player.getDisplayName()));
        return true;
    }
}
