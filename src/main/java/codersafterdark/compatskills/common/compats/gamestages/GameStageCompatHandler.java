package codersafterdark.compatskills.common.compats.gamestages;

import codersafterdark.compatskills.common.compats.gamestages.gamestagelocks.GameStageLock;
import codersafterdark.compatskills.common.compats.gamestages.gamestagelocks.GameStageLockHandler;
import codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement.GameStageRequirement;
import codersafterdark.compatskills.common.compats.gamestages.gamestagerequirement.GameStageRequirementHandler;
import codersafterdark.compatskills.utils.CompatModuleBase;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.common.MinecraftForge;

public class GameStageCompatHandler extends CompatModuleBase {
    private static boolean registered;

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new GameStageRequirementHandler());
        RequirementRegistry registry = ReskillableAPI.getInstance().getRequirementRegistry();
        registry.addRequirementHandler("stage", GameStageRequirement::new);
        registry.addRequirementHandler("!stage", input -> registry.getRequirement("not|stage|" + input));
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    public static void addGameStageLock(GameStageLock key, RequirementHolder holder) {
        if (!registered) {
            MinecraftForge.EVENT_BUS.register(new GameStageLockHandler());
            registered = true;
        }
        LevelLockHandler.addLockByKey(key, holder);
    }
}