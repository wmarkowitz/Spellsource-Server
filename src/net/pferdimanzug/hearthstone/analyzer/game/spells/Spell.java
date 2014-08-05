package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.logic.CloneContainer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public abstract class Spell extends CloneContainer {
	
	private EntityReference target;
	private SpellSource source;
	private EntityReference sourceEntity;
	
	public int assignedGC;
	
	public void cast(GameContext context, Player player, List<Entity> targets) {
		if (targets == null) {
			onCast(context, player, null);
			return;
		}
		for (Entity target : targets) {
			onCast(context, player, target);
		}
	}

	@Override
	public Spell clone() {
		Spell clone = (Spell) super.clone();
		clone.assignedGC = 0;
		return clone;
	}
	
	public SpellSource getSource() {
		return source;
	}

	public EntityReference getSourceEntity() {
		return sourceEntity;
	}

	public EntityReference getTarget() {
		return target;
	}

	public boolean hasPredefinedTarget() {
		if (target != null) {
			return target.isTargetGroup();
		}
		return false;
	}

	protected abstract void onCast(GameContext context, Player player, Entity target);

	public void setSource(SpellSource source) {
		this.source = source;
	}

	public void setSourceEntity(EntityReference sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	public void setTarget(EntityReference target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "[SPELL " + getClass().getSimpleName() + " target=" + target + ", source=" + source + ", hashCode: " + hashCode() + "]";
	}

}
