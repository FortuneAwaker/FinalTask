package by.popovich.last.action;

import java.io.Serializable;

/**
 * Class do describe one item of menu.
 */
public class MenuItem implements Serializable {
    /**
     * Url to go from reference.
     */
    private String url;
    /**
     * Name of reference.
     */
    private String name;

    /**
     * Constructor with parameters.
     *
     * @param urlToSet  url to set.
     * @param nameToSet name to set.
     */
    public MenuItem(final String urlToSet, final String nameToSet) {
        this.url = urlToSet;
        this.name = nameToSet;
    }

    /**
     * Getter.
     *
     * @return url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }
}
