

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author zhangzongbo
 * @date 18-12-7 下午3:02
 */
public class regTest {

    public static void main(String[] args) {
         final String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\\\\\/])+$";
         final String string = "http://www.fapiao.com/dzfp-web/pdf/download?request=6e7JGm38jfjghVrv4ILd-kEn64HcUX4qL4a4qJ4-CHLmqVnenXC692m74H5oxkjgdsYazxcUmfcOH2fAfY1Vw__%5EDadIfJgiEf";

         final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
         final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("完整匹配: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
    }


}
