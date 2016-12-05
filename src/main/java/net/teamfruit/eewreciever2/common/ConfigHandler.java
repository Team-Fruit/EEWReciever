package net.teamfruit.eewreciever2.common;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler extends Configuration {
	public static ConfigHandler instance;

	private final File configFile;

	public ConfigHandler(final File configFile) {
		super(configFile);
		this.configFile = configFile;

	}

	@Override
	public void save() {
		if (hasChanged())
			super.save();
	}

	@SubscribeEvent
	public void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (StringUtils.equals(eventArgs.modID, Reference.MODID))
			save();
	}

	public String getFilePath() {
		return this.configFile.getPath();
	}

	public static abstract class ConfigProperty<E> {
		protected final Configuration config;
		protected final Property property;
		private transient E prop;

		protected ConfigProperty(final Configuration config, final Property property, final E prop) {
			this.config = config;
			this.property = property;
			this.prop = prop;
		}

		public ConfigProperty<E> setComment(final String comment) {
			this.property.comment = comment;
			return this;
		}

		protected void setProp(final E prop) {
			if (!this.property.requiresMcRestart())
				this.prop = prop;
		}

		public E get() {
			return this.prop;
		}

		public abstract ConfigProperty<E> set(E value);

		public abstract ConfigProperty<E> reset();

		public abstract ConfigProperty<E> reload();

		public static ConfigProperty<String> propertyString(final ConfigHandler config, final Property property) {
			return new StringConfigProperty(config, property);
		}

		public static ConfigProperty<Boolean> propertyBoolean(final ConfigHandler config, final Property property) {
			return new BooleanConfigProperty(config, property);
		}

		public static ConfigProperty<Double> propertyDouble(final ConfigHandler config, final Property property) {
			return new DoubleConfigProperty(config, property);
		}

		public static ConfigProperty<Integer> propertyInteger(final ConfigHandler config, final Property property) {
			return new IntegerConfigProperty(config, property);
		}

		private static class StringConfigProperty extends ConfigProperty<String> {
			protected StringConfigProperty(final Configuration config, final Property property) {
				super(config, property, property.getString());
			}

			@Override
			public StringConfigProperty set(final String value) {
				this.property.set(value);
				setProp(value);
				this.config.save();
				return this;
			}

			@Override
			public StringConfigProperty reset() {
				final String p = this.property.getDefault();
				this.property.set(p);
				setProp(p);
				this.config.save();
				return this;
			}

			@Override
			public ConfigProperty<String> reload() {
				setProp(this.property.getString());
				return this;
			}
		}

		private static class BooleanConfigProperty extends ConfigProperty<Boolean> {
			protected BooleanConfigProperty(final Configuration config, final Property property) {
				super(config, property, property.getBoolean());
			}

			@Override
			public BooleanConfigProperty set(final Boolean value) {
				this.property.set(value);
				setProp(value);
				this.config.save();
				return this;
			}

			@Override
			public BooleanConfigProperty reset() {
				final String p = this.property.getDefault();
				this.property.set(p);
				setProp(this.property.getBoolean());
				this.config.save();
				return this;
			}

			@Override
			public BooleanConfigProperty reload() {
				setProp(this.property.getBoolean());
				return this;
			}
		}

		private static class DoubleConfigProperty extends ConfigProperty<Double> {
			protected DoubleConfigProperty(final Configuration config, final Property property) {
				super(config, property, property.getDouble());
			}

			@Override
			public DoubleConfigProperty set(final Double value) {
				this.property.set(value);
				setProp(value);
				this.config.save();
				return this;
			}

			@Override
			public DoubleConfigProperty reset() {
				final String p = this.property.getDefault();
				this.property.set(p);
				setProp(this.property.getDouble());
				this.config.save();
				return this;
			}

			@Override
			public DoubleConfigProperty reload() {
				setProp(this.property.getDouble());
				return this;
			}
		}

		private static class IntegerConfigProperty extends ConfigProperty<Integer> {
			protected IntegerConfigProperty(final Configuration config, final Property property) {
				super(config, property, property.getInt());
				property.getType();
			}

			@Override
			public IntegerConfigProperty set(final Integer value) {
				this.property.set(value);
				setProp(value);
				this.config.save();
				return this;
			}

			@Override
			public IntegerConfigProperty reset() {
				final String p = this.property.getDefault();
				this.property.set(p);
				setProp(this.property.getInt());
				this.config.save();
				return this;
			}

			@Override
			public IntegerConfigProperty reload() {
				setProp(this.property.getInt());
				return this;
			}
		}
	}
}
