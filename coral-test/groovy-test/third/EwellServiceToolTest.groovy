import com.coral.test.groovy.web.UserInfoDTO
import com.ewell.sdk.business.EwellServiceTool
import com.ewell.sdk.properties.PropertiesService
import com.ewell.sdk.properties.PropertiesValue

def loadProperties() {
    String path = "E:\\projects\\mine\\java\\coral-cloud\\coral-test\\groovy-test\\third\\SDKConfig.properties";
    Object inputStream;
    try {
        inputStream = new FileInputStream(path);
    } catch (FileNotFoundException var2) {
        inputStream = PropertiesService.class.getClassLoader().getResourceAsStream("SDKConfig.properties");
    }
    Properties properties = new Properties();
    properties.load((InputStream) inputStream);
    return properties;
};

def run2(UserInfoDTO user) {
    println '#####user: ' + user.getUserNo() + ',' + user.getUserName();
    println 'properties:' + loadProperties();
    EwellServiceTool ewellServiceTool = new EwellServiceTool();

    println '######ewellServiceTool:' + ewellServiceTool;
    println 'all properties:' + PropertiesValue.properties;
};

run2(user);