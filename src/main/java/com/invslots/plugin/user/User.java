package com.invslots.plugin.user;

import lombok.Getter;

import java.util.UUID;

public class User {

	@Getter
	private final UUID uuid;

	public User(UUID uuid) {
		this.uuid = uuid;
	}
}
