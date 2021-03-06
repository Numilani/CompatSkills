package codersafterdark.compatskills.common.compats.reskillable.skillhiding;

import codersafterdark.compatskills.CompatSkills;
import codersafterdark.compatskills.common.compats.reskillable.ReskillableCompatHandler;
import codersafterdark.compatskills.common.compats.reskillable.playerexpansion.wrapper.CTSkill;
import codersafterdark.compatskills.utils.CheckMethods;
import codersafterdark.compatskills.utils.Utils;
import codersafterdark.reskillable.api.data.RequirementHolder;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("crafttweaker")
@ZenClass("mods.compatskills.VisibilityLock")
@ZenRegister
public class VisibilityLockTweaker {
    @ZenMethod
    public static void addVisibilityLock(CTSkill skill, String... defaultRequirements) {
        if (ReskillableCompatHandler.ENABLED) {
            CompatSkills.LATE_ADDITIONS.add(new AddVisibilityLock(skill, defaultRequirements));
        }
    }

    private static class AddVisibilityLock implements IAction {
        private final CTSkill skill;
        private final String[] requirements;

        private AddVisibilityLock(CTSkill skill, String... requirements) {
            this.skill = skill;
            this.requirements = requirements;
        }

        @Override
        public void apply() {
            if (CheckMethods.checkSkill(skill.getSkill()) & CheckMethods.checkStringArray(requirements)) {
                ReskillableCompatHandler.addReskillableLock(new VisibilityLock(skill.getSkill()), RequirementHolder.fromStringList(requirements));
            }
        }

        @Override
        public String describe() {
            return "Added Skill " + (skill == null ? "null" : skill.getName()) + " visibility lock. With Requirements: " + Utils.formatRequirements(requirements);
        }
    }
}