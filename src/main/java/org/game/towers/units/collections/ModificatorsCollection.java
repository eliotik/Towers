package org.game.towers.units.collections;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;

import org.apache.commons.lang3.SerializationUtils;
import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.units.towers.modificators.Modificator;
import org.game.towers.workers.XmlReader;
import org.hamcrest.Matchers;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.*;

public class ModificatorsCollection {

	private static ArrayList<Modificator> items = new ArrayList<Modificator>();

    public static void load() {
    	items = new ArrayList<Modificator>();
        XmlReader.getStreamFromFile(Config.MODIFICATORS_FILE);
        NodeList listOfElements = XmlReader.read(Config.MODIFICATOR_NODE_NAME);

        for( int i=0; i < listOfElements.getLength(); ++i ) {
            Node firstNode = listOfElements.item(i);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                Element element = (Element) firstNode;

                Modificator item = new Modificator();

                item.setId(element.getAttribute("id").toString());
                item.setName(element.getAttribute("name").toString());
                item.setDescription(element.getAttribute("type").toString());
                item.setDuration(Integer.parseInt(element.getAttribute("duration").toString()));
                item.setPriority(Integer.parseInt(element.getAttribute("priority").toString()));
                item.setImpact(element.getAttribute("impact").toString());

                NodeList attributes = element.getElementsByTagName(Config.ATTRIBUTE_NODE_NAME);
                if (attributes.getLength() > 0) {
                	for(int a = 0; a < attributes.getLength(); ++a) {

                		Node mainAttributeNode = attributes.item(a);
                		if( mainAttributeNode.getNodeType() == Node.ELEMENT_NODE ) {

                			Element attributeElement = (Element) mainAttributeNode;
                			HashMap<String, Object> attribute = new HashMap<String, Object>();
                			attribute.put(attributeElement.getAttribute("name").toString(), convertValueByType(attributeElement.getAttribute("type").toString(), attributeElement.getAttribute("value").toString()));

                			if (item.getAttributes().containsKey(element.getAttribute("class").toString())) {
                				item.getAttributes().get(element.getAttribute("class").toString()).add(attribute);
                			} else {
                				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                				list.add(attribute);
                				item.getAttributes().put(element.getAttribute("class").toString(), list);
                			}

                		}

                	}
                }

                getItems().add(item);
                Game.debug(DebugLevel.INFO, "Added Modificator type: " + item.getId());
            }
        }
        Game.debug(DebugLevel.INFO, "Loaded Modificators types: " + getItems().size());
    }

    private static Object convertValueByType(String type, String value) {
    	switch(type){
    	case "boolean": return Boolean.parseBoolean(value);
    	case "int": return Integer.parseInt(value);
    	case "double": return Double.parseDouble(value);

    	case "string":
    	default: return value;
    	}
    }

    public static ArrayList<Modificator> getItems() {
		return items;
	}

	public static Modificator getByType(String type) {
		try {
			List<Modificator> types = filter(having(on(Modificator.class).getId(), Matchers.equalTo(type)), getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find Modificator by type: " + type);
				return null;
			}
			Modificator modificator = (Modificator) types.get(0);
			return SerializationUtils.clone(modificator);
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize Modificator: " + type);
		}
		return null;
	}
}
