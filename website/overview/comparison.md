![MultiViewAdapter](/images/why-cover.jpg)

MultiViewAdapter is an utility library which helps in separating the view logic from data management logic inside recyclerview adapters. It helps you write composable view binders which can be reused across the entire app.

### Why this library?

Have you ever displayed more than two view types in your RecyclerView adapter? How was that experience? How much was the boilerplate code? 

RecyclerView offers high flexibility and performance. However, because of this flexibility, there’s a bit of boilerplate code involved to create an adapter. Code can quickly get out of hand if you have more than two viewtypes. You will have multiple if-else conditions, switch cases, and so on. Unfortunately, there is no easy way to reuse the creation and binding code of viewholders.

Lt us write an adapter to display a list of names using the deafult approach and library. And compare them side by side. 

<!-- tabs:start -->

#### ** Default Adapter **

Adapter class

```java
public class VanillaAdapter extends RecyclerView.Adapter<NameViewolder> {

    private final List<Name> names;

    public VanillaAdapter(List<Name> names) {
        this.names = names;
    }

    @NonNull @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NameViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        Name name = names.get(position);
        holder.textView.setText(name.toString());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    static class NameViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public NameViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }
}
```

Adapter class usage

```java
// Inside Activity / Fragment 
private void setUpAdapter(List<Name> names) {
    recyclerView.setAdapter(new VanillaAdapter(names));
}
```

#### ** MultiViewAdapter **

ItemBinder class

```java
public class NameBinder extends ItemBinder<NameViewolder> {

    @NonNull @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new NameViewHolder(inflate(R.layout.item_name, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, Name name) {
        holder.textView.setText(name.toString());
    }

    @Override
    public boolean canBindData(Object obj) {
        return obj instanceOf Name;
    }

    static class NameViewHolder extends ItemViewHolder<Name> {

        TextView textView;

        public NameViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }
}
```

MultiViewAdapter class usage

```java
// Inside Activity / Fragment 
private void setUpAdapter(List<Name> names) {
    MultiViewAdapter adapter = new MultiViewAdapter();

    ListSection<Name> listSection = new ListSection(names);

    adapter.registerBinders(new NameBinder());
    adapter.addSection(listSection);

    recyclerView.setAdapter(adapter);
}
```

<!-- tabs:end -->

Now lets add an header viewtype into the list and our code grows exponentially.


<!-- tabs:start -->

#### ** Default Adapter **

Adapter class

```java
public class VanillaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_HEADER = 1;
    private static final int VIEWTYPE_NAME = 2;

    private final Header header;
    private final List<Name> names;

    public VanillaAdapter(Header header, List<Name> names) {
        this.header = header;
        this.names = names;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEWTYPE_HEADER : VIEWTYPE_NAME;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == VIEWTYPE_HEADER) {
            return new HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false));
        } else {
            return new NameViewHolder(inflater.inflate(R.layout.item_name, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == VIEWTYPE_HEADER) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.textView.setText(header.toString());
        } else {
            NameViewHolder nameViewHolder = (NameViewHolder) holder;
            Name name = names.get(position);
            nameViewHolder.textView.setText(name.toString());
        }
    }

    @Override
    public int getItemCount() {
        return names.size() + 1;
    }

    static class NameViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public NameViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public HeaderViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }
}
```

Adapter class usage

```java
// Inside Activity / Fragment 
private void setUpAdapter(List<Name> names) {
    recyclerView.setAdapter(new VanillaAdapter(names, header));
}
```

#### ** MultiViewAdapter **

ItemBinder classes

```java
public class NameBinder extends ItemBinder<Name, NameViewolder> {

    @NonNull @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new NameViewHolder(inflate(R.layout.item_name, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, Name name) {
        holder.textView.setText(name.toString());
    }

    @Override
    public boolean canBindData(Object obj) {
        return obj instanceOf Name;
    }

    static class NameViewHolder extends ItemViewHolder<Name> {

        TextView textView;

        public NameViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }
}
```

```java
public class HeaderBinder extends ItemBinder<Header, HeaderViewHolder> {

    @NonNull @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HeaderViewHolder(inflate(R.layout.item_header, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, Header header) {
        holder.textView.setText(header.toString());
    }

    @Override
    public boolean canBindData(Object obj) {
        return obj instanceOf Header;
    }

    static class HeaderViewHolder extends ItemViewHolder<Header> {

        TextView textView;

        public HeaderViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_view);
        }
    }
}
```

MultiViewAdapter class usage

```java
// Inside Activity / Fragment 
private void setUpAdapter(Header header, List<Name> names) {
    MultiViewAdapter adapter = new MultiViewAdapter();

    ItemSection<Header> itemSection = new ItemSection(header);
    ListSection<Name> listSection = new ListSection(names);

    adapter.registerBinders(new HeaderBinder(), new NameBinder());
    adapter.addSection(itemSection);
    adapter.addSection(listSection);

    recyclerView.setAdapter(adapter);
}
```

<!-- tabs:end -->

!> As you can see in the above code, MultiViewAdapter has separated each viewholder logic into their own classes. This helps you maintain very complex adapter code in the longer run.

###### Default adapter approach :
1. In default adapter approach, code is not re-usable as it is. For example, in another screen if we are displaying a different list with header, the code used to display the header (createViewHolder and bindViewHolder) can not be re-used.
2. If you have to add one more viewtype the code grows painfully.
3. If the data needs to be updated, its hard to write the updation logic and call the correct notify method.

###### MultiViewAdapter approach :
1. ItemBinder handles the logic to create and display the view for that item. Ex: HeaderBinder handles creating and binding the header item to the view. Now the HeaderBinder can be added to any adapter which lets you re-use the component across the app.
2. View types are managed by the library. You can add any number of view types to a single adapter.
3. The library handles the data updation logic and calls the correct notify method.

!> Minimal changes are needed to write a binder from existing adapter. It is also helpful if you want to move away from this library :)

#### What about existing solutions?

To solve the above problem, you can also use a different library. But all such libraries have a common restrictions - Your data model will be polluted with the view logic.

1. Your data objects should be extended/implement from library's model, which can interfere with your object modeling.
2. View holder creation and binding has to be written inside the data model. You are forced to keep layout and view references inside the model class itself.
3. For complex lists, generic notifyDataSetChanged method will be called during updation. Also these libraries don’t take advantage of DiffUtil.
4. You have to write switch cases, if you want to have different item-decorations/span-size/selection-modes/expansion for different view types.
5. You lose the type information when accessing objects ie., you need to cast the object every time you need it.

MultiViewAdapter solves all of these requirements. The library was specifically designed in a way to not interfere with your object modeling and hierarchy.
