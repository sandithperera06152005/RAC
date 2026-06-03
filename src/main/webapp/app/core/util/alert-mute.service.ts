import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AlertMuteService {
  private muted = false;

  mute(): void {
    this.muted = true;
  }

  unmute(): void {
    this.muted = false;
  }

  isMuted(): boolean {
    return this.muted;
  }
}
