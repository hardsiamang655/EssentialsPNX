package cn.yescallop.essentialsnk.command.defaults;

import org.powernukkitx.Player;
import org.powernukkitx.PlayerFood;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;

import java.util.LinkedList;

public class FeedCommand extends CommandBase {

    public FeedCommand(EssentialsAPI api) {
        super("feed", api);
        this.setAliases(new String[]{"eat"});

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("player", true, CommandParamType.WILDCARD_SELECTION)
        });
        //KailynDev2024®
    }


    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length > 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player;
        if (args.length == 0) {
            if (!this.testIngame(sender)) {
                return false;
            }
            player = (Player) sender;
        } else {
            if (!sender.hasPermission("essentialsnk.feed.others")) {
                this.sendPermissionMessage(sender);
                return false;
            }
            player = api.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[0]));
                return false;
            }
        }
        PlayerFood foodData = player.getFoodData();
        foodData.setFood(foodData.getMaxFood());
        foodData.sendFood();
        player.sendMessage(Language.translate("commands.feed.success"));
        if (sender != player) {
            sender.sendMessage(Language.translate("commands.feed.success.other", player.getDisplayName()));
        }
        return true;
    }
}
