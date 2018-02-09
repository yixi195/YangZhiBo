package com.turing.framework.utils;

import org.junit.Test;

import static com.turing.framework.utils.EncodeUtils.base64Decode;
import static com.turing.framework.utils.EncodeUtils.base64Encode;
import static com.turing.framework.utils.EncodeUtils.base64Encode2String;
import static com.turing.framework.utils.EncodeUtils.htmlDecode;
import static com.turing.framework.utils.EncodeUtils.htmlEncode;
import static com.turing.framework.utils.EncodeUtils.urlDecode;
import static com.turing.framework.utils.EncodeUtils.urlEncode;
import static junit.framework.Assert.assertEquals;

/**
 * EncodeUtils单元测试
 */

public class EncodeUtilsTest {

    String urlEncodeString = "%E5%93%88%E5%93%88%E5%93%88";
    String html = "<html>" +
            "<head>" +
            "<title>我的第一个 HTML 页面</title>" +
            "</head>" +
            "<body>" +
            "<p>body 元素的内容会显示在浏览器中。</p>" +
            "<p>title 元素的内容会显示在浏览器的标题栏中。</p>" +
            "</body>" +
            "</html>";
    String encodeHtml = "&lt;html&gt;&lt;head&gt;&lt;title&gt;&#25105;&#30340;&#31532;&#19968;&#20010; HTML &#39029;&#38754;&lt;/title&gt;&lt;/head&gt;&lt;body&gt;&lt;p&gt;body &#20803;&#32032;&#30340;&#20869;&#23481;&#20250;&#26174;&#31034;&#22312;&#27983;&#35272;&#22120;&#20013;&#12290;&lt;/p&gt;&lt;p&gt;title &#20803;&#32032;&#30340;&#20869;&#23481;&#20250;&#26174;&#31034;&#22312;&#27983;&#35272;&#22120;&#30340;&#26631;&#39064;&#26639;&#20013;&#12290;&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;";

    @Test
    public void testUrlEncode() throws Exception {
        assertEquals(urlEncode("哈哈哈"), urlEncodeString);
        assertEquals(urlEncode("哈哈哈", "UTF-8"), urlEncodeString);
    }

    @Test
    public void testUrlDecode() throws Exception {
        assertEquals(urlDecode(urlEncodeString), "哈哈哈");
        assertEquals(urlDecode(urlEncodeString, "UTF-8"), "哈哈哈");
    }

    @Test
    public void testBase64EncodeAndDecode() throws Exception {
        assertEquals(base64Decode(base64Encode("blankj")), "blankj".getBytes());
        assertEquals(base64Decode(base64Encode2String("blankj".getBytes())), "blankj".getBytes());
        assertEquals(base64Encode2String("blankj".getBytes()), "Ymxhbmtq");
        assertEquals(base64Encode("blankj".getBytes()), "Ymxhbmtq".getBytes());
    }

    @Test
    public void testHtmlEncode() throws Exception {
        assertEquals(htmlEncode(html), encodeHtml);
    }

    @Test
    public void testHtmlDecode() throws Exception {
        assertEquals(htmlDecode(encodeHtml).toString(), html);
    }
}
