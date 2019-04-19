![Spans](../images/spans.jpg)

### Basic Usage

Override the ```getSpanSize(int maxSpanCount)``` inside your ItemBinder class and return the span count. MaxSpanCount is the span count of the parent ie., Adapter or Section.

```java
  @Override public int getSpanSize(int maxSpanCount) {
    return 1; // Return any number which is less than maxSpanCount 
  }
```

Set span count to both adapter and layout manager. Also set the ``SpanSizeLookup`` to the layout manager.

```java
  multiViewAdapter.setSpanCount(3);
  gridLayoutManager.setSpanCount(3);
  gridLayoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());
```

!> By default 1 is the span count returned by all ItemBinders. You don't need a separate ItemBinder for displaying them in grid/list. You can use the same ItemBinder.

### Advance Usage

Same ItemBinder can have different span count in different ``Section``. For example,

![Multi Spans](/image/multi-spans.jpg)

In the above screenshot, both 'Section One' and 'Section Two' uses same ItemBinder, but have different span counts. This can be achieved by setting the maxSpanCount to the ``Section``.


```java
  void initAdapter() {
    multiViewAdapter.setSpanCount(6);
    gridLayoutManager.setSpanCount(6);
    gridLayoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

    sectionOne.setSpanCount(2);
    sectionTwo.setSpanCount(3);
  }
```

!> If you are setting different spancount for different section, then make sure that adapter's spancount is divisible by section's spancount.