import { WebPlugin } from '@capacitor/core';
import { SumUpPlugin } from './definitions';

export class SumUpWeb extends WebPlugin implements SumUpPlugin {
  constructor() {
    super({
      name: 'SumUp',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const SumUp = new SumUpWeb();

export { SumUp };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(SumUp);
