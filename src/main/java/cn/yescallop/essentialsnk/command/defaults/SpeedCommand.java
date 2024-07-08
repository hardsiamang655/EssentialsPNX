package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.effect.Effect;
import cn.nukkit.entity.effect.EffectSpeed;
import cn.nukkit.entity.effect.EffectType;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

import java.util.LinkedList;

public class SpeedCommand extends CommandBase {

    public SpeedCommand(EssentialsAPI api) {
        super("speed", api);

        // command parameters
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("multiplier", false, CommandParamType.INT),
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        });
        this.enableParamTree();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length == 0 || args.length > 2) {
            this.sendUsage(sender);
            return false;
        }
        int speed;
        try {
            speed = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.number.invalidinteger", args[0]));
            return false;
        }
        Player player;
        if (args.length == 2) {
            if (!sender.hasPermission("essentialsnk.speed.others")) {
                this.sendPermissionMessage(sender);
                return false;
            }
            player = api.getServer().getPlayer(args[1]);
        } else if (!this.testIngame(sender)) {
            return false;
        } else {
            player = (Player) sender;
        }
        if (player == null) {
            sender.sendMessage(Language.translate("commands.generic.player.notfound", args[0]));
            return false;
        }

        player.removeEffect(EffectType.SPEED);
        if (speed != 0) {
               player.getEntity().addEffect(
                       Effect.get("speed")
                               .setAmplifier(speed)
                               .setDuration(Integer.MAX_VALUE)
                       );


        }


        if (sender == player) {
            sender.sendMessage(Language.translate("commands.speed.success", speed));
        } else {
            sender.sendMessage(Language.translate("commands.speed.success.other", player.getDisplayName(), speed));
        }
        return true;
    }
}