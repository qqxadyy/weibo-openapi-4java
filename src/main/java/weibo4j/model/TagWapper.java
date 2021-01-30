package weibo4j.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagWapper {
    private List<Tag> tags;
    private String id;

    @Override
    public String toString() {
        String str = "";
        str += "TagWapper [tags=[";
        for (Tag t : tags) {
            str += t.toString() + " ";
        }
        str += "], ";
        str += "id=" + id + "]";
        return str;
    }

}
