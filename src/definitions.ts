declare module '@capacitor/core' {
  interface PluginRegistry {
    SumUp: SumUpPlugin;
  }
}

export interface SumUpResponse {
  code: number,
  message: string
}

export interface LoginOptions {
  affiliateKey: string,
  accessToken?: string,
}

export interface SumUpPlugin {
  login(options: LoginOptions): Promise<SumUpResponse>;
}
