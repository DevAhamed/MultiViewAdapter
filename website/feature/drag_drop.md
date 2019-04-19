![Drag and Drop](image/drag-cover.jpg)

### Usage

You can drag and drop the items inside or across sections. An item can be moved to any place of another item - only constraint is view type should be same for both items.

Drag and drop gesture can be added by two simple steps,

1. Attach your RecyclerView to the adapter's ``ItemTouchHelper``.

```java
  adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);
```

2. Override ``getDragDirections()`` inside your viewholder class.

```java
    @Override public int getDragDirections() {
      return ItemTouchHelper.LEFT
          | ItemTouchHelper.UP
          | ItemTouchHelper.RIGHT
          | ItemTouchHelper.DOWN;
    }
```