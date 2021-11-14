import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime now = getCurrentTime();
        return now.isBefore(closingTime) && now.isAfter(openingTime);
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return this.menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public int hashCode() {
        return Objects.hash(name, location, openingTime, closingTime, menu);
    }

    public boolean equals(Object obj) {
        if(obj instanceof Restaurant) {
            Restaurant other = (Restaurant) obj;
            return Objects.equals(this.name, other.name) && Objects.equals(location, other.location)
                    && Objects.equals(openingTime, other.openingTime) &&
                    Objects.equals(closingTime, other.closingTime) &&
                    Objects.equals(menu, other.menu);
        }
        return false;
    }

    public int order(List<String> selectedItems) {
        AtomicInteger total = new AtomicInteger(0);
        selectedItems.stream().forEach(itemName -> {
            Item item = Objects.requireNonNull(findItemByName(itemName));
            total.addAndGet(item.getPrice());
        });
        System.out.println("Your order will cost: "+total.get());
        return total.get();
    }
}
