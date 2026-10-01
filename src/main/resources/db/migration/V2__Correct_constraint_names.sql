-- Rename constraints for Item table
EXEC sp_rename 'simple_shop.CHECK_ITEM_STOCK', 'CHECK_Item_Stock', 'OBJECT';
EXEC sp_rename 'simple_shop.CHECK_ITEM_PRICE', 'CHECK_Item_Price', 'OBJECT';
EXEC sp_rename 'simple_shop.CHECK_ITEM_LAST_CHANGED_BY', 'CHECK_Item_LastChangedBy', 'OBJECT';

-- Rename constraint for ItemHistoryState table
EXEC sp_rename 'simple_shop.ID_CHECK', 'CHECK_ItemHistoryState_ItemId', 'OBJECT';
EXEC sp_rename 'simple_shop.CHECK_ITEM_HISTORY_STOCK', 'CHECK_ItemHistoryState_Stock', 'OBJECT';
EXEC sp_rename 'simple_shop.CHECK_ITEM_HISTORY_PRICE', 'CHECK_ItemHistoryState_Price', 'OBJECT';
EXEC sp_rename 'simple_shop.CHECK_ITEM_HISTORY_LAST_CHANGED_BY', 'CHECK_ItemHistoryState_LastChangedBy', 'OBJECT';