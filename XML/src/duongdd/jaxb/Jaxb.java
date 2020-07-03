package duongdd.jaxb;

import duongdd.entity.ElectricProductEntity;
import duongdd.entity.PinSolarProductEntity;
import duongdd.utils.XMLEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringWriter;

public class Jaxb {
    public static boolean doubleCheckElectricProduct(String filePath, ElectricProductEntity productEntity){
        try{
            JAXBContext context = JAXBContext.newInstance(ElectricProductEntity.class);
            Unmarshaller um = context.createUnmarshaller();
            Marshaller ma = context.createMarshaller();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(filePath));
            XMLEventHandler handler = new XMLEventHandler();
            um.setSchema(schema);
            um.setEventHandler(handler);
            StringWriter sw = new StringWriter();
            ma.marshal(productEntity, sw);
            ma.setEventHandler(handler);
            marshaller(productEntity);
            Validator v = schema.newValidator();
            v.validate(new JAXBSource(ma, productEntity));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void marshaller(ElectricProductEntity productEntity){
        try{
            JAXBContext context = JAXBContext.newInstance(ElectricProductEntity.class);
            Marshaller ma = context.createMarshaller();
            StringWriter sw = new StringWriter();
            ma.marshal(productEntity, sw);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean doubleCheckPinSolarProduct(String filePath, PinSolarProductEntity productEntity){
        try{
            JAXBContext context = JAXBContext.newInstance(PinSolarProductEntity.class);
            Unmarshaller um = context.createUnmarshaller();
            Marshaller ma = context.createMarshaller();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(filePath));
            XMLEventHandler handler = new XMLEventHandler();
            um.setSchema(schema);
            um.setEventHandler(handler);
            StringWriter sw = new StringWriter();
            ma.marshal(productEntity, sw);
            ma.setEventHandler(handler);
            marshaller(productEntity);
            Validator v = schema.newValidator();
            v.validate(new JAXBSource(ma, productEntity));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void marshaller(PinSolarProductEntity productEntity){
        try{
            JAXBContext context = JAXBContext.newInstance(PinSolarProductEntity.class);
            Marshaller ma = context.createMarshaller();
            StringWriter sw = new StringWriter();
            ma.marshal(productEntity, sw);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
