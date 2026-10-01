ALTER TABLE Item DROP COLUMN ImageUrl;

-- 4000: this is the character count in unicode. each character uses 2 byte.
-- the maximally valid number of bytes is 8000 per nvarchar.
ALTER TABLE Item ADD Description NVARCHAR(4000) NOT NULL CONSTRAINT Temp_Item_Description_Constraint DEFAULT 'No description provided!';
ALTER TABLE Item DROP CONSTRAINT Temp_Item_Description_Constraint;

ALTER TABLE ItemHistoryState DROP COLUMN ImageUrl;

ALTER TABLE ItemHistoryState ADD Description NVARCHAR(4000) NOT NULL CONSTRAINT Temp_ItemHistory_Description_Constraint DEFAULT 'No description provided!';
ALTER TABLE ItemHistoryState DROP CONSTRAINT Temp_ItemHistory_Description_Constraint;
 
CREATE TABLE ItemImage(
 	Id BIGINT IDENTITY(1,1) PRIMARY KEY,
 	ItemId BIGINT NOT NULL CONSTRAINT CHECK_ItemImage_Id CHECK (ItemId > 0),
 	StorageKey VARCHAR(1024) NOT NULL UNIQUE,
	-- GALLERY, THUMBNAIL
 	ImageType VARCHAR(20) NOT NULL,
 	-- e.g. jpg, webp
 	FileType VARCHAR(10) NOT NULL,
 	-- ACTIVE, PENDING
 	ImageState VARCHAR(20) NOT NULL,
 	CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
 	
 	CONSTRAINT FK_ItemImage_Item FOREIGN KEY (ItemId) REFERENCES Item(Id) ON DELETE CASCADE
 );
  CREATE INDEX INDEX_ItemImage_ItemId_ImageState ON ItemImage(ItemId, ImageState);