/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.npc.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.hydos.lint.entity.goal.PredicatedGoal;
import me.hydos.lint.entity.passive.human.NPCHumanEntity;
import net.minecraft.entity.ai.goal.GoalSelector;

public class NPCPathing implements Consumer<NPCHumanEntity> {
	public NPCPathing() {
	}

	private Stack<Task.Predicate> predicateStack = new Stack<>();
	private Int2ObjectMap<List<Task>> tasks = new Int2ObjectArrayMap<>();

	public NPCPathing survival(Task task) {
		return this.addTask(SURVIVAL, task);
	}

	public NPCPathing habit(Task task) {
		return this.addTask(HABIT, task);
	}

	public NPCPathing order(Task task) {
		return this.addTask(ORDER, task);
	}

	public NPCPathing work(Task task) {
		return this.addTask(WORK, task);
	}

	public NPCPathing fulfilment(Task task) {
		return this.addTask(FULFILMENT, task);
	}

	public NPCPathing leisure(Task task) {
		return this.addTask(LEISURE, task);
	}

	public NPCPathing addTask(int priority, Task task) {
		if (!this.predicateStack.isEmpty()) {
			for (Task.Predicate predicate : this.predicateStack) {
				task = task.withPredicate(predicate);
			}
		}

		this.tasks.computeIfAbsent(priority, i -> new ArrayList<>()).add(task);
		return this;
	}

	public NPCPathing when(Task.Predicate predicate, Consumer<NPCPathing> pathingBranch) {
		this.predicateStack.push(predicate);
		pathingBranch.accept(this);
		this.predicateStack.pop();

		return this;
	}

	@Override
	public void accept(NPCHumanEntity entity) {
		GoalSelector ai = entity.getGoalSelector();

		this.tasks.int2ObjectEntrySet().forEach(entry -> {
			int priority = entry.getIntKey();

			for (Task task : entry.getValue()) {
				Task.Predicate predicate = task.getPredicate();

				if (predicate == null) {
					ai.add(priority, task.createGoal(entity));
				} else {
					ai.add(priority, new PredicatedGoal(task.createGoal(entity), predicate.provider.apply(entity)));
				}
			}
		});
	}

	private static final int SURVIVAL = 0; // survival things like running from enemies
	private static final int HABIT = 1; // habitual things light going to bed at night
	private static final int ORDER = 2; // law, orders from a boss, etc
	private static final int WORK = 3; // work related things
	private static final int FULFILMENT = 4; // things for physical/mental/social fulfilment, like meeting with friends and stuff
	private static final int LEISURE = 5; // stuff for their own leisure, less important than fulfilling tasks.
}
