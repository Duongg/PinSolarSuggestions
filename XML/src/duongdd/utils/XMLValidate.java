package duongdd.utils;

public class XMLValidate {
    public String convertStringDetail(String strCapa) {
        strCapa = strCapa
                .replace("&nbsp;", "")
                .replace("~", "")
                .replace(",", ".")
                .replace("·", "")
                .replace(":", "")
                .replace(" ", "")
                .replace("\u00a0", "").trim();
        return strCapa;
    }

    public String convertStringCapacity(String capacity) {
        String capa = "";
        capacity = capacity
                .replace("&nbsp;", "")
                .replace(" ", "")
                .replace("csnóng:", "")
                .replace(",", ".")
                .toLowerCase();
        try {
            if (capacity.contains(":")) {
                int pos = capacity.indexOf(":");
                int lastIndex = capacity.indexOf("w");
                capa = capacity.substring(pos + 1, lastIndex);
            } else if (capacity.contains("t")) {
                int post = capacity.indexOf("t");
                int lastIndex = capacity.indexOf("w");
                capa = capacity.substring(post + 1, lastIndex);
            } else if (capacity.contains("-")) {
                int pos = capacity.charAt(0);
                int last = capacity.charAt(5);
                capa = capacity.substring(pos + 1, last);
            } else if (capacity.contains("w")) {
                int lastIndex = capacity.indexOf("w");
                capa = capacity.substring(0, lastIndex);
            } else if (capacity.contains("hp")) {
                int lastIndexCap_hp = capacity.indexOf("p");
                capa = capacity.substring(0, lastIndexCap_hp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return capa;
    }

    public String cutUnitCapacityProduct(String strCutted) {
        String capacity = "";
        try {
            // cut unit capacity
            if (strCutted.contains("w")) {
                int lastIndexCap = strCutted.indexOf("w");
                capacity = strCutted.substring(0, lastIndexCap);
            } else if (strCutted.contains("hp")) {
                int lastIndexCap_hp = strCutted.indexOf("p");
                capacity = strCutted.substring(0, lastIndexCap_hp);
            } else if (strCutted.contains("btu")) {
                int lastIndexCap_btu = strCutted.indexOf("tu");
                capacity = strCutted.substring(0, lastIndexCap_btu);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return capacity;
    }

    public String cutBoundary(String capacity) {
        char[] ch = new char[4];
        capacity.getChars(6, 10, ch, 0);

        return capacity;
    }

    public float parseStrCapaToFloat(String capacity) {
        String strCap = "";
        float capacityProduct = 0;
        int pos_k = capacity.indexOf("k");
        int pos_h = capacity.indexOf("h");
        int pos_tu = capacity.indexOf("tu");
        int pos_d = capacity.indexOf("đ");
        int pos_c = capacity.indexOf("c");
        // cut string capacity to parse float
        // transform to kw, btu, hp to w
        try {

            if (capacity.charAt(capacity.length() - 1) == 'k') {
                strCap = capacity.substring(0, pos_k);
                capacityProduct = Float.parseFloat(strCap);
                capacityProduct = capacityProduct * 1000;
            } else if (capacity.charAt(capacity.length() - 1) == 'h') {
                strCap = capacity.substring(0, pos_h);
                capacityProduct = Float.parseFloat(strCap);
                capacityProduct = capacityProduct * 745;
            } else if (capacity.charAt(capacity.length() - 1) == 'b') {
                strCap = capacity.substring(0, pos_tu);
                capacityProduct = Float.parseFloat(strCap);
                capacityProduct = (float) (capacityProduct * 0.293);
            } else if (capacity.charAt(capacity.length() - 1) == 'đ') {
                strCap = capacity.substring(0, pos_d);
                capacityProduct = Float.parseFloat(strCap);
            } else if (capacity.charAt(capacity.length() - 1) == 'c') {
                strCap = capacity.substring(0, pos_c);
                capacityProduct = Float.parseFloat(strCap);
            } else {
                capacityProduct = Float.parseFloat(capacity);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return capacityProduct;
    }

    public String parseStringLight(String nameProduct) {
        nameProduct = nameProduct.replace("&#200;", "È")
                .replace("&#232;", "È")
                .replace("&#192;", "À")
                .replace("&#211;", "Ó")
                .replace("&#194;", "Â")
                .replace("&#212;", "Ô")
                .replace("&#210;", "Ò")
                .replace("&#202;", "Ê")
                .replace("&#193;", "Á")
                .replace("&#205;", "í")
                .replace("&#224;", "à")
                .replace("&#226;", "â");
        return nameProduct;
    }

    public float convertKwhPerDayToWatt(String strCapacity) {
        String strConvert = "";
        String cap1 = "";
        String cap2 = "";
        String capacity = "";
        float capacity1 = 0;
        float time = 0;
        float capacityProduct = 0;
        strConvert = strCapacity.replaceAll("\\s+", "");
        try {
            if (strConvert.contains("kw/ngày")) {
                strConvert = strConvert.replace("ngày", "24h");
                int pos = strConvert.indexOf("kw/");
                int last = strConvert.indexOf("h");

                cap1 = strConvert.substring(0, pos);
                cap2 = strConvert.substring(pos + 3, last);
                capacity1 = Float.parseFloat(cap1.trim());
                time = Float.parseFloat(cap2);
                capacityProduct = capacity1 * 1000 * time;
            } else if (strConvert.charAt(strCapacity.length() - 1) == 'w') {
                int lastIndexCap = strConvert.indexOf("w");
                capacity = strConvert.substring(0, lastIndexCap);
                capacityProduct = Float.parseFloat(capacity.trim());
            } else if (strConvert.contains("kw/h")) {
                int pos = strConvert.indexOf("kw/h");
                capacity = strConvert.substring(0, pos);
                float capPro = Float.parseFloat(capacity);
                capacityProduct = capPro * 1000;
            } else if (strConvert.contains("w/h")) {
                int posi = strConvert.indexOf("w/h");
                capacity = strConvert.substring(0, posi);
                capacityProduct = Float.parseFloat(capacity.trim());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return capacityProduct;
    }

    public String cutStringCapacityPinSolar(String capacity) {
        String cap = "";
        capacity = capacity.replace(" ","").toLowerCase().trim();
        try {
            if (capacity.contains("suoer")) {
                int pos1 = capacity.indexOf("suoer") + 5;
                int last1 = capacity.indexOf("w");
                cap = capacity.substring(pos1, last1);
            } else if (capacity.contains("moso")) {
                int pos5 = capacity.indexOf("moso") + 4;
                int last5 = capacity.indexOf("w");
                cap = capacity.substring(pos5, last5);
            } else if (capacity.contains("sofar")) {
                int pos3 = capacity.indexOf("sofar") + 5;
                int last3 = capacity.indexOf("w");
                cap = capacity.substring(pos3, last3);
            }else if (capacity.contains("city")) {
                int pos8 = capacity.indexOf("city") + 4;
                int last8 = capacity.indexOf("w");
                cap = capacity.substring(pos8, last8);
            } else if (capacity.contains("mono")) {
                int pos10 = capacity.indexOf("mono") + 4;
                int last10 = capacity.indexOf("w");
                cap = capacity.substring(pos10, last10);
            }else if (capacity.contains("hoạt")) {
                int pos9 = capacity.indexOf("hoạt") + 4;
                int last9 = capacity.indexOf("w");
                cap = capacity.substring(pos9, last9);
            }else if (capacity.contains("cong")) {
                int pos7 = capacity.indexOf("cong") + 4;
                int last7 = capacity.indexOf("w");
                cap = capacity.substring(pos7, last7);
            } else if (capacity.contains("trời")) {
                int pos2 = capacity.indexOf("trời") + 4;
                int last2 = capacity.indexOf("w");
                cap = capacity.substring(pos2, last2);
            } else if (capacity.contains("pha")) {
                int pos6 = capacity.indexOf("pha") + 3;
                int last6 = capacity.indexOf("w");
                cap = capacity.substring(pos6, last6);
            } else if (capacity.contains("lưới")) {
                int pos4 = capacity.indexOf("lưới") + 4;
                int last4 = capacity.indexOf("w");
                cap = capacity.substring(pos4, last4);
            } else if (capacity.contains("-")) {
                int pos13 = capacity.indexOf("-") + 1;
                int last13 = capacity.indexOf("w");
                cap = capacity.substring(pos13, last13);
            }else if (capacity.contains("power")) {
                int pos11 = capacity.indexOf("power") + 5;
                int last11 = capacity.indexOf("w");
                cap = capacity.substring(pos11, last11);
            }else if (capacity.contains("chuẩn")) {
                int pos12 = capacity.indexOf("chuẩn") + 5;
                int last12 = capacity.indexOf("w");
                cap = capacity.substring(pos12, last12);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cap;
    }
    public float parseToFloat(String capacity){
        float capacityProduct = 0;
        String cap = "";
        capacity = capacity.replace("&nbsp;","").replace(" ","").toLowerCase();
        try {
            if (capacity.contains("cell")) {
                int pos = capacity.indexOf("cell") + 4;
                int last = capacity.indexOf("w");
                cap = capacity.substring(pos, last);
                capacityProduct = Float.parseFloat(cap);
            } else if (capacity.contains("w")) {
                cap = capacity.replace("w", "");
                capacityProduct = Float.parseFloat(cap);
            } else if (capacity.contains("k")) {
                cap = capacity.replace("k", "");
                capacityProduct = Float.parseFloat(cap) * 1000;
            } else {
                capacityProduct = Float.parseFloat(capacity);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return capacityProduct;
    }
}
