![Concepts](images/concept-cover.jpg)

Core mantra of MultiViewAdapter - Seperation of view logic from data management logic. You get two different components which are :

1. Section - Component to hold your data. All data related updates will run here and proper notify method will be called on the adapter.
2. ItemBinder - Component which creates and binds your view holder. All view related logic should go here.

We will take a look into following concepts in detail. This will help you understand about the library in-depth.

   1. MultiViewAdapter
   2. Section
   3. ItemBinder
   4. ItemViewHolder
   5. Decoration
   6. Extensions

## 1. MultiViewAdapter

The class which glues together all the other components. MultiViewAdapter allows you to add any number of section and itembinders to it. In most cases you can directly use the ```MultiViewAdapter``` unlike framework's ```RecyclerView.Adapter``` which needs you to subclass itself. You can use the adapter as follows,

```java
    MultiViewAdapter adapter = new MultiViewAdapter();
    recyclerView.setAdapter(adapter);
```
You can register your binders and add sections to the adapter as follows,
```java
    adapter.registerBinders(yourBinder);
    adapter.addSection(yourSection);
```
If you are using the ```GridLayoutManager```, you can set the span count like this. Also don't forget about ```SpanSizeLookup```
```java
    // Set Span Count
    adapter.setSpanCount(4);
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);

    // Set SpanSize lookup to your layout manager
    layoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());
```
MultiViewAdapter can have selection mode and expansion mode.
```java
    // Set SelectionMode
    adapter.setSelectionMode(Mode.SINGLE); // SINGLE, MULTIPLE or NONE

    // Set ExpansionMode
    adapter.setExpansionMode(Mode.MULTIPLE); // SINGLE, MULTIPLE or NONE
```


## 2. Section

Section is the building block for MultiViewAdapter. Section will hold your data which needs to be displayed inside the recyclerview -  data can be a single item or list of items. When the underlying data is changed the section will calculate the diff and call the correct notify method inside the adapter.

![How Section Works GIF](/images/how-section-works.gif)

There are different types of sections.

|Name|Description|
|---|---|
|ItemSection|Section to display single item|
|ListSection|Section to display list of items|
|HeaderSection|Section to display list of items along with a header|
|NestedSection|Section which can host other section|
|TreeSection|Section which can display items in tree fashion. Ex: Comment list|

```java
// Using ItemSection
ItemSection<SingleItemModel> itemSection = new ItemSection<>();
itemSection.setItem(singleItem);
adapter.addSection(itemSection);

// Using ListSection
ListSection<ListItemModel> listSection = new ListSection<>();
listSection.addAll(listOfItems);
adapter.addSection(listSection);

// Using HeaderSection
HeaderSection<SingleItemModel, ListItemModel> headerSection = new HeaderSection<>();
headerSection.setHeader(singleItem);
headerSection.getListSection.addAll(listOfItems);
adapter.addSection(headerSection);

// Using NestedSection
NestedSection nestedSection = new NestedSection();
nestedSection.addSection(sectionOne);
nestedSection.addSection(sectionTwo);
adapter.addSection(nestedSection);

// Using TreeSection
// A TreeSection will hold one item and another treesection
TreeSection<YourModel> treeSection = new TreeSection<>();
treeSection.setItem(yourItem);
treeSection.addSection(anotherTreeSection);
adapter.addSection(treeSection);
```

Other features offered by Section

1. You can show or hide any section inside adapter. Just call ```section.hideSection()``` and the section will be hidden in the adapter.
2. HeaderSection and TreeSection allows you to collapse/expand the section.
3. Sections can have their own span count but it should be less than adapter's span count. Kindly take a look into [Grid & Spans](feature/spans.md) page for more info.
4. You can set your own payload provider for each section. Also you can set your own diffutil.
5. Sections can have their own decorators. You can learn more about it in the [Decoration](feature/decoration.md) page.
6. Sections can have their own selection or expansion modes. Apart from ```SINGLE```, ```MULTIPLE``` and ```NONE```, sections also support ```INHERIT``` mode, which inherits the property of parent.

!> By default, sections will have ```INHERIT``` as default value for selection and expansion modes. It inherits the property from its parent which can be either MultiViewAdapter or NestedSection. This allows you to have multiple combination of selection mode. Take a look at sample app for usage.

## 3. ItemBinder

ItemBinder is the class where all view related code will be written. ItemBinder is responsible for creating and binding view holders. The method signatures are kept close to the default ```RecyclerView.Adapter``` method signatures. This will help you easily migrate to the library or away from the library. 

For each viewtype inside the recyclerview, you need to create an ItemBinder. ItemBinder also allows you to add decoration which means that you can have different decoration for different view types. You can decorator by calling ```itemBinder.addDecoration(yourDecoration)```

ItemBinder is the place where span count for each item is calculated. By default ItemBinder returns the span count as 1. You can override this behavior inside your ItemBinder class like this,

```java
  // maxSpanCount is the span count for the parent
  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount / 2; // or any other value as desired
  }
```

## 4. ItemViewHolder

ItemViewHolder is usually written as part of the ItemBinder class. It offers some nice api additions on top of its parent class ```RecyclerView.ViewHolder```. For enabling the 'Swipe to dismiss' or 'Drag and Drop' functionalities, you need to override ```getSwipeDirections()``` and ```getDragDirections()``` respectiveley.

It also allows you to,
* Call toggleItemSelection method to toggle selection of the item.
* Call toggleItemExpansion method to toggle expansion of the item.
* Call toggleSectionExpansion method to toggle expansion of the section. Note: You should call this method from items which are inside the HeaderSection, NestedSection or TreeSection.
* Call startDrag method to drag the view
* Set click and long click listeners for the entire view holder
* Call ```onClick()``` method to notify section's onClickListener.

## 5. Decoration

Decorations API is one of the unique feature of the MultiViewAdapter. Decorations can be added to the Section or ItemBinder. While decoration API's are similar to ```RecyclerView.ItemDecoration``` it adds more useful parameters to draw a decoration. For example, you can exactly know the position of the item inside the section (like top-left or top row) and draw decoration accordingly.

## 6. Extension

Not every feature can be written inside the core-library beacuse some are niche features or some features can have external dependencies. So the library was written in such way where the api's can be overridden by the developers. Apart from these, we also provide few extensions which can be drop-in replacements for the core library components. Following are some extensions provided by the library.

|Name|Description|
|---|---|
|DataBinding|Adds databinding support to the library|
|Decorator|Provides some default decorators like ```DividerDecoration```, ```InsetDecoration```|
|RxDiffUtil|Reactive way to run the diffutil in the background|
