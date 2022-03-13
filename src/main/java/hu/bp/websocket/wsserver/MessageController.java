package hu.bp.websocket.wsserver;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {
	@MessageMapping("/echo")
	@SendTo("/topics/messagesTopic")
	public DemoMessage processMessage(DemoMessage message) {
		String returnMessage = HtmlUtils.htmlEscape("Received: " + message.getMessage());
		return new DemoMessage(returnMessage);
	}
}
