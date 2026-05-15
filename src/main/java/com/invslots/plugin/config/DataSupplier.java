package com.invslots.plugin.config;

import com.invslots.plugin.config.keys.ConfigKey;
import com.invslots.plugin.config.keys.TextConfigKey;
import org.bukkit.configuration.MemorySection;

import java.util.List;

public interface DataSupplier<T> {
	DataSupplier<Boolean> BOOLEAN = MemorySection::getBoolean;
	DataSupplier<Integer> INTEGER = MemorySection::getInt;
	DataSupplier<Double> DOUBLE = MemorySection::getDouble;
	DataSupplier<String> STRING = MemorySection::getString;
	DataSupplier<List<String>> STRING_LIST = MemorySection::getStringList;

	static <T> ConfigKey<T> key(DataSupplier<T> supplier, String path) {
		return new ConfigKey<>(supplier, path);
	}

	static TextConfigKey textKey(String path) {
		return new TextConfigKey(path);
	}

	/**
	 * Sets a config key to not reloadable
	 *
	 * @param key Key to update
	 * @return Unreloadable Config Keys
	 */
	static <T> ConfigKey<T> notReloadable(ConfigKey<T> key) {
		key.setReloadable(false);
		return key;
	}

	static ConfigKey<Boolean> booleanKey(String path/*, boolean def*/) {
		return key(BOOLEAN, path);
	}

	static ConfigKey<Integer> integerKey(String path) {
		return key(INTEGER, path);
	}

	static ConfigKey<Double> doubleKey(String path) {
		return key(DOUBLE, path);
	}

	static ConfigKey<String> stringKey(String path/*, String def*/) {
		return key(STRING, path);
	}

	static ConfigKey<List<String>> stringListKey(String path/*, String def*/) {
		return key(STRING_LIST, path/*, def*/);
	}

	T getValue(Config config, String path/*, T def*/);
}