// newline.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({ name: 'newline' })
export class NewlinePipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) {}

  transform(value: string): SafeHtml {
    const lineBreaks = value.replace(/\n/g, '<br>');
    return this.sanitizer.bypassSecurityTrustHtml(lineBreaks);
  }
}