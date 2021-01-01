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

package me.hydos.lint.world.structure2;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

/**
 * @author Valoeghese
 * Internal class used for handling structure generation.
 */
public final class LintStructureInstance {
	public LintStructureInstance(LintStructure structure, ChunkGenerator generator, int x, int z) {
		BlockPos pos = new BlockPos(x, structure.getYStart(generator, x, z), z);
		ChunkRandom rand = new ChunkRandom();
		rand.setPopulationSeed(structure.getDecorationSeed(), x, z);

		this.computeRooms(structure.getStartRoom(pos), rand, 0, structure.getMaxIterDepth());
	}

	private final List<Room> rooms = new ArrayList<>();
	private final Object2BooleanMap<Room> hasGenerated = new Object2BooleanArrayMap<>();

	private void computeRooms(Room startRoom, ChunkRandom random, int iter, int maxIterDepth) {
		boolean iterateDeeper = iter < maxIterDepth;
		startRoom.computeBounds(random);

		roomIterator: for (Room room : startRoom.computeNodes(startRoom.getBoundingBox(), random)) {
			Box box = room.getBoundingBox();

			for (Room existing : this.rooms) {
				if (box.intersects(existing.getBoundingBox())) {
					continue roomIterator;
				}
			}

			this.rooms.add(room);

			if (iterateDeeper) {
				computeRooms(startRoom, random, iter + 1, maxIterDepth);
			}
		}
	}
}