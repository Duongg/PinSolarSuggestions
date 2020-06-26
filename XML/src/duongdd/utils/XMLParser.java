package duongdd.utils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XMLParser {
    private LinkedList lEvents = new LinkedList<XMLEvent>();

    private String filePath;
    public static XMLEventReader parseFileToXMLEvent(String filePath) throws XMLStreamException, FileNotFoundException {
        XMLInputFactory factory = XMLInputFactory.newInstance();

        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(filePath));
        return reader;
    }
    public void autoParseFileEventNotWellForm() throws XMLStreamException, FileNotFoundException{

        XMLEventReader reader = parseFileToXMLEvent(filePath);

        //List luu giu the mo gan nhat
        LinkedList<Integer> lStartTagPogs = new LinkedList<>();

        boolean flagRemoved = false;

        while(reader.hasNext()){
            XMLEvent event = null;

            try {
                //case 01:
                event = reader.nextEvent();
                if(flagRemoved){
                    lEvents = removeListFrom(lEvents, lStartTagPogs.getLast());
                    lStartTagPogs.removeLast();
                    flagRemoved = false;
                }
            } catch (XMLStreamException e) {
                //case 02:
                if(lStartTagPogs.size() != 0){
                    if(flagRemoved){
                        String msgErr = e.getMessage();

                        msgErr = msgErr.substring(msgErr.indexOf(":[") + 2, msgErr.indexOf("]\n"));

                        int row = Integer.parseInt(msgErr.substring(0, msgErr.indexOf(",")));
                        int col = Integer.parseInt(msgErr.substring(msgErr.indexOf(",") + 1));

                        //vi tri cua the mo
                        StartElement element = (StartElement) lEvents.get(lStartTagPogs.getLast());

                        /////////////////reader = ?
                        reader = remakeFile(filePath,
                                element.getLocation().getLineNumber(),
                                element.getLocation().getColumnNumber() -1, row, col - 3);
                        row = 0;
                        col = 0;
                        lEvents.removeAll(lEvents);
                        lStartTagPogs.removeAll(lStartTagPogs);
                        flagRemoved = false;
                    }else{
                        flagRemoved = true;
                    }
                }else{
                    break;
                }

            }catch(Exception e){
                break;
            }
            if(event != null){
                if(event.isStartElement()){
                    if(event.isStartElement()){
                        lStartTagPogs.add(lEvents.size());
                    }
                    if(event.isEndElement()){
                        lStartTagPogs.removeLast();
                    }
                    lEvents.add(event);
                }
            }
        }
    }
    public static LinkedList removeListFrom(LinkedList listRemove, int from){
        int to = listRemove.size();
        for (int i = from; i < to; i++) {
            listRemove.removeLast();
        }
        return listRemove;
    }
    public static XMLEventReader remakeFile(String filePath, int startRow, int posClose, int endRow, int posOpen){
        List<String> lines = new ArrayList<String>();
        String line = "";
        File file = new File(filePath);
        boolean flagRemoved = false;
        try {
            int curRow = 0;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            while((line = br.readLine()) != null){
                curRow ++;
                if(curRow == startRow){
                    line =removeErrorTagWithPosClose(line, posClose);
                    lines.add(line);
                    flagRemoved = true;
                }
                if(!flagRemoved){
                    lines.add(line);
                }
                if(curRow == endRow){
                    line = removeErrorTagWithPosOpen(line, posOpen);
                    flagRemoved = false;
                    lines.add(line);
                }
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fw);
            for (String s : lines) {
                if(!s.trim().equals("")){
                    out.write(s + "\n");
                }
            }
            out.flush();
            fw.close();
            out.close();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            return factory.createXMLEventReader(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(XMLStreamException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String removeErrorTagWithPosClose(String line, int posClose){
        int posOpen = 0;
        --posClose;


        for (int i = posClose; i >0 ; i--) {
            if(line.charAt(i) == '<'){
                posOpen = i;
                break;
            }
        }
        line = line.substring(0,posOpen);
        return line;
    }
    public static String removeErrorTagWithPosOpen(String line, int posOpen){
        int posClose = 0;
        for (int i = posOpen; i < line.length(); i++) {
            if(line.charAt(i) == '>'){
                posClose = i;
                break;
            }
        }
        line = line.substring(posClose +1);
        return line;

    }
}
