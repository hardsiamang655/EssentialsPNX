package cn.yescallop.essentialsnk.command.defaults.home;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.level.Location;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class HomeCommand extends CommandBase {

    public HomeCommand(EssentialsAPI api) {
        super("home", api);
        this.setAliases(new String[]{"homes"});

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("home",true,CommandParamType.RAW_TEXT)
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
        if (args.length > 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            String[] list = api.getHomesList(player);
            if (list.length == 0) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.home.nohome"));
                return false;
            }
            sender.sendMessage(Language.translate("commands.home.list") + "\n" + String.join(", ", list));
            return true;
        }
        Location home = api.getHome(player, args[0].toLowerCase());
        if (home == null) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.home.notexists", args[0]));
            return false;
        }
        player.teleport(home);
        sender.sendMessage(Language.translate("commands.home.success", args[0]));
        return true;
    }
}
