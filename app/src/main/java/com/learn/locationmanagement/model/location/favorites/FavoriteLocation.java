package com.learn.locationmanagement.model.location.favorites;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FavoriteLocation implements Parcelable {
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

	protected FavoriteLocation(@NonNull Parcel in) {
		id = in.readString();
		code = in.readString();
		name = in.readString();
		image = in.readString();
	}

	public static final Creator<FavoriteLocation> CREATOR = new Creator<FavoriteLocation>() {
		@Override
		public FavoriteLocation createFromParcel(Parcel in) {
			return new FavoriteLocation(in);
		}

		@Override
		public FavoriteLocation[] newArray(int size) {
			return new FavoriteLocation[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(id);
		parcel.writeString(code);
		parcel.writeString(name);
		parcel.writeString(image);
	}
}
