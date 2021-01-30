package weibo4j.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Source implements java.io.Serializable {

    private static final long serialVersionUID = -8972443458374235866L;
    private String url; // 来源连接
    private String relationShip;
    private String name; // 来源文案名称

    public Source(String str) {
        super();
        String[] source = str.split("\"", 5);
        url = source[1];
        relationShip = source[3];
        name = source[4].replace(">", "").replace("</a", "");
    }

    @Override
    public String toString() {
        return "Source [url=" + url + ", relationShip=" + relationShip + ", name=" + name + "]";
    }

}
