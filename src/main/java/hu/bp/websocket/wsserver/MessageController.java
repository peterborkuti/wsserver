package hu.bp.websocket.wsserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
public class MessageController {
	@MessageMapping("/echo")
	@SendTo("/topics/messagesTopic")
	public DemoMessage processMessage(DemoMessage message) {
		log.info("Got message: {}", message);
		String returnMessage = HtmlUtils.htmlEscape("Received: " + message.getMessage());
		log.info("Sending message back: {}", returnMessage);
		return new DemoMessage(returnMessage);
	}
}
