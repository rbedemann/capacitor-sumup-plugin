<p align="center"><br><img src="https://user-images.githubusercontent.com/236501/85893648-1c92e880-b7a8-11ea-926d-95355b8175c7.png" width="128" height="128" /></p>
<h3 align="center">SumUp</h3>
<p align="center"><strong><code>@capacitor-community/sumup</code></strong></p>
<p align="center">
  Plugin for SumUp Mobile SDK.
</p>

<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2020?style=flat-square" />
  <a href="https://github.com/capacitor-community/sumup/actions?query=workflow%3A%22CI%22"><img src="https://img.shields.io/github/workflow/status/capacitor-community/sumup/CI?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/sumup"><img src="https://img.shields.io/npm/l/@capacitor-community/sumup?style=flat-square" /></a>
<br>
  <a href="https://www.npmjs.com/package/@capacitor-community/sumup"><img src="https://img.shields.io/npm/dw/@capacitor-community/sumup?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/sumup"><img src="https://img.shields.io/npm/v/@capacitor-community/sumup?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<a href="#contributors-"><img src="https://img.shields.io/badge/all%20contributors-0-orange?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:END -->
</p>

## Maintainers

| Maintainer | GitHub | Social |
| -----------| -------| -------|
| Robin Bedemann | [rbedemann](https://github.com/rbedemann) |  |

## Installation

### Web application

1. Add plugin to your dependencies

    with yarn
    ```shell script
    yarn add @capacitor-community/sumup
    ```
    or
    npm
    ```shell script
    npm i -S @capacitor-community/sumup
    ```

2. Import Plugin in your code

    ```typescript
    import { Plugins } from '@capacitor/core';
    
    const { SumUp } = Plugins;
    ```

### Android

1. Setup SumUp Maven repository

    In `app/build.gradle` add following lines:
    
    ```groovy
    allprojects {
       repositories {
          maven { url 'https://maven.sumup.com/releases' }
       }
    }
    ```

2. Add Plugin to your app's MainActivity

    In Java:
    ```java
    public class MainActivity extends BridgeActivity {
      @Override
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        // Initializes the Bridge
        this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
          // Additional plugins you've installed go here
          // Ex: add(TotallyAwesomePlugin.class);
          add(SumUp.class);
        }});
      }
    }
    ```
    
    Or Kotlin:
    
    ```kotlin
    class MainActivity : BridgeActivity() {
        public override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
    
            // Initializes the Bridge
            this.init(savedInstanceState, object : ArrayList<Class<out Plugin?>?>() {
                init {
                    // Additional plugins you've installed go here
                    // Ex: add(TotallyAwesomePlugin.class);
                    add(SumUp::class.java)
                }
            })
        }
    }
    ```

## Usage

1. Login 
   ```typescript
   SumUp.login({
     affiliateKey: "<< YOUR AFFILIATE KEY >>",
   
     // optional: Login Screen will be shown every time, if not provided
     accessToken: "<< ACCESS TOKEN >>",
   })
   .then((loginResponse: SumUpResponse) => {})
   ```
   Get your affiliate key [here](https://me.sumup.com/developers) and
   find out more about how to generate an access token in SumUps official [documentation](https://developer.sumup.com/rest-api/#section/Authentication)

2. Initiate a checkoout (Login required)
    ```typescript
    SumUp.checkout(
       {
          title: 'Test checkout', 
          total: 100.50, 
          currency: 'EUR',   
          skipSuccessScreen: true,
          additionalInfo: {
            title: 'Booking 1'
          }
       } as CheckoutOptions
   ).then((r: SumUpResponse) => {
     // Checkout completed           
   })
   .catch((r: SumUpResponse) => {   
     // Checkout failed
   })
   ```
   Find a detailed description of possible parameters in SumUp's [documentation](https://github.com/sumup/sumup-android-sdk#4-make-a-payment).

