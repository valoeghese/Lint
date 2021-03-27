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

package me.hydos.lint.core.block;

import static me.hydos.lint.Lint.RESOURCE_PACK;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import me.hydos.lint.Lint;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

// TODO add render layer nonsense here.
/**
 * @reason collect a lot of things in one place and keep them nice and java.
 */
public class Model {
	public Model() {
	}

	StateFunction state = null;
	ModelFunction blockModel = null;
	Boolean opaque = null; // This can also be determined by the material. If both are set, this wins.
	Layer renderLayer = Layer.DEFAULT;

	public Model blockState(StateFunction blockStateCreator) {
		this.state = blockStateCreator;
		return this;
	}

	public Model blockModel(ModelFunction modelCreator) {
		this.blockModel = modelCreator;
		return this;
	}

	public Model opaque(boolean opaque) {
		this.opaque = opaque;
		return this;
	}

	public Model renderOn(Layer layer) {
		this.renderLayer = layer;
		return this;
	}

	public Model immutable() {
		return new Immutable(this);
	}

	void createFor(Block block, String id) {
		JBlockModel[] modelLocations;

		if (this.blockModel == null) {
			modelLocations = new JBlockModel[] {new JBlockModel(Lint.id(id))};
		} else {
			Set<Map.Entry<Identifier, JModel>> models = this.blockModel.createModels(subPath -> Lint.id("block/" + id + (subPath.isEmpty() ? "" : ("_" + subPath)))).entrySet();

			for (Map.Entry<Identifier, JModel> model : models) {
				RESOURCE_PACK.addModel(model.getValue(), model.getKey());
			}

			modelLocations = models.stream().map(entry -> new JBlockModel(entry.getKey())).collect(Collectors.toList()).toArray(new JBlockModel[0]); // Pre-Java 11 support. In java 11, we would use generators.
		}

		if (this.state != null) {
			// "ID Location"
			Identifier idl = Lint.id(id);
			RESOURCE_PACK.addBlockState(this.state.createModel(idl, modelLocations), idl);
		}
	}

	@FunctionalInterface
	public interface StateFunction {
		JState createModel(Identifier id, JBlockModel[] models);
	}

	@FunctionalInterface
	public interface ModelFunction {
		Map<Identifier, JModel> createModels(Function<String, Identifier> ids);
	}

	// States
	public static final StateFunction SIMPLE_STATE = (id, models) -> JState.state(JState.variant().put("", models[0]));

	public static final StateFunction TALL_CROSS_STATE = (id, models) ->  JState.state(JState.variant().put("half=lower", models[0]).put("half=upper", models[1]));

	// Block Model Functions
	public static final ModelFunction CUBE_ALL = ids -> {
		Identifier id = ids.apply("");
		return ImmutableMap.of(id, JModel.model().parent("block/cube_all").textures(JModel.textures().var("all", id.toString())));
	};

	private static final ModelFunction TALL_CROSS = ids -> {
		Identifier bottomModelId = ids.apply("");
		Identifier topModelId = ids.apply("top");
		return ImmutableMap.of(
				// bottom model
				bottomModelId,
				JModel.model()
					.parent("block/cross")
					.textures(JModel.textures().var("cross", bottomModelId.toString())),
				// top model
				topModelId,
				JModel.model()
					.parent("block/cross")
					.textures(JModel.textures().var("cross", topModelId.toString())));
	};

	// Models

	public static final Model SIMPLE_CUBE_ALL = new Model()
			.blockState(SIMPLE_STATE)
			.blockModel(CUBE_ALL)
			.immutable();

	public static final Model CUTOUT_CUBE_ALL = new Model()
			.blockState(SIMPLE_STATE)
			.blockModel(CUBE_ALL)
			.renderOn(Layer.CUTOUT_MIPPED)
			.opaque(false)
			.immutable();

	public static final Model NONE = new Model().immutable();

	public static final Model SIMPLE_BLOCKSTATE_ONLY = new Model().blockState(SIMPLE_STATE).immutable();

	public static final Model TALL_PLANT = new Model()
			.blockState(TALL_CROSS_STATE)
			.blockModel(TALL_CROSS)
			.opaque(false)
			.renderOn(Layer.CUTOUT_MIPPED)
			.immutable();

	private static final class Immutable extends Model {
		public Immutable(Model parent) {
			this.opaque = parent.opaque;
			this.state = parent.state;
			this.blockModel = parent.blockModel;
			this.renderLayer = parent.renderLayer;
		}

		@Override
		public Model opaque(boolean opaque) {
			throw new UnsupportedOperationException("Cannot set opaque property on an immutable model!");
		}

		@Override
		public Model blockModel(ModelFunction modelCreator) {
			throw new UnsupportedOperationException("Cannot set blockModel property on an immutable model!");
		}

		@Override
		public Model blockState(StateFunction blockStateCreator) {
			throw new UnsupportedOperationException("Cannot set blockState property on an immutable model!");
		}
		
		@Override
		public Model renderOn(Layer layer) {
			throw new UnsupportedOperationException("Cannot set render layer property on an immutable model!");
		}
	}
}
