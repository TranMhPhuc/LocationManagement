package com.learn.locationmanagement.model.location.favorites;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Location {
	@SerializedName("id")
	private String id;

	@SerializedName("code")
	private String code;

	@SerializedName("name")
	private String name;

	@SerializedName("image")
	private String image;


	public Location(String image, String code, String name, String id) {
		this.image = image;
		this.code = code;
		this.name = name;
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return Objects.equals(id, location.id) && Objects.equals(code, location.code) && Objects.equals(name, location.name) && Objects.equals(image, location.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, name, image);
	}
}
