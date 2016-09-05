package chess.interfaces.document.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by YJH on 2016/5/16.
 */
@Controller
@RequestMapping("/document")
public class DocumentController {

    @RequestMapping(value = "/api")
    public ModelAndView api() {
        try {
            return new ModelAndView("/document/api", "doc", freemarker.ext.dom.NodeModel.parse(new File(getClass().getResource("/").getPath() + "api.xml")));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/document/api");
    }

    @RequestMapping(value = "/apicommand/{id}")
    @ResponseBody
    public String apicommand(@PathVariable String id, HttpServletRequest request) {
        try {
            command(id, request);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void command(String id, HttpServletRequest request) throws DocumentException, SAXException, IOException {
        //提取xml文档
        SAXReader saxReader = new SAXReader();
        File inputFile = new File(getClass().getResource("/").getPath() + "api.xml");
        Document document = saxReader.read(inputFile);

        if (id.equals("addtable")) {
            //获取根节点
            Element root = document.getRootElement().element("tables");
            //创建元素

            Element newItem = root.addElement("table");

            Element id1 = newItem.addElement("id");
            id1.setText(UUID.randomUUID().toString());
            newItem.addElement("name");
            newItem.addElement("value");
            newItem.addElement("rows");
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
            writer.write(document);
            writer.close();
        } else if (id.equals("updatetablename")) {
            //获取根节点
            Element root = document.getRootElement().element("tables");

            String tableid = request.getParameter("tableid");
            String newname = request.getParameter("newname");
            String newvalue = request.getParameter("newvalue");

            Iterator iterator = root.nodeIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Element) {
                    Element element = (Element) o;
                    if (element.element("id").getText().equals(tableid)) {
                        element.element("name").setText(newname);
                        element.element("value").setText(newvalue);
                        OutputFormat format = OutputFormat.createPrettyPrint();
                        XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
                        writer.write(document);
                        writer.close();
                        return;
                    }
                }
            }
        } else if (id.equals("deltable")) {
            //获取根节点
            Element root = document.getRootElement().element("tables");

            String tableid = request.getParameter("tableid");
            Iterator iterator = root.nodeIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Element) {
                    Element element = (Element) o;
                    if (element.element("id").getText().equals(tableid)) {
                        root.remove(element);
                        OutputFormat format = OutputFormat.createPrettyPrint();
                        XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
                        writer.write(document);
                        writer.close();
                        return;
                    }
                }
            }
        } else if (id.equals("addrow")) {
            //获取根节点
            Element root = document.getRootElement().element("tables");

            String tableid = request.getParameter("tableid");

            Iterator iterator = root.nodeIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Element) {
                    Element element = (Element) o;
                    if (element.element("id").getText().equals(tableid)) {
                        Element element1 = element.element("rows").addElement("row");
                        element1.addElement("id").setText(UUID.randomUUID().toString());
                        element1.addElement("name");
                        element1.addElement("value");
                        element1.addElement("content");
                        OutputFormat format = OutputFormat.createPrettyPrint();
                        XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
                        writer.write(document);
                        writer.close();
                        return;
                    }
                }
            }
        } else if (id.equals("updaterow")) {
            //获取根节点
            Element root = document.getRootElement().element("tables");

            String name = request.getParameter("name");
            String value = request.getParameter("value");
            String content = request.getParameter("content");
            String tableid = request.getParameter("tableid");
            String rowid = request.getParameter("rowid");

            Iterator iterator = root.nodeIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Element) {
                    Element element = (Element) o;
                    if (element.element("id").getText().equals(tableid)) {
                        Iterator iterator1 = element.element("rows").nodeIterator();
                        while (iterator1.hasNext()) {
                            Object o1 = iterator1.next();
                            if (o1 instanceof Element) {
                                Element element1 = (Element) o1;
                                if (element1.element("id").getText().equals(rowid)) {
                                    element1.element("name").setText(name);
                                    element1.element("value").setText(value);
                                    element1.element("content").setText(content);
                                    OutputFormat format = OutputFormat.createPrettyPrint();
                                    XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
                                    writer.write(document);
                                    writer.close();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } else if (id.equals("delrow")) {
            //获取根节点
            Element root = document.getRootElement().element("tables");

            String tableid = request.getParameter("tableid");
            String rowid = request.getParameter("rowid");

            Iterator iterator = root.nodeIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Element) {
                    Element element = (Element) o;
                    if (element.element("id").getText().equals(tableid)) {
                        Iterator iterator1 = element.element("rows").nodeIterator();
                        while (iterator1.hasNext()) {
                            Object o1 = iterator1.next();
                            if (o1 instanceof Element) {
                                Element element1 = (Element) o1;
                                if (element1.element("id").getText().equals(rowid)) {
                                    element.element("rows").remove(element1);
                                    OutputFormat format = OutputFormat.createPrettyPrint();
                                    XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
                                    writer.write(document);
                                    writer.close();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } else if (id.equals("addapi")) {
            //获取根节点1
            Element root = document.getRootElement().element("apis");

            //创建元素
            Element newItem = root.addElement("api");
            newItem.addElement("id").setText(UUID.randomUUID().toString());
            newItem.addElement("name").setText("无");
            newItem.addElement("address").setText("无");
            newItem.addElement("shuoming").setText("无");
            newItem.addElement("zhuyi").setText("无");
            newItem.addElement("fanhuixinxi").setText("无");
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
            writer.write(document);
            writer.close();
        } else if (id.equals("updateapi")) {
            //获取根节点1
            Element root = document.getRootElement().element("apis");

            String apiid = request.getParameter("apiid");
            String name = request.getParameter("name");
            String address = request.getParameter("addresstxt");
            String shuoming = request.getParameter("shuomingtxt");
            String zhuyi = request.getParameter("zhuyitxt");
            String fanhuixinxi = request.getParameter("fanhuixinxitxt");

            Iterator iterator = root.nodeIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof Element) {
                    Element element = (Element) o;
                    if (element.element("id").getText().equals(apiid)) {
                        element.element("name").setText(name);
                        element.element("address").setText(address);
                        element.element("shuoming").setText(shuoming);
                        element.element("zhuyi").setText(zhuyi);
                        element.element("fanhuixinxi").setText(fanhuixinxi);
                        OutputFormat format = OutputFormat.createPrettyPrint();
                        XMLWriter writer = new XMLWriter(new FileOutputStream(inputFile), format);
                        writer.write(document);
                        writer.close();
                        return;
                    }
                }
            }
        }
    }

}
