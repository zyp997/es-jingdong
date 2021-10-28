package ml.guest997.utils;

import ml.guest997.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {
    public List<Content> parseJD(String keyword) throws Exception {
        String url = "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8";
        //解析网页，这里的 Document 对象就是 js 中讲到的浏览器文档对象。
        Document document = Jsoup.parse(new URL(url), 30000);
        //下面的方法都是为了获取搜索页面的标签中的数据并封装成 Content 对象，再将所有的对象放入 List 中。
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        List<Content> contents = new ArrayList<>();
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");    //获取标签属性值
            String price = el.getElementsByClass("p-price").eq(0).text();       //获取标签文本值
            String title = el.getElementsByClass("p-name").eq(0).text();
            contents.add(new Content(img, price, title));
        }
        return contents;
    }

    //测试是否能正常爬取数据
    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().parseJD("java").forEach(System.out::println);
    }

}
