import { WebPlugin } from '@capacitor/core';
import { CheckoutOptions, LoginOptions, SumUpPlugin, SumUpResponse } from './definitions';
import { registerWebPlugin } from '@capacitor/core';

export class SumUpWeb extends WebPlugin implements SumUpPlugin {
  constructor() {
    super({
      name: 'SumUp',
      platforms: ['web'],
    });
  }

  login(options: LoginOptions): Promise<SumUpResponse> {
    console.debug(options)
    return Promise.resolve({
      code: 0,
      message: 'Not available in web'
    });
  }

  checkout(options: CheckoutOptions): Promise<SumUpResponse> {
    console.debug(options)
    return Promise.resolve({
      code: 0,
      message: 'Not available in web'
    });
  }
}

const SumUp = new SumUpWeb();

export { SumUp };
registerWebPlugin(SumUp);
