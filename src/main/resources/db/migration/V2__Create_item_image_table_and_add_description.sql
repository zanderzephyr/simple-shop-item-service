ALTER TABLE Item DROP COLUMN ImageUrl;

ALTER TABLE Item ADD Description NVARCHAR(10000) NOT NULL CONSTRAINT Temp_Item_Description_Constraint DEFAULT 'No description provided!';
ALTER TABLE Item DROP CONSTRAINT Temp_Item_Description_Constraint;

ALTER TABLE ItemHistoryState DROP COLUMN ImageUrl;

ALTER TABLE ItemHistoryState ADD Description NVARCHAR(10000) NOT NULL CONSTRAINT Temp_ItemHistory_Description_Constraint DEFAULT 'No description provided!';
ALTER TABLE ItemHistoryState DROP CONSTRAINT Temp_ItemHistory_Description_Constraint;
 
CREATE TABLE ItemImage(
 	Id BIGINT IDENTITY(1,1) PRIMARY KEY,
 	ItemId BIGINT NOT NULL
 		CONSTRAINT ID_CHECK CHECK (ItemId > 0)
 		CONSTRAINT FK_ItemImage_Item REFERENCES Item(Id) ON DELETE CASCADE,
 	StorageKey VARCHAR(1024) NOT NULL UNIQUE,
	-- GALLERY, THUMBNAIL
 	ImageType VARCHAR(20) NOT NULL,
 	-- e.g. jpg, webp
 	FileType VARCHAR(10) NOT NULL,
 	-- ACTIVE, PENDING
 	ImageState VARCHAR(20) NOT NULL,
 	CreatedAt DATETIME2 NOT NULL DEFAULT GETDATE()
 );
  CREATE INDEX INDEX_ItemImage_ItemId_ImageState ON ItemImage(ItemId, ImageState);