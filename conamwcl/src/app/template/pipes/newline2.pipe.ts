// newline2.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({ name: 'newline2' })
export class Newline2Pipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) {}

  transform(value: string): SafeHtml {
    const lineBreaks = value.replace(/\n/g, '<br>');
    return this.sanitizer.bypassSecurityTrustHtml(lineBreaks);
  }
}