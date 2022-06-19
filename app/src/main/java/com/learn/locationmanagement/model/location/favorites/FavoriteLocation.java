package com.learn.locationmanagement.model.location.favorites;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FavoriteLocation {
	@SerializedName("id")
	private String id;

	@SerializedName("code")
	private String code;

	@SerializedName("name")
	private String name;

	@SerializedName("image")
	private String image;


	public FavoriteLocation(String image, String code, String name, String id) {
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
		FavoriteLocation favoriteLocation = (FavoriteLocation) o;
		return Objects.equals(id, favoriteLocation.id) && Objects.equals(code, favoriteLocation.code) && Objects.equals(name, favoriteLocation.name) && Objects.equals(image, favoriteLocation.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, name, image);
	}
}
