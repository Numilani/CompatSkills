package codersafterdark.compatskills.common.compats.reskillable.customcontent;

import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.CompatSkillConstants;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.compatskills.SkillCreator")
@ZenRegister
public class CrTSkill extends Skill {

    @ZenProperty
    public IFormattedText name;

    @ZenMethod
    public void setLevelCap(int cap) {
        skillConfig.setLevelCap(cap);
    }

    @ZenMethod
    public void setEnabled(boolean enabled) {
        skillConfig.setEnabled(enabled);
    }

    @ZenMethod
    public void setSkillPointInterval(int amount) {
        skillConfig.setSkillPointInterval(amount);
    }

    @ZenMethod
    public void setBaseCost(int cost) {
        skillConfig.setBaseLevelCost(cost);
    }

    //TODO make a way to set the level staggering

    public CrTSkill(ResourceLocation name, ResourceLocation background) {
        super(name, background);
        ReskillableRegistries.SKILLS.register(this);
    }


    private static CrTSkill createSkill(ResourceLocation name, ResourceLocation background) {
        if (ReskillableRegistries.SKILLS.containsKey(name)) {
            Skill value = ReskillableRegistries.SKILLS.getValue(name);
            if (value instanceof CrTSkill) {
                CrTSkill customSkill = (CrTSkill) value;
                if (!background.equals(customSkill.getBackground())) {
                    customSkill.background = background;
                    CraftTweakerAPI.logInfo("Loaded Skill: " + name + " - Updated Background: " + background);
                } else {
                    CraftTweakerAPI.logInfo("Loaded Skill: " + name);
                }
                return customSkill;
            }
        }
        CraftTweakerAPI.logInfo("Created new Skill: " + name + " - With Background: " + background);
        return new CrTSkill(name, background);
    }

    @ZenMethod
    public static CrTSkill createSkill(String name, String backGroundLocation) {
        if (CheckMethods.checkString(name) & CheckMethods.checkString(backGroundLocation)) {
            return createSkill(new ResourceLocation(CompatSkillConstants.MOD_ID, name), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @ZenMethod
    public static CrTSkill createNewSkill(String nameLocation, String backGroundLocation) {
        if (CheckMethods.checkString(nameLocation) & CheckMethods.checkString(backGroundLocation)) {
            return createSkill(new ResourceLocation(nameLocation), new ResourceLocation(backGroundLocation));
        }
        return null;
    }

    @Override
    public String getName() {
        return name == null ? super.getName() : name.getText();
    }

    //TODO override getSpriteFromRank when we figure out a good way to say how many pictures they provide
}
