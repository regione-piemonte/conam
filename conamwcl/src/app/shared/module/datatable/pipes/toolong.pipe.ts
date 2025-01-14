// toolong.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({ name: 'toolong' })
export class ToolongPipe implements PipeTransform {
  transform(value: string, length: number = 20): string {
    const truncatedText = value.length > length ? value.substring(0, length) + '...' : value;
    const tooltipText = value.length > length ? value : '';
    const html = `<span title="${tooltipText}">${truncatedText}</span>`;
    return btoa(html); // Codifica HTML in una stringa base64
  }
}