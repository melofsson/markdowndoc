package beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pojos.Page;
import pojos.Section;

public class ContentJsonFileHandler extends FileHandler {
    private String contentJsonPath;

private enum UPDATE_TYPE{
    ADD,
    DELETE,
    UPDATE
}

public ContentJsonFileHandler(String contentJsonPath) {
    this.contentJsonPath = contentJsonPath;
}

public ContentJsonFileHandler() {
}

public void setJsonContentPath(String path){
    this.contentJsonPath = path;
}

public void writeNewPageToContentJson(String sectionName, String pageName, String pageFileName, String[] tags){
    List<Section> sections = getSections();
    boolean sectionNotFound = true;
    Page page = new Page(pageName, pageFileName);
            for (String tag : tags) {
                page.addSearchTag(tag);
            }
    for (Section section : sections) {
        if (sectionName.equalsIgnoreCase(section.name)){
            section.addPage(page);
            sectionNotFound = false;
            break;
        }
    }
    if (sectionNotFound){
        Section section = new Section(sectionName);
        section.addPage(page);
        sections.add(section);
    }
    writeToJson(sections);
}

public void writeNewSubPageToContentJson(String sectionName, String parentPageName, String pageName, String pageFileName, String[] tags){
    List<Section> sections = getSections();
    Section currentSection = getSection(sectionName, sections);
    Page parentPage = getPageObject(parentPageName, currentSection.pages);
    Page page = new Page(pageName, pageFileName);
            for (String tag : tags) {
                page.addSearchTag(tag);
            }
    parentPage.addSubPage(page);
    writeToJson(sections);
}

private Section getSection(String section, List<Section> sections){
    for (Section currentSection : sections) {
        if (currentSection.name.equalsIgnoreCase(section)) {
            return currentSection;
        }
    }
    return null;    
}

private Page getPageObject(String pageName, List<Page> pages){
    for (Page currentPage : pages) {
        if (currentPage.getName().equalsIgnoreCase(pageName)) {
            return currentPage;
        }
    }
    return null;    
}

public void deletePageFromContentJson(String section, String pageName){
    List<Section> sections = getSections();
    Section currentSection = getSection(section, sections);
    Page page = getPageObject(pageName, currentSection.pages);
    currentSection.pages.remove(page);
    if (currentSection.pages.size() == 0) {
        sections.remove(currentSection);
    }
    writeToJson(sections);
}

public void deleteSubPageFromContentJson(String section, String parentPageName, String pageName){
    List<Section> sections = getSections();
    Section currentSection = getSection(section, sections);
    Page page = getPageObject(parentPageName, currentSection.pages);
    Page subPage = getPageObject(pageName, page.getSubPages());
    page.removeSubPage(subPage);
    writeToJson(sections);
}

public void updatePageInJsonContent(String section, String pageName, String newPageName, String[] tags){
    List<Section> sections = getSections();
    Section currentSection = getSection(section, sections);
    Page currentPage = getPageObject(pageName, currentSection.pages);
    currentPage.setName(newPageName);
    currentPage.clearTags();
    if (tags != null){
        for (String tag: tags) {
            currentPage.addSearchTag(tag);
        }
    }
    writeToJson(sections);
}

public void updateSubPageInJsonContent(String section, String parentPageName, String pageName, String newPageName, String[] tags){
    List<Section> sections = getSections();
    Section currentSection = getSection(section, sections);
    Page currentPage = getPageObject(parentPageName, currentSection.pages);
    Page currentSubPage = getPageObject(pageName, currentPage.getSubPages());
    currentSubPage.setName(newPageName);
    currentSubPage.clearTags();
    if (tags != null){
        for (String tag: tags) {
            currentSubPage.addSearchTag(tag);
        }
    }
    writeToJson(sections);
}

private void writeToJson(List<Section> sections){
    JSONObject rootObject = new JSONObject();
    JSONArray sectionArray = new JSONArray();
    for (Section section : sections) {
        JSONObject sectionObject = new JSONObject();
        JSONArray pagesArray = new JSONArray();
        sectionObject.put("section", section.name);
        for (Page page : section.pages) {
            JSONObject pageObject = new JSONObject();
            JSONArray tagsArray = new JSONArray();
            JSONArray subPagesArray = new JSONArray();
            for (String tag : page.getSearchTags()){
                tagsArray.add(tag);
            }
            for (Page subPage : page.getSubPages()) {
                JSONObject subPageObject = new JSONObject();
                JSONArray subPageTagsArray = new JSONArray();
                for (String subPageTag : subPage.getSearchTags()){
                    subPageTagsArray.add(subPageTag);
                }
                subPageObject.put("name", subPage.getName());
                subPageObject.put("fileName", subPage.getFileName());
                subPageObject.put("tags", subPageTagsArray);
                subPagesArray.add(subPageObject);
            }
            pageObject.put("name", page.getName());
            pageObject.put("fileName", page.getFileName());
            pageObject.put("tags", tagsArray);
            pageObject.put("pages", subPagesArray);
            pagesArray.add(pageObject);
        }
        sectionObject.put("pages", pagesArray);
        sectionArray.add(sectionObject);
    }

    rootObject.put("sections", sectionArray);
    writeToFile(contentJsonPath, rootObject.toJSONString());
}

public List<Section> getSections(){
        JsonArray sectionsArray = getSectionsArray();
        List<Section> sections = new ArrayList<>();
        for (Object object : sectionsArray) {
            JsonObject sectionObject = (JsonObject) object;
            Section section = new Section(sectionObject.getString("section"));
            JsonArray pagesArray = sectionObject.getJsonArray("pages");
            for (Object pageObject : pagesArray) {
                JsonObject pageJsonObject = (JsonObject) pageObject;
                Page page = new Page(pageJsonObject.getString("name"), pageJsonObject.getString("fileName"));
                for (Object tagObject: pageJsonObject.getJsonArray("tags")) {
                    page.addSearchTag((String)tagObject.toString().replace("\"", ""));
                }
                JsonArray subPagesArray = pageJsonObject.getJsonArray("pages");
                for (Object subPageObject : subPagesArray){
                    JsonObject subPageJsonObject = (JsonObject)subPageObject;
                    Page subPage = new Page(subPageJsonObject.getString("name"), subPageJsonObject.getString("fileName"));
                    for (Object subTagObject: subPageJsonObject.getJsonArray("tags")) {
                        subPage.addSearchTag((String)subTagObject.toString().replace("\"", ""));
                    }
                    page.addSubPage(subPage);

                }
              section.addPage(page);  
            }
            if (section.pages.size() != 0) {
            sections.add(section);
        }
    }
    return sections;

}
public List<Page> getSubPages(String section, String page){
    ArrayList<Page> subPages = new ArrayList<Page>();
    JsonArray sectionsArray = getSectionsArray();
    JsonObject sectionObject = getSectionObject(sectionsArray, section);
    JsonArray pagesArray = sectionObject.getJsonArray("pages");
    JsonObject pageJsonObject = getPageObject(pagesArray, page);   
    JsonArray subPagesArray = pageJsonObject.getJsonArray("pages");
    for (Object subPageObject : subPagesArray) {
        JsonObject subPageJsonObject = (JsonObject)subPageObject;
        Page newPage = new Page(subPageJsonObject.getString("name"), subPageJsonObject.getString("fileName"));
            for (Object tagObject: subPageJsonObject.getJsonArray("tags")) {
                newPage.addSearchTag((String)tagObject.toString().replace("\"", ""));
            }
            subPages.add(newPage);  
        }
    return subPages;
}

public ArrayList<String> getTags(String section, String page){
    ArrayList tags = new ArrayList<String>();
    JsonArray sectionsArray = getSectionsArray();
    JsonObject sectionObject = getSectionObject(sectionsArray, section);
    JsonArray pagesArray = sectionObject.getJsonArray("pages");
    JsonObject pageJsonObject = getPageObject(pagesArray, page);     
    JsonArray tagsArray = pageJsonObject.getJsonArray("tags");
    for (int i = 0; i < tagsArray.size(); i++) {
        tags.add(tagsArray.getString(i));
    }
    return tags;
}

public ArrayList<String> getSubPageTags(String section, String page, String subPage){
    ArrayList tags = new ArrayList<String>();
    JsonArray sectionsArray = getSectionsArray();
    JsonObject sectionObject = getSectionObject(sectionsArray, section);
    JsonArray pagesArray = sectionObject.getJsonArray("pages");
    JsonObject pageJsonObject = getPageObject(pagesArray, page);   
    JsonArray subPagesArray = pageJsonObject.getJsonArray("pages");
    JsonObject subPageObject = getPageObject(subPagesArray, subPage); 
    JsonArray tagsArray = subPageObject.getJsonArray("tags");
    for (int i = 0; i < tagsArray.size(); i++) {
        tags.add(tagsArray.getString(i));
    }
    return tags;
}

private JsonObject getSectionObject(JsonArray sectionsArray, String section){
    for (Object object : sectionsArray) {
        JsonObject sectionObject = (JsonObject) object;
        if (sectionObject.getString("section").equalsIgnoreCase(section)){
           return sectionObject;
        }
   }
   return null;
}

private JsonObject getPageObject(JsonArray pagesArray, String page){
    for (Object pageObject : pagesArray) {
        JsonObject pageJsonObject = (JsonObject) pageObject;
        if (pageJsonObject.getString("name").equalsIgnoreCase(page)) {
            return pageJsonObject;
        }
    }
   return null;
}

private JsonArray getSectionsArray(){
    JsonReader jsonReader = null;
    try {
        jsonReader = Json.createReader(new FileInputStream(contentJsonPath));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    JsonArray sectionsArray = jsonReader.readObject().getJsonArray("sections");
    return sectionsArray;
}
    
}