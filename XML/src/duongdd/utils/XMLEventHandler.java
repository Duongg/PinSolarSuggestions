package duongdd.utils;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class XMLEventHandler implements ValidationEventHandler {
    @Override
    public boolean handleEvent(ValidationEvent event) {
        return true;
    }
}
