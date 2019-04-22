![Decorations](images/ext-decorator-cover.jpg)

'Decorations' is a simple extension which gives you some basic decorations for your ItemBinders and Sections. It has two decorations as follows :

1. DividerDecoration - Draws a divider between items
2. InsetDecoration - Adds inset around the items

### Download

![Decorators Version](https://api.bintray.com/packages/devahamed/MVA2/ext-decorator/images/download.svg)

```groovy
implementation 'dev.ahamed.mva2:ext-decorator:2.0.0-alpha01'
```

### Usage

```java
    InsetDecoration insetDecoration = new InsetDecoration(8); // Adds inset of 8px around items
    DividerDecoration dividerDecoration = new DividerDecoration(context, VERTICAL);

    // Add it to section
    section.addDecoration(insetDecoration);
    section.addDecoration(dividerDecoration);

    // Add it to ItemBinder
    itemBinder.addDecoration(insetDecoration);
    itemBinder.addDecoration(dividerDecoration);
```