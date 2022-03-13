import { AfterViewInit, Component, OnDestroy } from '@angular/core';
import { map, Subject, takeUntil } from 'rxjs';
import { RxStompService } from './rx-stomp.service';
import { Message } from '@stomp/stompjs';

type DemoMessage = {
  message: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit, OnDestroy{
  title = 'angular';
  receivedMessages: string[] = [];
  textInput = "";

  private unsubscribe$ = new Subject<boolean>();

  constructor(private rxStompService: RxStompService) { }

  ngAfterViewInit(): void {
    this.rxStompService.watch('/topics/messagesTopic')
    .pipe(
      takeUntil(this.unsubscribe$),
      map((msg: Message) => JSON.parse(msg.body) as DemoMessage)
    )
    .subscribe((message: DemoMessage) => {
      this.receivedMessages.push(message.message);
    });
  }

  onSendMessage() {
    const message: DemoMessage = { message: this.textInput };
    this.rxStompService.publish({ destination: '/messages/echo', body: JSON.stringify(message) });
  }
  
  ngOnDestroy(): void {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

 
}
