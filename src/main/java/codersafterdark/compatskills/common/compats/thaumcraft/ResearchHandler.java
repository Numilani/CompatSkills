package codersafterdark.compatskills.common.compats.thaumcraft;

import codersafterdark.compatskills.common.compats.thaumcraft.keys.ResearchKey;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.research.ResearchEvent;

import java.util.stream.Collectors;

public class ResearchHandler {

    @SubscribeEvent
    public void onResearchEvent(ResearchEvent.Research event) {
        String researchKey = event.getResearchKey();
        EntityPlayer player = event.getPlayer();
        PlayerData data = PlayerDataHandler.get(player);
        RequirementHolder holder = LevelLockHandler.getLockByKey(new ResearchKey(researchKey));
        if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
            event.setCanceled(true);
            TextComponentTranslation error = new TextComponentTranslation("compatskills.thaumcraft.researchError");
            String reqs = holder.getRequirements().stream().map(req -> '\n' + req.getToolTip(data)).collect(Collectors.joining());
            player.sendStatusMessage(new TextComponentString(error.getUnformattedComponentText() + CompatSkillConstants.REQUIREMENT_STRING + reqs), false);
        }
    }
}