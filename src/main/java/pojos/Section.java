package pojos;


import java.util.ArrayList;
import java.util.List;

public class Section {
    
    public List<Page> pages = new ArrayList<>();
    public String name; 

    public Section(String name) {
        System.out.print("Creating object");
        this.name = name;
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }
}
