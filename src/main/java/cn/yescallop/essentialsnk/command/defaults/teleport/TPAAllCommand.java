package cn.yescallop.essentialsnk.command.defaults.teleport;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class TPAAllCommand extends CommandBase {

    public TPAAllCommand(EssentialsAPI api) {
        super("tpaall", api);

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("player",true,CommandParamType.WILDCARD_SELECTION)
        });
        //KailynDev2024®
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (api.hasCooldown(sender)) {
            return true;
        }
        Player player;
        if (args.length == 0) {
            if (!this.testIngame(sender)) {
                return false;
            }
            player = (Player) sender;
        } else if (args.length == 1) {
            player = api.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[0]));
                return false;
            }
        } else {
            this.sendUsage(sender);
            return false;
        }
        for (Player p : api.getServer().getOnlinePlayers().values()) {
            if (p != player) {
                api.requestTP(player, p, false);
                p.sendMessage(Language.translate("commands.tpahere.invite", player.getDisplayName()));
            }
        }
        player.sendMessage(Language.translate("commands.tpaall.success"));
        return true;
    }
}
