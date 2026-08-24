package cn.yescallop.essentialsnk.command.defaults;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

import java.util.LinkedList;

public class ReplyCommand extends CommandBase {

    public ReplyCommand(EssentialsAPI api) {
        super("reply", api);
        this.setAliases(new String[]{"r"});

        // command parameters
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("message", false, CommandParamType.ID)
        });
        //KailynDev2024®
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        if (args.length < 1) {
            return false;
        }

        if (!api.getLastMessagedPlayers().containsKey(sender.getName())) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.reply.notmessaged"));
            return false;
        }

        String playerName = api.getLastMessagedPlayers().get(sender.getName());
        CommandSender target = api.getServer().getPlayer(playerName);

        if (target == null) {
            if (playerName.equalsIgnoreCase("CONSOLE")) {
                target = api.getServer().getConsoleSender();
            } else {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", playerName));
                api.getLastMessagedPlayers().remove(sender.getName());
                return false;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }

        sender.sendMessage(Language.translate("commands.message.toformat", target.getName(), builder.toString()));
        target.sendMessage(Language.translate("commands.message.fromformat", sender.getName(), builder.toString()));

        api.getLastMessagedPlayers().put(sender.getName(), target.getName());
        api.getLastMessagedPlayers().put(target.getName(), sender.getName());
        return true;
    }
}
