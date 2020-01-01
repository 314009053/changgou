
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt测试
 * jwt 是用于微服务之间传递信息的一段加密字符串，是一个json格式。各个微服务可以根据改json字符串识别用户的身份信息，
 * 构成：头部                 荷载                                                                                                        签名                三部分以.分隔
 *示例：eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjaGFuZ2dvdSIsImlzcyI6IueVhei0reWVhuWfjiIsInN1YiI6IkpXVOS7pOeJjOa1i-ivlSIsImlhdCI6MTU3Nzg5MzI0Mn0.jEaDwIFbOvqDvxHiRcb41pRxS7UiEGs00DMqPWZ6zsY
 *头部：用于描述关于该jwt的基本信息，如类型，签名所用过的算法等，也可以被表示成一个json对象｛"typ":"JWT","alg":"hs256"｝   并对其进行base64编码
 * 载荷：就是存放有效信息的地方，主要包括三部分   base64（标准中注册的声明+公共的声明+私有的声明）
 * 1、标准中注册的声明（建议但不强制使用）
 *    iss: jwt的签发者
 *    sub: 当前令牌的描述说明
 *    aud: 接受jet的一方
 *    exp: jwt的过期时间，必须大于签发时间
 *    nbf: 定义在什么时间之前，该jwt都是不可用的
 *    iat： jwt的签发时间
 *    jti：jwt的唯一身份标识，主要用来作为一次性token，回避重放攻击
 * 2、公共的声明
 *    可以添加任何信息，一般添加用的相关信息或其他业务需要的必要信息，但不建议添加敏感信息，应为该部分在客户端可解密
 * 3、私有的声明
 *    提供者和消费者共同定义的声明，一般不建议存放敏感信息，
 *    该部分就是自定义的claim，与jwt标准规定的区别在于：jwt规定的claim，jwt在接收方拿到后，都知道该怎么对其进行验证，而私有的claim不会验证，除非告诉接收方明确的验证规则
 *    即公共部分和私有部分不参与令牌校验。
 *签证：校验数据是否被篡改。 将base64后的头部和载荷进行拼接，再由头部中声明的加密方法，进行加盐加密。构成jwt的第三部分
 *     secret（密钥）是保存在服务器端的。jwt的签发生成也是在服务器端进行的，secret就是用来进行jwt的签发和验签。所以，他用的是服务器端的私钥
 * 1、header（base64后头部）
 * 2、payload（base64后的载荷）
 * 3、secret （密钥-》盐）
 *
 *
 */

public class JwtTest {

    @Test
    public void testCreat(){
        JwtBuilder builder = Jwts.builder()
                .setId("changgou")           //设置唯一id
                .setIssuer("畅购商城")         //设置签发者
                .setSubject("JWT令牌测试")      //设置主题
                .setIssuedAt(new Date())     //设置签发日期
                .setExpiration(new Date(System.currentTimeMillis()+36000))  //设置过期时间，一般不要太长
                .signWith(SignatureAlgorithm.HS256,"changgou");//设置签名算法，和密钥（盐）

        //自定义载荷
        Map<String,Object> param = new HashMap<>();
        param.put("name","zhangsan");
        param.put("age",10000);
        param.put("address","北京");

        builder.addClaims(param);
        String token = builder.compact();
        System.out.println(token);

        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjaGFuZ2dvdSIsImlzcyI6IueVhei0reWVhuWfjiIsInN1YiI6IkpXVOS7pOeJjOa1i-ivlSIsImlhdCI6MTU3Nzg5MzI0Mn0.jEaDwIFbOvqDvxHiRcb41pRxS7UiEGs00DMqPWZ6zsY
          
    }
    
    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjaGFuZ2dvdSIsImlzcyI6IueVhei0reWVhuWfjiIsInN1YiI6IkpXVOS7pOeJjOa1i-ivlSIsImlhdCI6MTU3Nzg5NDYyOSwiZXhwIjoxNTc3ODk0NjY1LCJhZGRyZXNzIjoi5YyX5LqsIiwibmFtZSI6InpoYW5nc2FuIiwiYWdlIjoxMDAwMH0.CID4_YiSjHqLBPQmzOiWsaIo2bixMISDxNeHDGsp52A";

        Claims claims = Jwts.parser().setSigningKey("changgou")   //设置密钥（盐）
                .parseClaimsJws(token)                      //解析签名
                .getBody();//获取结果


        System.out.println(claims.toString());
    }
}
