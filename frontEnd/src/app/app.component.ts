import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'tour-finder-front-end';
  slides = [
    {'image': 'https://gsr.dev/material2-carousel/assets/demo.png'},
    {'image': 'https://gsr.dev/material2-carousel/assets/demo.png'},
    {'image': 'https://gsr.dev/material2-carousel/assets/demo.png'},
    {'image': 'https://gsr.dev/material2-carousel/assets/demo.png'},
    {'image': 'https://gsr.dev/material2-carousel/assets/demo.png'}
  ];
}
