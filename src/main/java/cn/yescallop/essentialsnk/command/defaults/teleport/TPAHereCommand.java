package cn.yescallop.essentialsnk.command.defaults.teleport;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class TPAHereCommand extends CommandBase {

    public TPAHereCommand(EssentialsAPI api) {
        super("tpahere", api);

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
        if (api.hasCooldown(sender)) {
            return true;
        }
        Player player = api.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[0]));
            return false;
        }
        if (sender == player) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.tpa.self"));
            return false;
        }
        if (!api.isIgnoring(player.getUniqueId(), ((Player) sender).getUniqueId())) {
            api.requestTP((Player) sender, player, false);
            player.sendMessage(Language.translate("commands.tpahere.invite", sender.getName()));
            sender.sendMessage(Language.translate("commands.tpa.success", player.getDisplayName()));
        } else {
            sender.sendMessage(Language.translate("commands.tpdeny.denied", player.getDisplayName()));
        }
        return true;
    }
}
