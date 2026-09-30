package com.simple_shop.item_service.item_image.persistence;

import java.time.LocalDateTime;

import com.simple_shop.item_service.item_image.model.ImageState;
import com.simple_shop.item_service.item_image.model.ImageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ItemImage")
public class ItemImageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "ItemId")
	private Long itemId;

	@Column(name = "StorageKey", length = 1024, nullable = false, unique = true)
	private String storageKey;

	@Enumerated(EnumType.STRING)
	@Column(name = "ImageType", length = 20, nullable = false)
	private ImageType imageType;

	@Column(name = "FileType", length = 10, nullable = false)
	private String fileType;

	@Enumerated(EnumType.STRING)
	@Column(name = "ImageState", length = 20, nullable = false)
	private ImageState imageState;

	@Column(name = "CreatedAt", nullable = false)
	private LocalDateTime createdAt;

	public ItemImageEntity() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(final Long itemId) {
		this.itemId = itemId;
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public void setStorageKey(final String storageKey) {
		this.storageKey = storageKey;
	}

	public ImageType getImageType() {
		return this.imageType;
	}

	public void setImageType(final ImageType imageType) {
		this.imageType = imageType;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(final String fileType) {
		this.fileType = fileType;
	}

	public ImageState getImageState() {
		return this.imageState;
	}

	public void setImageState(final ImageState imageState) {
		this.imageState = imageState;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}