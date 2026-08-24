package cn.yescallop.essentialsnk.command.defaults.warp;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.level.Location;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class WarpCommand extends CommandBase {

    public WarpCommand(EssentialsAPI api) {
        super("warp", api);
        this.setAliases(new String[]{"warps"});

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("warp",true,CommandParamType.RAW_TEXT)
        });
        this.commandParameters.put("other", new CommandParameter[] {
                CommandParameter.newType("warp",false,CommandParamType.ID),
                CommandParameter.newType("player",false,CommandParamType.WILDCARD_SELECTION)
        });
        //KailynDev2024®
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length > 2) {
            this.sendUsage(sender);
            return false;
        }
        if (args.length == 0) {
            String[] list = api.getWarpsList();
            if (list.length == 0) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.warp.nowarp"));
                return false;
            }
            sender.sendMessage(Language.translate("commands.warp.list") + "\n" + String.join(", ", list));
            return true;
        }
        Location warp = api.getWarp(args[0].toLowerCase());
        if (warp == null) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.warp.notexists", args[0]));
            return false;
        }

        if (api.hasCooldown(sender)) {
            return true;
        }

        Player player;
        if (args.length == 1) {
            if (!this.testIngame(sender)) {
                return false;
            }

            player = (Player) sender;
        } else {
            if (!sender.hasPermission("essentialsnk.warp.others")) {
                this.sendPermissionMessage(sender);
                return false;
            }
            player = api.getServer().getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[1]));
                return false;
            }
        }
        api.onTP(player, warp, Language.translate("commands.warp.success", args[0]));
        if (sender != player) {
            sender.sendMessage(Language.translate("commands.warp.success.other", player.getDisplayName(), args[0]));
        }
        return true;
    }
}