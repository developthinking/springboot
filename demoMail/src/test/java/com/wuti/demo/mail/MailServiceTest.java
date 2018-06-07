package com.wuti.demo.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.wuti.demo.service.mail.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

	@Autowired
	private MailService mailService;
	@Autowired
    private TemplateEngine templateEngine;
	
//	@Test
//	public void testSimpleMail() throws Exception {
//		mailService.sendSimpleMail("youremail","这是一封简单邮件","大家好，这是我的第一封邮件！");
//	}
	
	@Test
	public void testHtmlMail() throws Exception {
	    String content="<html>\n" +
	            "<body>\n" +
	            "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
	            "</body>\n" +
	            "</html>";
	    mailService.sendHtmlMail("youremail","这是一封HTML邮件",content);
	}
	
//	@Test
//	public void sendAttachmentsMail() {
//		String[] filePahts = new String[2];
//	    String filePath1="E:/wt/1000000001.jpg";
//	    String filePath2="F:/AllShare/hadoop/flowSum.jar";
//	    filePahts[0] = filePath1;
//	    filePahts[1] = filePath2;
//	    
//	    mailService.sendAttachmentsMail("youremail", "主题：带附件的邮件", "有附件，请查收！", filePahts);
//	}
	
//	@Test
//	public void sendInlineResourceMail() {
//	    String rscId = "pic001";
//	    String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
//	    String imgPath = "E:/wt/1000000001.jpg";
//
//	    mailService.sendInlineResourceMail("youremail", "主题：这是有图片的邮件", content, imgPath, rscId);
//	}
	
//	@Test
//	public void sendTemplateMail() {
//	    //创建邮件正文
//	    Context context = new Context();
//	    context.setVariable("name", "尊敬的wt：");
//	    context.setVariable("id", "001");
//	    String emailContent = templateEngine.process("emailTemplate", context);
//
//	    mailService.sendHtmlMail("youremail","主题：这是模板邮件",emailContent);
//	}
	
}
