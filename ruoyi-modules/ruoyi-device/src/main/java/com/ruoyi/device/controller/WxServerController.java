package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.ruoyi.common.core.web.domain.AjaxResult;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import com.ruoyi.device.service.OllamaService;

/**
 * 微信服务器配置验证Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/wx/server")
public class WxServerController {
    
    private static final Logger log = LoggerFactory.getLogger(WxServerController.class);
    
    @Value("${wx.token}")
    private String token;

    @Autowired
    private OllamaService ollamaService;

    /**
     * 验证消息的确来自微信服务器
     */
    @GetMapping("/verify")
    public String verifyServer(@RequestParam("signature") String signature,
                             @RequestParam("timestamp") String timestamp,
                             @RequestParam("nonce") String nonce,
                             @RequestParam("echostr") String echostr) {
        
        log.info("接收到微信验证请求 - signature: {}, timestamp: {}, nonce: {}, echostr: {}", 
                signature, timestamp, nonce, echostr);
        
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (String str : arr) {
            content.append(str);
        }
        
        String tmpStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
            
            log.info("计算的签名: {}", tmpStr);
            log.info("微信的签名: {}", signature.toLowerCase());
            
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-1加密失败", e);
            return "服务器内部错误: " + e.getMessage();
        }
        
        // 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (tmpStr != null && tmpStr.equals(signature.toLowerCase())) {
            log.info("验证成功");
            return echostr;
        }
        
        log.warn("验证失败 - 本地签名: {}, 微信签名: {}, 参与签名的字符串: {}", 
                tmpStr, signature.toLowerCase(), content.toString());
        return String.format("非法请求 - 签名不匹配。本地签名: %s, 微信签名: %s", 
                tmpStr, signature.toLowerCase());
    }
    
    /**
     * 将字节数组转换为十六进制字符串
     */
    private String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte b : byteArray) {
            strDigest.append(byteToHexStr(b));
        }
        return strDigest.toString();
    }
    
    /**
     * 将字节转换为十六进制字符串
     */
    private String byteToHexStr(byte mByte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }

    /**
     * 接收微信服务器发送的消息
     */
    @PostMapping(value = "/verify", produces = MediaType.APPLICATION_XML_VALUE)
    public String receiveMessage(HttpServletRequest request) {
        try {
            // Read request body content
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String xmlData = sb.toString();
            log.info("Received WeChat message raw XML: {}", xmlData);

            // Parse XML data
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));
            Element root = document.getRootElement();

            // Get message details
            String msgType = root.elementText("MsgType");
            String fromUserName = root.elementText("FromUserName");
            String toUserName = root.elementText("ToUserName");
            String createTime = root.elementText("CreateTime");
            
            // Log message details
            log.info("Message Details:");
            log.info("- Message Type: {}", msgType);
            log.info("- From User: {}", fromUserName);
            log.info("- To User: {}", toUserName);
            log.info("- Create Time: {}", createTime);

            // Log specific content based on message type
            switch (msgType) {
                case "text":
                    String content = root.elementText("Content");
                    log.info("- Text Content: {}", content);
                    break;
                case "image":
                    String picUrl = root.elementText("PicUrl");
                    String mediaId = root.elementText("MediaId");
                    log.info("- Picture URL: {}", picUrl);
                    log.info("- Media ID: {}", mediaId);
                    break;
                case "voice":
                    String format = root.elementText("Format");
                    String voiceMediaId = root.elementText("MediaId");
                    log.info("- Voice Format: {}", format);
                    log.info("- Voice Media ID: {}", voiceMediaId);
                    break;
                case "video":
                    String videoMediaId = root.elementText("MediaId");
                    String thumbMediaId = root.elementText("ThumbMediaId");
                    log.info("- Video Media ID: {}", videoMediaId);
                    log.info("- Thumb Media ID: {}", thumbMediaId);
                    break;
                case "location":
                    String locationX = root.elementText("Location_X");
                    String locationY = root.elementText("Location_Y");
                    String scale = root.elementText("Scale");
                    String label = root.elementText("Label");
                    log.info("- Location: ({}, {})", locationX, locationY);
                    log.info("- Scale: {}", scale);
                    log.info("- Label: {}", label);
                    break;
                case "link":
                    String title = root.elementText("Title");
                    String description = root.elementText("Description");
                    String url = root.elementText("Url");
                    log.info("- Link Title: {}", title);
                    log.info("- Description: {}", description);
                    log.info("- URL: {}", url);
                    break;
                default:
                    log.info("- Unhandled message type");
            }

            // Build response message
            String responseContent;
            String content = "";
            switch (msgType) {
                case "text":
                    content = root.elementText("Content");
                    log.info("Processing text message: {}", content);
                    try {
                        // 调用Ollama API获取回复
                        responseContent = ollamaService.generateResponse(content);
                        if (responseContent == null || responseContent.trim().isEmpty()) {
                            responseContent = "抱歉，AI暂时无法回复，请稍后再试。";
                        }
                        // 确保响应内容不超过2048字节
                        if (responseContent.getBytes(StandardCharsets.UTF_8).length > 2048) {
                            responseContent = new String(responseContent.getBytes(StandardCharsets.UTF_8), 0, 2048, StandardCharsets.UTF_8);
                        }
                    } catch (Exception e) {
                        log.error("Error getting AI response", e);
                        responseContent = "系统处理消息时出现错误，请稍后重试。";
                    }
                    break;
                case "image":
                    responseContent = "你的图片已收到";
                    break;
                case "voice":
                    responseContent = "你的语音已收到";
                    break;
                case "video":
                    responseContent = "你的视频已收到";
                    break;
                case "location":
                    responseContent = "你的位置信息已收到";
                    break;
                case "link":
                    responseContent = "你分享的链接已收到";
                    break;
                default:
                    responseContent = "你的消息已收到";
            }

            // 构建标准的微信消息返回格式
            String responseXml = String.format(
                "<xml>\n" +
                "<ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "<FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "<CreateTime>%d</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[%s]]></Content>\n" +
                "</xml>",
                fromUserName, toUserName, System.currentTimeMillis()/1000, responseContent
            );

            log.info("Prepared response XML: {}", responseXml);
            
            // 验证返回的XML是否合法
            try {
                SAXReader validationReader = new SAXReader();
                validationReader.read(new ByteArrayInputStream(responseXml.getBytes(StandardCharsets.UTF_8)));
                log.info("Response XML validation passed");
            } catch (DocumentException e) {
                log.error("Invalid response XML", e);
                // 如果XML不合法，返回一个简单的确认消息
                responseXml = String.format(
                    "<xml>\n" +
                    "<ToUserName><![CDATA[%s]]></ToUserName>\n" +
                    "<FromUserName><![CDATA[%s]]></FromUserName>\n" +
                    "<CreateTime>%d</CreateTime>\n" +
                    "<MsgType><![CDATA[text]]></MsgType>\n" +
                    "<Content><![CDATA[消息已收到]]></Content>\n" +
                    "</xml>",
                    fromUserName, toUserName, System.currentTimeMillis()/1000
                );
            }

            return responseXml;

        } catch (IOException | DocumentException e) {
            log.error("Error processing WeChat message", e);
            return "";
        }
    }
} 