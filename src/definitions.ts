declare module '@capacitor/core' {
  interface PluginRegistry {
    SumUp: SumUpPlugin;
  }
}

export interface SumUpPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
