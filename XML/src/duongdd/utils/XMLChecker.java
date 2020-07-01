package duongdd.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class XMLChecker {
    private static Character quote;
    private static boolean isStartChar(char c){
        return Character.isLetter(c) || XMLConfigurations.UNDERSCORE == c || XMLConfigurations.COLON == c;
    }
    private static boolean isNamedChar(char c){
        return Character.isLetterOrDigit(c) || XMLConfigurations.UNDERSCORE == c || XMLConfigurations.HYPHEN == c || XMLConfigurations.PERIOD == c;
    }
    private static boolean isStartTagChars(char c){
        return isStartChar(c);
    }
    private static boolean isStartAttrChars(char c){
        return isStartChar(c);
    }
    private static boolean isTagChars(char c){
        return isNamedChar(c);
    }
    private static boolean isAttrChars(char c){
        return isNamedChar(c);
    }
    private static boolean isSpace(char c){
        return Character.isSpaceChar(c);
    }
    public static final List<String> INNER_TAGS = Arrays.asList(
            "area", "base", "br", "col", "command",
            "embed", "hr", "img", "input", "keygen",
            "link", "meta", "param","source", "track","wbr"
    );


    private static boolean isChar(char x){
        return (x >= 'a' && x<='z');
    }
    private static String getTagName(String content){
        if(content.charAt(content.length() - 2) == '/'){
            return null;
        }
        String res = "";
        int i = 1;
        if(content.charAt(i) == '/'){
            res = res + '/';
            i++;
        }

        while(isChar(content.charAt(i))){
            res = res + content.charAt(i);
            i++;
        }

        if(res.length() == 0 || (res.length() == 1 && res.charAt(0) == '/')){
            return null;
        }
        return res;
    }
    public static String fixTagName(String content){
        //lists open tags
        List<String> stack = new ArrayList<>();
        //lists position open tags
        List<Integer> li = new ArrayList<>();
        //lists extra tag
        List<String> addTag = new ArrayList<>();

        int sz = content.length();
        int mark[] = new int[sz];
        Arrays.fill(mark, -1);

        int i = 0;
        while (i < content.length()){
            if(content.charAt(i) == '<'){
                int j = i + 1;

                String tagTmp = "" + content.charAt(i);

                while (j < content.length() && content.charAt(j) != '>'){
                    tagTmp = tagTmp + content.charAt(j);
                    j++;
                }

                int curEnd = j;
                tagTmp = tagTmp + '>';
                i = j + 1;
                String tag = getTagName(tagTmp);

                if(tag != null){
                    if(tag.charAt(0) != '/'){
                        stack.add(tag);
                        li.add(curEnd);
                    }
                    else{
                        while (stack.size() > 0){
                            if(stack.get(stack.size() - 1).equals(tag.substring(1))){
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                                break;
                            }
                            else{
                                //need to
                                addTag.add(stack.get(stack.size() - 1));
                                mark[li.get(li.size() - 1)] = addTag.size() - 1;
                                //remove
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                            }
                        }
                    }
                }
            }
            else i++;
        }
        while (stack.size() > 0){
            addTag.add(stack.get(stack.size() - 1));
            mark[li.get(li.size() - 1)] = addTag.size() - 1;
            stack.remove(stack.size() - 1);
            li.remove(li.size() - 1);
        }
        String newContent = "";

        for(int j = 0; j < content.length(); j++){
            newContent = newContent + content.charAt(j);
            if(mark[j] != -1){
                newContent = newContent + "</" +addTag.get(mark[j]) + ">";
            }
        }


        return "<document>" + newContent + "</document>";
    }
    public static String encodeContent(String content){
        content = content.replace("&agrave;", "à")
                .replace("&eacute;", "é")
                .replace("&ecirc;", "ê")
                .replace("&iacute;", "í")
                .replace("&ocirc;", "ô")
                .replace("&aacute;", "á")
                .replace("&acirc;", "â")
                .replace("&Acirc;", "Â")
                .replace("&Agrave;", "À")
                .replace("&Aacute;", "Á")
                .replace("&Ocirc;", "Ô")
                .replace("&ograve;","o")
                .replace("&laquo;", "")
                .replace("&raquo;", "")
                .replace("&amp;amp;", "")
                .replace("&ampamp;", "")
                .replace("&amp;oacute;","ó")
                .replace("", "")
                .replace("&#8211;","-")
                .replace("&amp;#244;","ô")
                .replace("&amp;#200;","È")
                .replace("&amp;#192;","À")
                .replace("&nbsp;","")
                .replace("&ampamp;","" )
                .replace("&amp;amp;", "")
                .replace("", "")
                .replace("&ampldquo;","")
                .replace("&amprdquo;","")
                .replace("&apostimes", "")
                .replace("&amp;oacute;","ó")
                .replace("gtCocirc","")
                .replace("&apos","")
                .replace("&amphellip;","")
                .replace("&amp","")
                .replace("&nbsp;","")
                .replace("gtTủ","")
                .replace("gtTốc","")
                .replace("&gt;","")
                .replace("$lt;p&","")
                .replace("$lt;/p&gt","")
                .replace("&amp;nbsp;","")
                .replace("&gt","&gt;")
                .replace("&", "&amp;");
        return content;
    }
    public static String preProcessingHtml(String content){
        content = content
                .replace("<div class=\"value\"><","<div class=\"value\">")
                .replace("onclick"," onclick")
                .replace("alt"," alt")
                .replace("\"style","\" style")
                .replace("\"src","\" src");
        return content;
    }
    public static String preParseNT(String content){
        content = content.replace("<ul>","")
                        .replace("</ul>","")
        .replace("</p> --></div>","</p> -->")
        .replace("type=hidden","type=\"hidden\"")
        .replace("checked","")
        .replace("data-numposts=\"5\"></div></div>","data-numposts=\"5\"></div>")
        .replace("</div></div></div><div class=\"clearfix\"></div>","");

        return content;
    }
    public static String preParseLedLight(String content){
        content = content.replace("itemscope","")
                .replace("required","")
                .replace("</li></document>","</document>");
        return content;
    }
    public static String preParseDM(String content){
        content = content.replace("</div></div></div><script type=\"text/javascript\">","<script type=\"text/javascript\">")
                .replace("</section>","")
                .replace("</body></html>","")
        .replace("<iframe src=\"https://www.googletagmanager.com/ns.html?id=GTM-PZWTS8Z\"height=\"0\" width=\"0\" style=\"display:none;visibility:hidden\"></iframe>","")
        .replace("<a span","<a ")
        .replace("data-tooltip=\"<p></span>","data-tooltip=\"\"><p>")
        .replace("trực tiếp</span></li><li","trực tiếp</span><li")
        .replace("bằng Nhôm</span></li></ul></div>","bằng Nhôm</span></li>")
        .replace("<br></br></div></div></div></document>","<br></br></div></div></document>")
        .replace("Hệ thống làm lạnh kép</span></span></span></li>","Hệ thống làm lạnh kép</span></span>")
        .replace("2014</span></span></li></ul></div></document>","2014</span></span></li></document>")
        .replace("</div><div><br></br></div></div><div><br></br></div></div></document>","</div><div><br></br></div></div><div><br></br></div></div></div></document>");
        return content;
    }
    public static String parseBrandName(String name){
        String nameBrand = name.replace("&#194;","Â")
                .replace("&#225;","á")
                .replace("&#232;","è")
                .replace("&#226;","â")
                .replace("&#224;","à");
        return nameBrand;
    }
    private static String convert(Map<String, String> mapAttr){
        if(mapAttr.isEmpty()){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : mapAttr.entrySet()) {

            String value = entry.getValue()
                    .replace("&", "&amp")
                    .replace("\"","&quot;")
                    .replace("'", "&apos")
                    .replace("<", "$lt;")
                    .replace(">", "&gt")
                    .replace("&ampamp;","" )
                    .replace("&amp;amp;", "")
                    .replace("", "")
                    .replace("&ampldquo;","")
                    .replace("&amprdquo;","")
                    .replace("&apostimes", "")
                    .replace("&amp;oacute;","ó")
                    .replace("gtCocirc","")
                    .replace("&apos","")
                    .replace("&amphellip;","")
                    .replace("&amp","")
                    .replace("&nbsp;","")
                    .replace("gtTủ","")
                    .replace("gtTốc","")
                    .replace("&gt;","")
                    .replace("$lt;p&","")
                    .replace("$lt;/p&gt","")
                    .replace("&amp;nbsp;","")
                    .replace("&gt","&gt;");
            builder.append(entry.getKey())
                    .append("=")
                    .append("\"").append(value).append("\"")
                    .append(" ");

        }//end for map
        String result = builder.toString().trim();
        if(!result.equals("")){
            result = " " + result;
        }
        return result;
    }

    public static String TagChecker(String content){
        content = content + " ";
        char[] reader = content.toCharArray();

        StringBuilder writer = new StringBuilder();
        StringBuilder openTag = new StringBuilder();
        boolean isEmptyTag = false;
        boolean isOpenTag = false;
        boolean isCloseTag = false;

        StringBuilder closeTag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();

        Map<String, String> mapAttributes = new HashMap<>();
        StringBuilder currentValue = new StringBuilder();
        Stack<String> stack = new Stack<>();

        String state = XMLConfigurations.CONTENT;

        for (int i = 0; i < reader.length; i++) {

            char c = reader[i];

            switch(state){
                case XMLConfigurations.CONTENT:
                    if(c == XMLConfigurations.LT){
                        state = XMLConfigurations.OPEN_BRACKET;
                        writer.append(currentValue.toString().trim().replace("&", "&amp;"));
                    }else{
                        currentValue.append(c);
                    }
                    break;
                case XMLConfigurations.OPEN_BRACKET:
                    if(isStartTagChars(c)){
                        state = XMLConfigurations.OPEN_TAG_NAME;

                        isOpenTag = true;
                        isEmptyTag = false;
                        isCloseTag = false;

                        openTag.setLength(0);
                        openTag.append(c);

                    }else if(c == XMLConfigurations.SLASH){
                        state = XMLConfigurations.CLOSE_TAG_SLASH;

                        isOpenTag = false;
                        isCloseTag = true;
                        isEmptyTag = false;
                    }
                    break;
                case XMLConfigurations.OPEN_TAG_NAME:
                    if(isTagChars(c)){
                        openTag.append(c);
                    }else if(isSpace(c)){
                        state = XMLConfigurations.TAG_INNER;
                    }else if(c == XMLConfigurations.GT){
                        state = XMLConfigurations.CLOSE_BRACKET;
                    }else if(c == XMLConfigurations.SLASH){
                        state = XMLConfigurations.EMPTY_SLASH;
                    }
                    break;
                case XMLConfigurations.TAG_INNER:
                    if(isSpace(c)){

                    }else if(isStartAttrChars(c)){
                        state = XMLConfigurations.ATTR_NAME;
                        attrName.setLength(0);
                        attrName.append(c);
                    }else if(c == XMLConfigurations.GT){
                        state = XMLConfigurations.CLOSE_BRACKET;
                    }else if(c == XMLConfigurations.SLASH){
                        state = XMLConfigurations.EMPTY_SLASH;
                    }
                    break;
                case XMLConfigurations.ATTR_NAME:
                    if(isAttrChars(c)){
                        attrName.append(c);
                    }else if(c == XMLConfigurations.EQ){
                        state = XMLConfigurations.EQUAL;
                    }else if(isSpace(c)){
                        state = XMLConfigurations.EQUAL_WAIT;
                    }else{
                        if(c == XMLConfigurations.SLASH){
                            mapAttributes.put(attrName.toString(), "true");
                            state = XMLConfigurations.EMPTY_SLASH;
                        }else if(c == XMLConfigurations.GT){
                            mapAttributes.put(attrName.toString(), "true");
                            state = XMLConfigurations.CLOSE_BRACKET;
                        }
                    }

                    break;
                case XMLConfigurations.EQUAL_WAIT:
                    if(isSpace(c)){

                    }else if(c == XMLConfigurations.EQ){
                        state = XMLConfigurations.EQUAL;
                    }else {
                        if(isStartAttrChars(c)){
                            mapAttributes.put(attrName.toString(), "true");
                            state = XMLConfigurations.ATTR_NAME;

                            attrName.setLength(0);
                            attrName.append(c);
                        }
                    }
                    break;
                case XMLConfigurations.EQUAL:
                    if(isSpace(c)){

                    }else if(c == XMLConfigurations.D_QUOT || c == XMLConfigurations.S_QUOT){
                        quote = c;
                        state = XMLConfigurations.ATTR_VALUE_Q;

                        attrValue.setLength(0);
                    }else if(!isSpace(c) && c != XMLConfigurations.GT){
                        state = XMLConfigurations.ATTR_VALUE_NQ;

                        attrValue.setLength(0);
                        attrValue.append(c);
                    }
                    break;
                case XMLConfigurations.ATTR_VALUE_Q:
                    if(c != quote){
                        attrValue.append(c);
                    }else if(c == quote){
                        state = XMLConfigurations.TAG_INNER;
                        mapAttributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case XMLConfigurations.ATTR_VALUE_NQ:
                    if(!isSpace(c) && c != XMLConfigurations.GT){
                        attrValue.append(c);
                    }else if(isSpace(c)){
                        state = XMLConfigurations.TAG_INNER;
                        mapAttributes.put(attrName.toString(), attrValue.toString());
                    }else if(c == XMLConfigurations.GT){
                        state = XMLConfigurations.CLOSE_BRACKET;
                        mapAttributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case XMLConfigurations.EMPTY_SLASH:
                    if(c == XMLConfigurations.GT){
                        state = XMLConfigurations.CLOSE_BRACKET;
                        isEmptyTag = true;
                    }
                    break;
                case XMLConfigurations.CLOSE_TAG_SLASH:
                    if(isStartAttrChars(c)){
                        state = XMLConfigurations.CLOSE_TAG_NAME;
                        closeTag.setLength(0);
                        closeTag.append(c);
                    }
                    break;
                case XMLConfigurations.CLOSE_TAG_NAME:
                    if(isTagChars(c)){
                        closeTag.append(c);
                    }else if(isSpace(c)){
                        state = XMLConfigurations.WAIT_END_TAG_CLOSE;
                    }else if(c == XMLConfigurations.GT){
                        state = XMLConfigurations.CLOSE_BRACKET;
                    }
                    break;
                case XMLConfigurations.WAIT_END_TAG_CLOSE:
                    if(isSpace(c)){

                    }else if(c == XMLConfigurations.GT){
                        state = XMLConfigurations.CLOSE_BRACKET;
                    }
                    break;
                case XMLConfigurations.CLOSE_BRACKET:
                    if(isOpenTag){
                        String openTagName = openTag.toString().toLowerCase();

                        if(INNER_TAGS.contains(openTagName)){
                            isEmptyTag = true;
                        }
                        writer.append(XMLConfigurations.LT)
                                .append(openTagName)
                                .append(convert(mapAttributes))
                                .append((isEmptyTag ? "/" : ""))
                                .append(XMLConfigurations.GT);

                        mapAttributes.clear();
                        if(!isEmptyTag){
                            stack.push(openTagName);
                        }

                    }else if(isCloseTag){

                        String closeTagName = closeTag.toString().toLowerCase();
                        if(!stack.isEmpty() && stack.contains(closeTagName)){
                            while(!stack.isEmpty() && !stack.peek().equals(closeTagName)){
                                writer.append(XMLConfigurations.LT)
                                        .append(XMLConfigurations.SLASH)
                                        .append(stack.pop())
                                        .append(XMLConfigurations.GT);
                            }
                            if(!stack.isEmpty() && stack.peek().equals(closeTagName)){
                                writer.append(XMLConfigurations.LT)
                                        .append(XMLConfigurations.SLASH)
                                        .append(stack.pop())
                                        .append(XMLConfigurations.GT);
                            }
                        }// end if
                    }

                    if(c == XMLConfigurations.LT){
                        state = XMLConfigurations.OPEN_BRACKET;
                    }else{
                        state = XMLConfigurations.CONTENT;
                        currentValue.setLength(0);
                        currentValue.append(c);
                    }
                    break;
            }

        }//end for reader
        if(XMLConfigurations.CONTENT.equals(state)){
            writer.append(currentValue.toString().trim().replace("&", "&amp;"));
        }

        while(!stack.isEmpty()){
            writer.append(XMLConfigurations.LT)
                    .append(XMLConfigurations.SLASH)
                    .append(stack.pop())
                    .append(XMLConfigurations.GT);
        }
        return writer.toString();
    }

}
