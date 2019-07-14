package by.popovich.last.action;

import java.io.Serializable;

/**
 * Class do describe one item of menu.
 */
public class MenuItem implements Serializable {
    private String url;
    private String name;

    public MenuItem(String urlToSet, String nameToSet) {
        this.url = urlToSet;
        this.name = nameToSet;
    }

    public void setUrl(String urlToSet) {
        this.url = urlToSet;
    }

    public String getName() {
        return name;
    }
}
