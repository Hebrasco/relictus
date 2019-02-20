package data;

import java.util.ResourceBundle;

public class Language {
    public String name;
    public ResourceBundle resourceBundle;

    public Language(String name, ResourceBundle resourceBundle) {
        this.name = name;
        this.resourceBundle = resourceBundle;
    }
}