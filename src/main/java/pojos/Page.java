package pojos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Page implements java.io.Serializable {

    private String name;
    private String fileName;
    private ArrayList<String> searchTags;
    private String data;
    private ArrayList<Page> subPages;
    private boolean isSubPage;

    public Page(){
        
    }
    public Page(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
        searchTags = new ArrayList<String>();
        subPages = new ArrayList<Page>();
        isSubPage = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }    

    public String getFileName(){
        return this.fileName;
    }

    public void setSearchTags(ArrayList<String> list){
        this.searchTags = list;
    }

    public ArrayList<String> getSearchTags(){
        return this.searchTags;
    }

    public void addSearchTag(String tag) {
        this.searchTags.add(tag);
    }

    public void clearTags(){
        this.searchTags.clear();
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData(){
        return this.data;
    }
   
    public ArrayList<Page> getSubPages(){
        return this.subPages;
    }

    public void setSubPages(ArrayList<Page> pages){
        this.subPages = pages;
    }

    public void addSubPage(Page page){
        this.subPages.add(page);
    }

    public void removeSubPage(Page page){
        this.subPages.remove(page);
    }

    public void setIsSubPage(boolean isSubPage){
        this.isSubPage = isSubPage;
    }

    public boolean getIsSubPage(){
        return this.isSubPage;
    }
}
